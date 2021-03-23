package com.github.rintilyx.azure.cosmos;

import com.azure.data.cosmos.CosmosClient;
import com.azure.data.cosmos.CosmosClientBuilder;
import com.azure.data.cosmos.FeedOptions;
import com.github.rintilyx.azure.cosmos.querybuilder.CosmosCollection;
import com.github.rintilyx.azure.cosmos.querybuilder.CosmosQuery;
import com.github.rintilyx.azure.cosmos.querybuilder.CosmosQueryConfiguration;

import java.util.concurrent.ExecutionException;

public class Main {


    public static void main(String... args) throws ExecutionException, InterruptedException {

        CosmosCollection cosmosCollection = new CosmosCollection("Countries");


        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint("https://localhost:8081/")
                .key("C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==")
                .build();

        System.out.println("Connected to Cosmos");

        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(cosmosClient);
        cosmosQueryConfiguration.setDatabase("3STORES");
        Long count = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                //.where(new ConditionBuilder(cosmosCollection).attribute("name").equalsTo("United States"))
                .countItemsSync(new FeedOptions());

        System.out.println("Finished :" + count);
        return;
    }


}
