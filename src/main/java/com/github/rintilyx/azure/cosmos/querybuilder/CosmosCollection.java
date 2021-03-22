package com.github.rintilyx.azure.cosmos.querybuilder;

import org.apache.commons.lang3.StringUtils;

public class CosmosCollection extends CosmosReference {

    public CosmosCollection(String collectionName, String alias) {
        if(StringUtils.isNotBlank(collectionName)) {
            this.setName(collectionName);
            this.setAlias(alias);
        } else {
            throw new RuntimeException("CollectionName cannot be blank");
        }
    }

    public CosmosCollection(String collectionName) {
        if(StringUtils.isNotBlank(collectionName)) {
            this.setName(collectionName);
            this.setAlias(String.valueOf(collectionName.toLowerCase().charAt(0)));
        } else {
            throw new RuntimeException("CollectionName cannot be blank");
        }
    }

}
