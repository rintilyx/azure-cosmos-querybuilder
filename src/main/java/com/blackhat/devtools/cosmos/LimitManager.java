package com.blackhat.devtools.cosmos;

import com.azure.data.cosmos.SqlQuerySpec;

public class LimitManager {

    private final CosmosQueryConfiguration cosmosQueryConfiguration;

    public LimitManager(CosmosQueryConfiguration cosmosQueryConfiguration) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    public SqlQuerySpec buildQuery() {
        return new CosmosQueryBuilder(cosmosQueryConfiguration)
                .buildQuery(SelectionType.SELECT);
    }

}
