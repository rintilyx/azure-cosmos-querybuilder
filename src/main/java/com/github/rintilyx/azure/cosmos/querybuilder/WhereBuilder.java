package com.github.rintilyx.azure.cosmos.querybuilder;

import java.util.ArrayList;
import java.util.List;

public class WhereBuilder {

    private final List<InternalExpression> internalExpressionList = new ArrayList<>();
    private Integer orderCounter = 1;

    WhereBuilder(Condition condition) {
        this.and(condition);
    }

    WhereBuilder(Expression expression) {
        this.and(expression);
    }

    public WhereBuilder and(Condition condition) {
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

    public WhereBuilder and(Expression expression) {
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

    public WhereBuilder or(Condition condition) {
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

    public WhereBuilder or(Expression expression) {
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

    public Criteria buildCriteria() {
        return new Criteria(this.internalExpressionList);
    }

}
