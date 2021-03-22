package com.github.rintilyx.azure.cosmos.querybuilder;

import java.util.ArrayList;
import java.util.List;

public class Expression {

    private List<InternalCondition> internalConditions = new ArrayList<>();
    private Integer orderCounter = 1;

    Expression(List<InternalCondition> internalConditions) {
        this.internalConditions = internalConditions;
    }

    Expression(Condition condition) {
        this.internalConditions.add(
                InternalCondition.builder()
                        .condition(condition)
                        .booleanOperator(null)
                        .isRoot(true)
                        .order(this.orderCounter)
                        .build()
        );
    }

    List<InternalCondition> getInternalConditions() {
        return this.internalConditions;
    }
}
