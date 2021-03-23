package com.github.rintilyx.azure.cosmos.querybuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CosmosQuery {

    private final CosmosQueryConfiguration cosmosQueryConfiguration;

    public CosmosQuery(CosmosQueryConfiguration cosmosQueryConfiguration) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    public CosmosQuery() {
        this.cosmosQueryConfiguration = new CosmosQueryConfiguration();
    }

    public SelectManager select() {
        this.cosmosQueryConfiguration.setSelectionType(SelectionType.SELECT);
        this.cosmosQueryConfiguration.setFields(new ArrayList<>());
        return new SelectManager(this.cosmosQueryConfiguration);
    }

    public SelectManager select(List<String> fields) {
        this.cosmosQueryConfiguration.setSelectionType(SelectionType.SELECT);
        this.cosmosQueryConfiguration.setFields(fields);
        return new SelectManager(this.cosmosQueryConfiguration);
    }

    public SelectManager select(String... fields) {
        this.cosmosQueryConfiguration.setSelectionType(SelectionType.SELECT);
        this.cosmosQueryConfiguration.setFields(Arrays.asList(fields));
        return new SelectManager(this.cosmosQueryConfiguration);
    }

}
