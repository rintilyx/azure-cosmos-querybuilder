package com.github.rintilyx.azure.cosmos.querybuilder;

import java.util.List;

public class Criteria {

    private List<InternalExpression> internalExpressionList;

    Criteria(List<InternalExpression> internalExpressions) {
        this.internalExpressionList = internalExpressions;
    }

    public List<InternalExpression> getInternalExpressionList() {
        return internalExpressionList;
    }

}
