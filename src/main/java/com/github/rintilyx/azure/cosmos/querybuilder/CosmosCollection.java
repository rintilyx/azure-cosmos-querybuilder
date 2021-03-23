package com.github.rintilyx.azure.cosmos.querybuilder;

import org.apache.commons.lang3.StringUtils;

public class CosmosCollection extends CosmosReference {

    private static final String DEFAULT_ALIAS = "c";

    public CosmosCollection(String collectionName) {
        if(StringUtils.isNotBlank(collectionName)) {
            this.setName(collectionName);
            this.setAlias(DEFAULT_ALIAS);
        } else {
            throw new CosmosQueryBuilderException("CollectionName cannot be blank");
        }
    }

}
