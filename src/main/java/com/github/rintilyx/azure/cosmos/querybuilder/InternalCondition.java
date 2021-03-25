package com.github.rintilyx.azure.cosmos.querybuilder;

class InternalCondition {

    private final Condition condition;
    private final BooleanOperator booleanOperator;
    private final Integer order;

    public InternalCondition(Condition condition, BooleanOperator booleanOperator, Integer order) {
        this.condition = condition;
        this.booleanOperator = booleanOperator;
        this.order = order;
    }

    Condition getCondition() {
        return condition;
    }

    BooleanOperator getBooleanOperator() {
        return booleanOperator;
    }

    Integer getOrder() {
        return order;
    }


    static InternalConditionBuilder builder() {
        return new InternalConditionBuilder();
    }

    static class InternalConditionBuilder {

        private Condition condition;
        private BooleanOperator booleanOperator;
        private Integer order;

        InternalConditionBuilder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        InternalConditionBuilder booleanOperator(BooleanOperator booleanOperator) {
            this.booleanOperator = booleanOperator;
            return this;
        }

        InternalConditionBuilder order(Integer order) {
            this.order = order;
            return this;
        }

        InternalCondition build() {
            return new InternalCondition(this.condition, this.booleanOperator, this.order);
        }


    }


}
