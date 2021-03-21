package com.blackhat.devtools.cosmos;

class InternalCondition {

    private final Condition condition;
    private final BooleanOperator booleanOperator;
    private final Integer order;
    private final boolean isRoot;

    public InternalCondition(Condition condition, BooleanOperator booleanOperator, Integer order, boolean isRoot) {
        this.condition = condition;
        this.booleanOperator = booleanOperator;
        this.order = order;
        this.isRoot = isRoot;
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

    boolean isRoot() {
        return isRoot;
    }

    static InternalConditionBuilder builder() {
        return new InternalConditionBuilder();
    }

    static class InternalConditionBuilder {

        private Condition condition;
        private BooleanOperator booleanOperator;
        private Integer order;
        private boolean isRoot = false;

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

        InternalConditionBuilder isRoot(boolean isRoot) {
            this.isRoot = isRoot;
            return this;
        }

        InternalCondition build() {
            return new InternalCondition(this.condition, this.booleanOperator, this.order, this.isRoot);
        }


    }


}
