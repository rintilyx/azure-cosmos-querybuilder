package com.blackhat.devtools.cosmos;

import com.azure.data.cosmos.SqlQuerySpec;

import java.util.ArrayList;
import java.util.List;

public class FromManager {

    private final CosmosQueryConfiguration cosmosQueryConfiguration;
    private final List<CosmosJoinReference> joins = new ArrayList<>();

    public FromManager(CosmosQueryConfiguration cosmosQueryConfiguration) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    public FromManager join(CosmosJoinReference cosmosJoinReference) {
        this.joins.add(cosmosJoinReference);
        return this;
    }

    public WhereManager where(Condition condition) {
        this.cosmosQueryConfiguration.setJoins(joins);
        return new WhereManager(this.cosmosQueryConfiguration, new ExpressionBuilder(condition).build());
    }

    public WhereManager where(Expression expression) {
        this.cosmosQueryConfiguration.setJoins(joins);
        return new WhereManager(this.cosmosQueryConfiguration, expression);
    }

    public SqlQuerySpec buildQuery() {
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).buildQuery();
    }


}
