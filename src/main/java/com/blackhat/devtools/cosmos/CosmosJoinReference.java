package com.blackhat.devtools.cosmos;

public class CosmosJoinReference extends CosmosReference {

    private final CosmosReference parent;

    public CosmosJoinReference(CosmosReference parent, String name, String alias) {
        this.setName(name);
        this.setAlias(alias + "_jref");
        this.parent = parent;
    }

    CosmosReference getParent() {
        return this.parent;
    }
}
