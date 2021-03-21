package com.blackhat.devtools.cosmos;

import com.azure.data.cosmos.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmosQueryBuilder {

    private static final String QUERY_PATTERN = "${SELECT} FROM ${COLLECTION_NAME} ${ALIAS} ${JOINS} ${WHERE} ${ORDER_BY} ${OFFSET} ${LIMIT}";

    private static final Logger LOGGER = LoggerFactory.getLogger(CosmosQueryBuilder.class);
    private final CosmosQueryConfiguration cosmosQueryConfiguration;
    private Integer sequence = 0;

    CosmosQueryBuilder(CosmosQueryConfiguration cosmosQueryConfiguration) {
        if (cosmosQueryConfiguration == null || cosmosQueryConfiguration.getCollection() == null)
            throw new RuntimeException("CosmosQueryConfiguration or collection cannot be null");
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    Flux<FeedResponse<CosmosItemProperties>> queryItems(FeedOptions feedOptions) {
        if (this.cosmosQueryConfiguration.getCosmosClient() == null)
            throw new RuntimeException("CosmosClient must be provided");
        if (this.cosmosQueryConfiguration.getDatabase() == null)
            throw new RuntimeException("Database must be provided");

        return Flux.just(this.cosmosQueryConfiguration.getCosmosClient())
                .map(cosmosClient -> cosmosClient.getDatabase(this.cosmosQueryConfiguration.getDatabase()))
                .map(cosmosDatabase -> cosmosDatabase.getContainer(this.cosmosQueryConfiguration.getCollection().getName()))
                .flatMap(cosmosContainer -> cosmosContainer.queryItems(this.buildQuery(), feedOptions));
    }

    <T> Flux<T> queryItems(FeedOptions feedOptions, Class<T> targetClass) {
        return this.queryItems(feedOptions)
                .flatMapIterable(FeedResponse::results)
                .flatMap(cosmosItemProperties -> applyConvert(cosmosItemProperties, (c) -> {
                            try {
                                return c.getObject(targetClass);
                            } catch (IOException ex) {
                                if (this.cosmosQueryConfiguration.getLogger() != null)
                                    this.cosmosQueryConfiguration.getLogger().warn("Conversion JSON to Object is failed: {}", ex.getMessage());
                                else
                                    LOGGER.warn("Conversion JSON to Object is failed: {}", ex.getMessage());
                                return null;
                            }
                        })
                );
    }

    <T> Flux<T> queryItems(FeedOptions feedOptions, Function<CosmosItemProperties, T> customConverter) {
        return this.queryItems(feedOptions)
                .flatMapIterable(FeedResponse::results)
                .flatMap(cosmosItemProperties -> applyConvert(cosmosItemProperties, customConverter));
    }

    private <T> Flux<T> applyConvert(CosmosItemProperties cosmosItemProperties, Function<CosmosItemProperties, T> converter) {
        return Flux.create(sink -> {
            try {
                T t = converter.apply(cosmosItemProperties);
                if (t != null)
                    sink.next(t);
                sink.complete();
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }


    SqlQuerySpec buildQuery() {
        final SqlParameterList sqlParameters = new SqlParameterList();

        String expressions = buildWhere(sqlParameters);

        String query = QUERY_PATTERN
                .replace("${SELECT}", buildSelect(this.cosmosQueryConfiguration.getSelectionType()))
                .replace("${COLLECTION_NAME}", this.cosmosQueryConfiguration.getCollection().getName())
                .replace("${ALIAS}", this.cosmosQueryConfiguration.getCollection().getAlias())
                .replace("${JOINS}", buildJoins(this.cosmosQueryConfiguration.getJoins()))
                .replace("${WHERE}", expressions.isEmpty() ? "" : "WHERE " + expressions)
                .replace("${LIMIT}", buildLimit(this.cosmosQueryConfiguration.getLimit()))
                .replace("${OFFSET}", buildOffset(this.cosmosQueryConfiguration.getOffset()))
                .replace("${ORDER_BY}", buildOrderBy(this.cosmosQueryConfiguration.getCollection().getAlias(), this.cosmosQueryConfiguration.getOrderBy()));

        if (this.cosmosQueryConfiguration.isExplicitLog()) {
            String formattedQuery = query.replace("", "");
            for (SqlParameter sqlParameter : sqlParameters) {
                formattedQuery = formattedQuery.replace(sqlParameter.name(), sqlParameter.value(Object.class) != null ? sqlParameter.value(Object.class).toString() : "null");
            }
            logQuery(formattedQuery);
        } else {
            logQuery(query);
        }

        return new SqlQuerySpec(query, sqlParameters);
    }

    private void logQuery(String query) {
        if (this.cosmosQueryConfiguration.getLogger() != null) {
            this.cosmosQueryConfiguration.getLogger().debug("{}", query);
        } else {
            LOGGER.debug("{}", query);
        }
    }

    private String buildSelect(SelectionType selectionType) {
        return selectionType.buildPattern(this.cosmosQueryConfiguration.getCollection().getAlias(), this.cosmosQueryConfiguration.getFields());
    }

    private static String buildOffset(Integer offset) {
        return offset != null ? ("OFFSET " + offset.toString()) : StringUtils.EMPTY;
    }

    private static String buildLimit(Integer limit) {
        return limit != null ? ("LIMIT " + limit.toString()) : StringUtils.EMPTY;
    }

    private static String buildOrderBy(String alias, List<OrderByClause> orderByClauses) {
        return Optional.ofNullable(orderByClauses)
                .map(clauses -> {
                    String orderByPattern = alias + ".${PROPERTY} ${CRITERIA}";
                    String sortingString = clauses.stream()
                            .map(clause -> orderByPattern.replace("${PROPERTY}", clause.getField()).replace("${CRITERIA}", clause.getCriteria().name()))
                            .collect(Collectors.joining(","));
                    return StringUtils.isNotBlank(sortingString) ? ("ORDER BY " + sortingString) : StringUtils.EMPTY;
                })
                .orElse(StringUtils.EMPTY);
    }

    private String buildJoins(List<CosmosJoinReference> joins) {
        return Optional.ofNullable(joins)
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .map(j -> "JOIN " + j.getAlias() + " IN " + j.getParent().getAlias() + "." + j.getName())
                .collect(Collectors.joining(" "));
    }

    private String buildWhere(SqlParameterList sqlParameters) {
        StringBuilder result = new StringBuilder();

        for (InternalExpression internalExpression : this.cosmosQueryConfiguration.getInternalExpressionList()) {
            if(!internalExpression.isRoot()) {
                result.append(" ").append(internalExpression.getBooleanOperator().value()).append(" ");
            }
            result.append(buildExpression(internalExpression.getExpression(), sqlParameters));
        }

        return result.toString();
    }

    private String buildExpression(Expression expression, SqlParameterList sqlParameters) {
        StringBuilder result = new StringBuilder("(");
        for (InternalCondition internalCondition : expression.getInternalConditions()) {
            if(!internalCondition.isRoot()) {
                result.append(" ").append(internalCondition.getBooleanOperator().value()).append(" ");
            }
            result.append(buildCondition(internalCondition.getCondition(), sqlParameters));
        }
        result.append(")");
        return !StringUtils.equals("()", result) ? result.toString() : "";
    }

    private String buildCondition(Condition condition, SqlParameterList sqlParameters) {
        if (includeInQuery(condition)) {
            String sqlParamName = getSqlParamName(sqlParameters, condition);
            condition.setSqlParamName(sqlParamName);
            return condition.getComparisonOperator().buildCondition(condition);
        } else {
            return "";
        }
    }

    private boolean includeInQuery(Condition condition) {
        return BooleanUtils.isTrue(condition.isIncludedIfNull()) || condition.getValue() != null;
    }

    private String getSqlParamName(SqlParameterList sqlParameterList, Condition condition) {
        if (condition.getValue() != null) {
            String result = "@" + condition.getAttribute() + "_" + this.sequence;
            incrementSequence();
            sqlParameterList.add(new SqlParameter().name(result).value(condition.getValue()));
            return result;
        } else {
            return null;
        }
    }

    private void incrementSequence() {
        this.sequence += 1;
    }


}
