package com.github.rintilyx.azure.cosmos;

import com.azure.data.cosmos.*;
import com.github.rintilyx.azure.cosmos.querybuilder.*;

public class Main {



    public static void main(String... args) {

        CosmosCollection cosmosCollection = new CosmosCollection("POM_PurchaseOrder");
        Condition condition = new ConditionBuilder(cosmosCollection, "purchaseOrderNumber").equalsTo(null);
        Condition expression = new ConditionBuilder(cosmosCollection, "providedProductCodes").arrayContains(null);
        Condition expression2 = new ConditionBuilder(cosmosCollection, "externalPurchaseOrderNumbers").arrayContains("38374");
        Expression expression3 = new ExpressionBuilder(new ConditionBuilder(cosmosCollection, "purchaseOrderTypes").arrayContains("REGULAR_RX"))
                .or(new ConditionBuilder(cosmosCollection, "purchaseOrderTypes").arrayContains("C2"))
                .build();

        Criteria criteria = new CriteriaBuilder()
                .where(condition)
                .and(expression)
                .and(expression2)
                .and(expression3)
                .buildCriteria();

        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(cosmosCollection)
                .where(criteria)
                .orderBy(OrderByClause.of(cosmosCollection, "_ts", SortingCriteria.ASC))
                .offset(null)
                .limit(null)
                .buildQuery();

        System.out.println(query.queryText());
        System.out.println(query.parameters());

    }

    public static void main2(String... args) {

        CosmosDatabase database = new CosmosClientBuilder()
                .endpoint("https://localhost:8081/")
                .key("C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==")
                .build()
                .getDatabase("purchaseordermanagement-3STORES");

        CosmosContainer refDataContainer = database.getContainer("POM_ReferenceData");
        CosmosContainer prodContainer = database.getContainer("POM_Product");

        System.out.println("Inizio...");

        FeedOptions feedOptions = new FeedOptions();
        feedOptions.maxItemCount(-1);
        prodContainer
                .queryItems("SELECT * FROM POM_Product c")
                .subscribe(result -> System.out.println("count: " + result.results().size()));

        while(true);

    }



}
