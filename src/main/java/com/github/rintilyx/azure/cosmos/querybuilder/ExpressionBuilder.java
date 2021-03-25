package com.github.rintilyx.azure.cosmos.querybuilder;

import java.util.ArrayList;
import java.util.List;

public class ExpressionBuilder {

    private List<InternalCondition> internalConditions = new ArrayList<>();
    private Integer orderCounter = 1;

    public ExpressionBuilder(Condition condition) {
        this.internalConditions.add(
                InternalCondition.builder()
                        .order(this.orderCounter)
                        .condition(condition)
                        .booleanOperator(null)
                        .build()
        );
    }

    public ExpressionBuilder and(Condition condition) {
        this.orderCounter += 1;
        this.internalConditions.add(
                InternalCondition.builder()
                        .order(this.orderCounter)
                        .condition(condition)
                        .booleanOperator(BooleanOperator.AND)
                        .build()
        );
        return this;
    }

    public ExpressionBuilder or(Condition condition) {
        this.orderCounter += 1;
        this.internalConditions.add(
                InternalCondition.builder()
                        .order(this.orderCounter)
                        .condition(condition)
                        .booleanOperator(BooleanOperator.OR)
                        .build()
        );
        return this;
    }

    public Expression build() {
        return new Expression(this.internalConditions);
    }

}
