package com.github.rintilyx.azure.cosmos.querybuilder;

import com.azure.data.cosmos.CosmosItemProperties;
import com.azure.data.cosmos.FeedOptions;
import com.azure.data.cosmos.FeedResponse;
import com.azure.data.cosmos.SqlQuerySpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WhereManager {

    private final CosmosQueryConfiguration cosmosQueryConfiguration;
    private final List<InternalExpression> internalExpressionList = new ArrayList<>();
    private List<OrderByClause> orderBy = new ArrayList<>();
    private Integer orderCounter = 1;

    public WhereManager(CosmosQueryConfiguration cosmosQueryConfiguration, Expression expression) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
        this.internalExpressionList.add(InternalExpression.builder().booleanOperator(null).expression(expression).order(this.orderCounter).build());
    }

    public WhereManager(CosmosQueryConfiguration cosmosQueryConfiguration, List<InternalExpression> internalExpressionList) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
        this.internalExpressionList.addAll(internalExpressionList);
    }

    public WhereManager and(Condition condition) {
        this.orderCounter += 1;
        this.internalExpressionList.add(
                InternalExpression.builder()
                        .booleanOperator(BooleanOperator.AND)
                        .expression(new ExpressionBuilder(condition).build())
                        .order(this.orderCounter)
                        .build()
        );
        return this;
    }

    public WhereManager and(Expression expression) {
        this.orderCounter += 1;
        this.internalExpressionList.add(
                InternalExpression.builder()
                        .booleanOperator(BooleanOperator.AND)
                        .expression(expression)
                        .order(this.orderCounter)
                        .build()
        );
        return this;
    }

    public WhereManager or(Condition condition) {
        this.orderCounter += 1;
        this.internalExpressionList.add(
                InternalExpression.builder()
                        .booleanOperator(BooleanOperator.OR)
                        .expression(new ExpressionBuilder(condition).build())
                        .order(this.orderCounter)
                        .build()
        );
        return this;
    }

    public WhereManager or(Expression expression) {
        this.orderCounter += 1;
        this.internalExpressionList.add(
                InternalExpression.builder()
                        .booleanOperator(BooleanOperator.OR)
                        .expression(expression)
                        .order(this.orderCounter)
                        .build()
        );
        return this;
    }


    public WhereManager orderBy(OrderByClause... orderByClauses) {
        this.orderBy = Arrays.asList(orderByClauses);
        return this;
    }

    public WhereManager orderBy(List<OrderByClause> orderByClauses) {
        this.orderBy = orderByClauses;
        return this;
    }

    public OffsetManager offset(Integer offset) {
        this.cosmosQueryConfiguration.setInternalExpressionList(this.internalExpressionList);
        this.cosmosQueryConfiguration.setOffset(offset);
        this.cosmosQueryConfiguration.setOrderBy(this.orderBy);
        return new OffsetManager(this.cosmosQueryConfiguration);
    }

    private void setInternalExpressionList() {
        this.cosmosQueryConfiguration.setInternalExpressionList(this.internalExpressionList);
    }

    public SqlQuerySpec buildQuery() {
        setInternalExpressionList();
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration)
                .buildQuery();
    }

    public Flux<FeedResponse<CosmosItemProperties>> queryItems() {
        setInternalExpressionList();
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItems();
    }

    public Flux<FeedResponse<CosmosItemProperties>> queryItems(FeedOptions feedOptions) {
        setInternalExpressionList();
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItems(feedOptions);
    }

    public List<FeedResponse<CosmosItemProperties>> queryItemsSync(FeedOptions feedOptions) {
        setInternalExpressionList();
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItemsSync(feedOptions);
    }

    public <T> Flux<T> queryItems(FeedOptions feedOptions, Class<T> targetClass) {
        setInternalExpressionList();
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItems(feedOptions, targetClass);
    }

    public <T> List<T> queryItemsSync(FeedOptions feedOptions, Class<T> targetClass) {
        setInternalExpressionList();
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItemsSync(feedOptions, targetClass);
    }

    public <T> Flux<T> queryItems(FeedOptions feedOptions, CosmosItemPropertiesConverter<T> customConverter) {
        setInternalExpressionList();
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItems(feedOptions, customConverter);
    }

    public <T> List<T> queryItemsSync(FeedOptions feedOptions, CosmosItemPropertiesConverter<T> customConverter) {
        setInternalExpressionList();
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItemsSync(feedOptions, customConverter);
    }

    public Mono<Long> countItems(FeedOptions feedOptions) {
        this.cosmosQueryConfiguration.setSelectionType(SelectionType.COUNT);
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).countItems(feedOptions);
    }

    public Long countItemsSync(FeedOptions feedOptions) {
        this.cosmosQueryConfiguration.setSelectionType(SelectionType.COUNT);
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).countItemsSync(feedOptions);
    }

}
