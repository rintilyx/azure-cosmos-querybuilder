package com.github.rintilyx.azure.cosmos.querybuilder;

public class CriteriaBuilder {

    public CriteriaBuilder() {

    }

    public WhereBuilder where(Condition condition) {
        return new WhereBuilder(condition);
    }

    public WhereBuilder where(Expression expression) {
        return new WhereBuilder(expression);
    }

}
