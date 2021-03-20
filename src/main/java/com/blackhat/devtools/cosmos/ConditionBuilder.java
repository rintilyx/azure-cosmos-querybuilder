package com.blackhat.devtools.cosmos;

public class ConditionBuilder {

    private final CosmosReference cosmosReference;
    private String attribute;
    private Object value;
    private ComparisonOperator comparisonOperator;
    private Boolean includeIfNullIndicator = false;

    public ConditionBuilder(CosmosReference cosmosReference) {
        this.cosmosReference = cosmosReference;
    }

    public ConditionBuilder attribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public ConditionBuilder includeIfNull(boolean includeIfNullIndicator) {
        this.includeIfNullIndicator = includeIfNullIndicator;
        return this;
    }

    public Condition equalsTo(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.EQUALS;
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator);
    }

    public Condition arrayContains(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.ARRAY_CONTAINS;
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator);
    }

    public Condition in(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.IN;
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator);
    }

    public Condition contains(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.CONTAINS;
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator);
    }

    public Condition startsWith(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.STARTS_WITH;
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator);
    }

    public Condition gte(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.GREATER_THAN_EQ;
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator);
    }

    public Condition gt(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.GREATER_THAN;
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator);
    }

    public Condition lt(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.LOWER_THAN;
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator);
    }

    public Condition lte(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.LOWER_THAN_EQ;
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator);
    }


}
