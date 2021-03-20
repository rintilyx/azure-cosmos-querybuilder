package com.blackhat.devtools.cosmos;

public class OffsetManager {

    private final CosmosQueryConfiguration cosmosQueryConfiguration;

    public OffsetManager(CosmosQueryConfiguration cosmosQueryConfiguration) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    public LimitManager limit(Integer limit) {
        this.cosmosQueryConfiguration.setLimit(limit);
        return new LimitManager(this.cosmosQueryConfiguration);
    }

}
