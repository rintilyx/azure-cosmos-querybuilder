package com.blackhat.devtools.cosmos;

import java.util.ArrayList;
import java.util.List;

public class CosmosQuery {

    private CosmosQueryConfiguration cosmosQueryConfiguration;

    public CosmosQuery(CosmosQueryConfiguration cosmosQueryConfiguration) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    public SelectManager select() {
        this.cosmosQueryConfiguration.setFields(new ArrayList<>());
        return new SelectManager(this.cosmosQueryConfiguration);
    }

    public SelectManager select(List<String> fields) {
        this.cosmosQueryConfiguration.setFields(fields);
        return new SelectManager(this.cosmosQueryConfiguration);
    }

}
