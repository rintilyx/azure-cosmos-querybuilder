package com.github.rintilyx.azure.cosmos.querybuilder;

public class OrderByClauseBuilder {

    private final CosmosReference cosmosReference;
    private String attribute;
    private SortingCriteria criteria;

    public OrderByClauseBuilder(CosmosReference cosmosReference) {
        this.cosmosReference = cosmosReference;
    }

    public OrderByClauseBuilder attribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public OrderByClauseBuilder criteria(SortingCriteria criteria) {
        this.criteria = criteria;
        return this;
    }

    public OrderByClause build() {
        return new OrderByClause(this.cosmosReference, this.attribute, this.criteria);
    }

}