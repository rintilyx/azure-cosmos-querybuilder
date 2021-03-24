package com.github.rintilyx.azure.cosmos;

import com.azure.data.cosmos.CosmosClient;
import com.azure.data.cosmos.CosmosClientBuilder;
import com.azure.data.cosmos.FeedOptions;
import com.azure.data.cosmos.PartitionKey;
import com.github.rintilyx.azure.cosmos.data.Country;
import com.github.rintilyx.azure.cosmos.data.ProductContract;
import com.github.rintilyx.azure.cosmos.querybuilder.CosmosCollection;
import com.github.rintilyx.azure.cosmos.querybuilder.CosmosQuery;
import com.github.rintilyx.azure.cosmos.querybuilder.CosmosQueryConfiguration;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {


    public static void main(String... args) throws ExecutionException, InterruptedException {
        CosmosCollection cosmosCollection = new CosmosCollection("POM_Product");

        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint("https://localhost:8081/")
                .key("C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==")
                .build();

        System.out.println("Connected to Cosmos");

        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(cosmosClient);
        cosmosQueryConfiguration.setDatabase("DEV_LOCAL");
        List<ProductContract> products = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                //.where(new ConditionBuilder(cosmosCollection).attribute("name").equalsTo("United States"))
                .queryItemsSync(new FeedOptions().partitionKey(new PartitionKey("PRODUCT")), ProductContract.class);

        System.out.println(new Gson().toJson(products));
        return;
    }


    public static void main3(String... args) {
        CosmosCollection cosmosCollection = new CosmosCollection("Countries");

        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint("https://localhost:8081/")
                .key("C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==")
                .build();

        System.out.println("Connected to Cosmos");

        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(cosmosClient);
        cosmosQueryConfiguration.setDatabase("DEV_LOCAL");
        List<Country> products = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                //.where(new ConditionBuilder(cosmosCollection).attribute("name").equalsTo("United States"))
                .queryItemsSync(new FeedOptions(), Country.class);

        System.out.println(new Gson().toJson(products));
        return;
    }


}
