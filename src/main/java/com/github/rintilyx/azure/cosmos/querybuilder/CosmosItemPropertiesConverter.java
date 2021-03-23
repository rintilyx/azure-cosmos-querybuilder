package com.github.rintilyx.azure.cosmos.querybuilder;

import com.azure.data.cosmos.CosmosItemProperties;

@FunctionalInterface
public interface CosmosItemPropertiesConverter<T> {

    T convert(CosmosItemProperties cosmosItemProperties);

}
