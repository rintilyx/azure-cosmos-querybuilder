package com.github.rintilyx.azure.cosmos.querybuilder;

public class OrderByClause {

    private final CosmosReference cosmosReference;
    private final String attribute;
    private final SortingCriteria criteria;

    OrderByClause(CosmosReference cosmosReference, String attribute, SortingCriteria criteria) {
        if (cosmosReference == null) {
            throw new CosmosQueryBuilderException("Cosmos Reference must be provided in order to build OrderByClause");
        }
        if (attribute == null) {
            throw new CosmosQueryBuilderException("attribute must be provided in order to build OrderByClause");
        }
        this.cosmosReference = cosmosReference;
        this.attribute = attribute;
        this.criteria = criteria;
    }

    public static OrderByClause of(CosmosReference cosmosReference, String attribute, SortingCriteria criteria) {
        return new OrderByClause(cosmosReference, attribute, criteria);
    }

    CosmosReference getCosmosReference() {
        return cosmosReference;
    }

    String getAttribute() {
        return attribute;
    }

    SortingCriteria getCriteria() {
        return criteria;
    }
}