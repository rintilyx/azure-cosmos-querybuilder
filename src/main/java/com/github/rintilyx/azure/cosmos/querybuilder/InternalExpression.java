package com.github.rintilyx.azure.cosmos.querybuilder;

class InternalExpression {

    private final Expression expression;
    private final BooleanOperator booleanOperator;
    private final Integer order;

    public InternalExpression(Expression expression, BooleanOperator booleanOperator, Integer order) {
        this.expression = expression;
        this.booleanOperator = booleanOperator;
        this.order = order;
    }

    Expression getExpression() {
        return this.expression;
    }

    BooleanOperator getBooleanOperator() {
        return this.booleanOperator;
    }

    Integer getOrder() {
        return this.order;
    }


    static InternalExpressionBuilder builder() {
        return new InternalExpressionBuilder();
    }

    static class InternalExpressionBuilder {

        private Expression expression;
        private BooleanOperator booleanOperator;
        private Integer order;

        InternalExpressionBuilder expression(Expression expression) {
            this.expression = expression;
            return this;
        }

        InternalExpressionBuilder booleanOperator(BooleanOperator booleanOperator) {
            this.booleanOperator = booleanOperator;
            return this;
        }

        InternalExpressionBuilder order(Integer order) {
            this.order = order;
            return this;
        }

        InternalExpression build() {
            return new InternalExpression(this.expression, this.booleanOperator, this.order);
        }


    }


}
