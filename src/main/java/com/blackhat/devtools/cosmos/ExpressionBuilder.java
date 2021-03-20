package com.blackhat.devtools.cosmos;

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
                        .isRoot(true)
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
                        .isRoot(false)
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
                        .isRoot(false)
                        .build()
        );
        return this;
    }

    public Expression build() {
        return new Expression(this.internalConditions);
    }

}
