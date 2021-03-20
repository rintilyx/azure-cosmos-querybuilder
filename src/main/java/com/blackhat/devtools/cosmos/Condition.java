package com.blackhat.devtools.cosmos;

public class Condition {

    private final CosmosReference cosmosReference;
    private final String attribute;
    private final Object value;
    private final ComparisonOperator comparisonOperator;
    private final boolean includedIfNull;

    Condition(CosmosReference cosmosReference, String attribute, Object value, ComparisonOperator comparisonOperator, boolean includedIfNull) {
        this.cosmosReference = cosmosReference;
        this.attribute = attribute;
        this.value = value;
        this.comparisonOperator = comparisonOperator;
        this.includedIfNull = includedIfNull;
    }

    CosmosReference getCosmosReference() {
        return cosmosReference;
    }

    String getAttribute() {
        return attribute;
    }

    Object getValue() {
        return value;
    }

    ComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    boolean isIncludedIfNull() {
        return includedIfNull;
    }

}
