package com.blackhat.devtools.cosmos;

import java.util.ArrayList;
import java.util.List;

public class FromManager {

    private final CosmosQueryConfiguration cosmosQueryConfiguration;
    private final List<CosmosJoinClause> joins = new ArrayList<>();

    public FromManager(CosmosQueryConfiguration cosmosQueryConfiguration) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    public FromManager join(CosmosJoinClause cosmosJoinClause) {
        this.joins.add(cosmosJoinClause);
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


}
