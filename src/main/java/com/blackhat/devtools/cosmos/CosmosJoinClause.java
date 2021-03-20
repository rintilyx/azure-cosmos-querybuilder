package com.blackhat.devtools.cosmos;

public class CosmosJoinClause extends CosmosReference {

    private CosmosReference parent;

    public CosmosJoinClause(CosmosReference parent, String name, String alias) {
        this.setName(name);
        this.setAlias(alias+"_jref");
        this.parent = parent;
    }

    public CosmosReference getParent() {
        return parent;
    }
}
