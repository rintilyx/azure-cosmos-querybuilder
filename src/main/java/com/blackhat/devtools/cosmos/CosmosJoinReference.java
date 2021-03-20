package com.blackhat.devtools.cosmos;

public class CosmosJoinReference extends CosmosReference {

    private CosmosReference parent;

    public CosmosJoinReference(CosmosReference parent, String name, String alias) {
        this.setName(name);
        this.setAlias(alias + "_jref");
        this.parent = parent;
    }

    public CosmosReference getParent() {
        return parent;
    }
}
