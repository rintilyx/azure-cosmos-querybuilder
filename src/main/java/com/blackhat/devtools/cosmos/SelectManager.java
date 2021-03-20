package com.blackhat.devtools.cosmos;

public class SelectManager {

    private final CosmosQueryConfiguration cosmosQueryConfiguration;

    public SelectManager(CosmosQueryConfiguration cosmosQueryConfiguration) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    public FromManager from(CosmosCollection collection) {
        this.cosmosQueryConfiguration.setCollection(collection);
        return new FromManager(this.cosmosQueryConfiguration);
    }

}
