package com.github.rintilyx.azure.cosmos.querybuilder;

import com.azure.data.cosmos.*;
import com.github.rintilyx.azure.cosmos.data.Country;
import com.github.rintilyx.azure.cosmos.data.ResultWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmosQueryBuilder {

    private static final String QUERY_PATTERN = "${SELECT} FROM ${COLLECTION_NAME} ${ALIAS} ${JOINS} ${WHERE} ${ORDER_BY} ${OFFSET} ${LIMIT}";

    private static final Logger LOGGER = LoggerFactory.getLogger(CosmosQueryBuilder.class);
    private final CosmosQueryConfiguration cosmosQueryConfiguration;
    private Integer sequence = 0;

    CosmosQueryBuilder(CosmosQueryConfiguration cosmosQueryConfiguration) {
        if (cosmosQueryConfiguration == null || cosmosQueryConfiguration.getCollection() == null)
            throw new CosmosQueryBuilderException("CosmosQueryConfiguration or collection cannot be null");
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    Flux<FeedResponse<CosmosItemProperties>> queryItems() {
        if (this.cosmosQueryConfiguration.getCosmosClient() == null)
            throw new CosmosQueryBuilderException("CosmosClient must be provided");
        if (this.cosmosQueryConfiguration.getDatabase() == null)
            throw new CosmosQueryBuilderException("Database must be provided");

        return Flux.just(this.cosmosQueryConfiguration.getCosmosClient())
                .map(cosmosClient -> cosmosClient.getDatabase(this.cosmosQueryConfiguration.getDatabase()))
                .map(cosmosDatabase -> cosmosDatabase.getContainer(this.cosmosQueryConfiguration.getCollection().getName()))
                .flatMap(cosmosContainer ->
                        cosmosContainer.queryItems(this.buildQuery())
                                .retryWhen(Retry.fixedDelay(this.cosmosQueryConfiguration.getMaxAttempts(), this.cosmosQueryConfiguration.getOnRetryFixedDelay()))
                );
    }

    Flux<FeedResponse<CosmosItemProperties>> queryItems(FeedOptions feedOptions) {
        if (this.cosmosQueryConfiguration.getCosmosClient() == null)
            throw new CosmosQueryBuilderException("CosmosClient must be provided");
        if (this.cosmosQueryConfiguration.getDatabase() == null)
            throw new CosmosQueryBuilderException("Database must be provided");

        return Flux.just(this.cosmosQueryConfiguration.getCosmosClient())
                .map(cosmosClient -> cosmosClient.getDatabase(this.cosmosQueryConfiguration.getDatabase()))
                .map(cosmosDatabase -> cosmosDatabase.getContainer(this.cosmosQueryConfiguration.getCollection().getName()))
                .flatMap(cosmosContainer ->
                        cosmosContainer.queryItems(this.buildQuery(), feedOptions)
                                .retryWhen(Retry.fixedDelay(this.cosmosQueryConfiguration.getMaxAttempts(), this.cosmosQueryConfiguration.getOnRetryFixedDelay()))
                );
    }

    List<FeedResponse<CosmosItemProperties>> queryItemsSync(FeedOptions feedOptions) {
        if (this.cosmosQueryConfiguration.getCosmosSyncClient() != null) {
            List<FeedResponse<CosmosItemProperties>> result = new ArrayList<>();
            performQueryItemsSync(feedOptions)
                    .forEachRemaining(result::add);
            return result;
        } else {
            return queryItems().toStream(1).collect(Collectors.toList());
        }
    }


    <T> Flux<T> queryItems(FeedOptions feedOptions, Class<T> targetClass) {
        return this.queryItems(feedOptions)
                .flatMapIterable(FeedResponse::results)
                .map(cosmosItemProperties -> getDefaultConverter(targetClass).convert(cosmosItemProperties));
    }

    <T> List<T> queryItemsSync(FeedOptions feedOptions, Class<T> targetClass) {
        if (this.cosmosQueryConfiguration.getCosmosSyncClient() != null) {
            List<T> result = new ArrayList<>();
            performQueryItemsSync(feedOptions)
                    .forEachRemaining(feedResponse -> {
                        feedResponse.results()
                                .stream()
                                .map(cosmosItemProperties -> getDefaultConverter(targetClass).convert(cosmosItemProperties))
                                .forEach(result::add);
                    });
            return result;
        } else {
            return this.queryItems(feedOptions)
                    .flatMapIterable(FeedResponse::results)
                    .map(cosmosItemProperties -> getDefaultConverter(targetClass).convert(cosmosItemProperties))
                    .toStream(1)
                    .collect(Collectors.toList());
        }
    }


    <T> Flux<T> queryItems(FeedOptions feedOptions, CosmosItemPropertiesConverter<T> customConverter) {
        return this.queryItems(feedOptions)
                .flatMapIterable(FeedResponse::results)
                .map(customConverter::convert);
    }

    <T> List<T> queryItemsSync(FeedOptions feedOptions, CosmosItemPropertiesConverter<T> customConverter) {
        if (this.cosmosQueryConfiguration.getCosmosSyncClient() != null) {
            List<T> result = new ArrayList<>();
            performQueryItemsSync(feedOptions)
                    .forEachRemaining(feedResponse -> {
                        feedResponse.results()
                                .stream()
                                .map(customConverter::convert)
                                .forEach(result::add);
                    });
            return result;
        } else {
            return this.queryItems(feedOptions)
                    .flatMapIterable(FeedResponse::results)
                    .map(customConverter::convert)
                    .toStream(1)
                    .collect(Collectors.toList());
        }
    }

    <T> Mono<Long> countItems(FeedOptions feedOptions) {
        return this.queryItems(feedOptions)
                .flatMapIterable(FeedResponse::results)
                .map(cosmosItemProperties -> cosmosItemProperties.get("_aggregate"))
                .map(i -> Long.parseLong(String.valueOf(i)))
                .reduce(Long::sum);
    }

    Long countItemsSync(FeedOptions feedOptions) {
        if (this.cosmosQueryConfiguration.getCosmosSyncClient() != null) {
            long result = 0L;
            Iterator<FeedResponse<CosmosItemProperties>> feedResponseIterator = performQueryItemsSync(feedOptions);
            while (feedResponseIterator.hasNext()) {
                FeedResponse<CosmosItemProperties> feedResponse = feedResponseIterator.next();
                for (CosmosItemProperties cosmosItemProperties : feedResponse.results()) {
                    result += Long.parseLong((String) cosmosItemProperties.get("_aggregate"));
                }
            }
            return result;
        } else {
            return this.countItems(feedOptions)
                    .flux()
                    .toStream(1)
                    .reduce(Long::sum)
                    .orElse(0L);
        }
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
                .replace("${ORDER_BY}", buildOrderBy(this.cosmosQueryConfiguration.getOrderBy()));

        logQuery(query);

        return new SqlQuerySpec(query, sqlParameters);
    }


    private Iterator<FeedResponse<CosmosItemProperties>> performQueryItemsSync(FeedOptions feedOptions) {
        if (this.cosmosQueryConfiguration.getCosmosClient() == null || this.cosmosQueryConfiguration.getCosmosSyncClient() == null)
            throw new CosmosQueryBuilderException("CosmosClient or CosmosSyncClient must be provided");
        return this.cosmosQueryConfiguration.getCosmosSyncClient()
                .getDatabase(this.cosmosQueryConfiguration.getDatabase())
                .getContainer(this.cosmosQueryConfiguration.getCollection().getName())
                .queryItems(this.buildQuery(), feedOptions);
    }

    public <T> CosmosItemPropertiesConverter<T> getDefaultConverter(Class<T> targetClass) {
        return (c) -> {
            try {
                Type resultWrapperType = new TypeToken<ResultWrapper<Country>>() {
                }.getType();
                ResultWrapper<T> wrapper = new Gson().fromJson(c.toJson(), resultWrapperType);
                return wrapper.getC();
            } catch (Exception ex) {
                if (CosmosQueryBuilder.this.cosmosQueryConfiguration.getLogger() != null)
                    CosmosQueryBuilder.this.cosmosQueryConfiguration.getLogger().warn("Conversion JSON to {} is failed due to: {}", targetClass.getName(), ex.getMessage());
                else
                    LOGGER.warn("Conversion JSON to {} is failed due to: {}", targetClass.getName(), ex.getMessage());
                return null;
            }
        };
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

    private static String buildOrderBy(List<OrderByClause> orderByClauses) {
        return Optional.ofNullable(orderByClauses)
                .map(clauses -> {
                    String orderByPattern = "${ALIAS}.${PROPERTY}${CRITERIA}";
                    String sortingString = clauses.stream()
                            .map(clause -> orderByPattern.replace("${ALIAS}", clause.getCosmosReference().getAlias()).replace("${PROPERTY}", clause.getAttribute()).replace("${CRITERIA}", clause.getCriteria() != null ? " " + clause.getCriteria().name() : ""))
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
        if (isToInclude(condition)) {
            String sqlParamName = getSqlParamName(sqlParameters, condition);
            condition.setSqlParamName(sqlParamName);
            return condition.getComparisonOperator().buildCondition(condition);
        } else {
            return "";
        }
    }

    private boolean isToInclude(Condition condition) {
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
