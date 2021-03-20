package com.blackhat.devtools.cosmos;

import com.azure.data.cosmos.SqlQuerySpec;

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
        this.internalExpressionList.add(InternalExpression.builder().booleanOperator(null).expression(expression).isRoot(true).order(this.orderCounter).build());
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


    public WhereManager orderBy(OrderByClause ...orderByClauses) {
        this.orderBy = Arrays.asList(orderByClauses);
        return this;
    }

    public OffsetManager offset(Integer offset) {
        this.cosmosQueryConfiguration.setInternalExpressionList(this.internalExpressionList);
        this.cosmosQueryConfiguration.setOffset(offset);
        this.cosmosQueryConfiguration.setOrderBy(this.orderBy);
        return new OffsetManager(this.cosmosQueryConfiguration);
    }

    public SqlQuerySpec buildQuery() {
        this.cosmosQueryConfiguration.setInternalExpressionList(this.internalExpressionList);
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration)
                .buildQuery(SelectionType.SELECT);
    }

}
