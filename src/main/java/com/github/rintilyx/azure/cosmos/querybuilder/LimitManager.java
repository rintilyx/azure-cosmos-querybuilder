package com.github.rintilyx.azure.cosmos.querybuilder;

import com.azure.data.cosmos.SqlQuerySpec;

public class LimitManager {

    private final CosmosQueryConfiguration cosmosQueryConfiguration;

    public LimitManager(CosmosQueryConfiguration cosmosQueryConfiguration) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    public SqlQuerySpec buildQuery() {
        return new CosmosQueryBuilder(cosmosQueryConfiguration)
                .buildQuery();
    }

}
