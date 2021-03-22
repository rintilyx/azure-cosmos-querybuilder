package com.github.rintilyx.azure.cosmos;

import com.azure.data.cosmos.CosmosClient;
import com.azure.data.cosmos.CosmosClientBuilder;
import com.azure.data.cosmos.SqlQuerySpec;
import com.github.rintilyx.azure.cosmos.data.Country;
import com.github.rintilyx.azure.cosmos.data.ResultWrapper;
import com.github.rintilyx.azure.cosmos.querybuilder.ConditionBuilder;
import com.github.rintilyx.azure.cosmos.querybuilder.CosmosCollection;
import com.github.rintilyx.azure.cosmos.querybuilder.CosmosQuery;
import com.github.rintilyx.azure.cosmos.querybuilder.CosmosQueryConfiguration;
import com.google.gson.Gson;
import reactor.util.Loggers;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main {


    public static void main(String... args) throws ExecutionException, InterruptedException {

        CosmosCollection cosmosCollection = new CosmosCollection("Countries");


        CosmosClient cosmosClient = new CosmosClientBuilder()
                .endpoint("https://localhost:8081/")
                .key("C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==")
                .build();
//        .getDatabase("DEV_LOCAL")
//                .getContainer("Countries")
//                .queryItems();

        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(cosmosClient);
        cosmosQueryConfiguration.setDatabase("DEV_LOCAL");
        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .where(new ConditionBuilder(cosmosCollection).attribute("name").equalsTo("United States"))
                .buildQuery();

        List<Country> countries = cosmosClient
                .getDatabase("DEV_LOCAL")
                .getContainer("Countries")
                .queryItems(sqlQuerySpec)
                .flatMapIterable(n -> n.results())
                .map(n -> {
                    try {
                        Gson gson = new Gson();
                        ResultWrapper<Country> wrapper = gson.fromJson(n.toJson(), ResultWrapper.class);
                        return wrapper.getC();
                    } catch (Exception ex) {
                        Loggers.getLogger(Main.class).error("err: {}", ex.getMessage());
                        return new Country();
                    }
                })
                .collectList()
                .toFuture()
                .get();


        Loggers.getLogger(Main.class).info("collect: ", countries);
    }


}
