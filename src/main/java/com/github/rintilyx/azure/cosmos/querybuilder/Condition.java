package com.github.rintilyx.azure.cosmos.querybuilder;

public class Condition {

    private final CosmosReference cosmosReference;
    private final String attribute;
    private final Object value;
    private String sqlParamName;
    private final ComparisonOperator comparisonOperator;
    private final boolean includedIfNull;
    private final Boolean boolExpr;

    Condition(CosmosReference cosmosReference, String attribute, Object value, ComparisonOperator comparisonOperator, boolean includedIfNull, Boolean boolExpr) {
        if (cosmosReference == null)
            throw new RuntimeException("Collection or Join reference must be provided");
        if (attribute == null)
            throw new RuntimeException("attribute must be provided");
        if (comparisonOperator == null)
            throw new RuntimeException("comparison operator must be provided");
        this.cosmosReference = cosmosReference;
        this.attribute = attribute;
        this.value = value;
        this.comparisonOperator = comparisonOperator;
        this.includedIfNull = includedIfNull;
        this.boolExpr = boolExpr;
    }

    CosmosReference getCosmosReference() {
        return this.cosmosReference;
    }

    String getAttribute() {
        return this.attribute;
    }

    Object getValue() {
        return this.value;
    }


    ComparisonOperator getComparisonOperator() {
        return this.comparisonOperator;
    }

    boolean isIncludedIfNull() {
        return this.includedIfNull;
    }

    String getSqlParamName() {
        return this.sqlParamName;
    }

    void setSqlParamName(String sqlParamName) {
        this.sqlParamName = sqlParamName;
    }

    Boolean getBoolExpr() {
        return this.boolExpr;
    }

}
