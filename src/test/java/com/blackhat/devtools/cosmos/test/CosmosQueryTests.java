package com.blackhat.devtools.cosmos.test;

import com.azure.data.cosmos.CosmosClient;
import com.azure.data.cosmos.SqlQuerySpec;
import com.blackhat.devtools.cosmos.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class CosmosQueryTests {

    @Mock
    private CosmosClient cosmosClient;

    @Test
    public void example1Test() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(countries)
                .buildQuery();

        System.out.println(query.queryText());
    }

    @Test
    public void example2Test() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        // Build condition
        Condition europeCondition = new ConditionBuilder(countries)
                .attribute("name")
                .equalsTo("Europe");

        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(countries)
                .where(europeCondition)
                .buildQuery();
        System.out.println(query.queryText());
    }

    @Test
    public void example3Test() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        // Build condition
        Condition europeCondition = new ConditionBuilder(countries) // This condition will be built using countries collection reference
                .attribute("name")
                .includeIfNull(false) // if name is null, the condition will be removed from query
                .equalsTo("Europe");

        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(countries)
                .where(europeCondition)
                .buildQuery();

        System.out.println(query.queryText());
    }

    @Test
    public void example4Test() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        // Build condition
        Condition nameCondition = new ConditionBuilder(countries)
                .attribute("name")
                .equalsTo("Europe");
        Condition codeCondition = new ConditionBuilder(countries)
                .attribute("code")
                .equalsTo("EU");

        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(countries)
                .where(nameCondition)
                .and(codeCondition)
                .buildQuery();

        System.out.println(query.queryText());
    }

    @Test
    public void example5Test() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        // Build conditions
        Condition idCondition = new ConditionBuilder(countries)
                .attribute("id")
                .equalsTo(1);
        Condition nameCondition = new ConditionBuilder(countries)
                .attribute("name")
                .startsWith("Europe");
        Condition codeCondition = new ConditionBuilder(countries)
                .attribute("code")
                .equalsTo("EU");

        // Build expressions
        Expression nameAndCodeExpression = new ExpressionBuilder(nameCondition).and(codeCondition).build(); // This conditions will be checked together

        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(countries)
                .where(idCondition)
                .or(nameAndCodeExpression)
                .buildQuery();

        System.out.println(query.queryText());
    }

    @Test
    public void example6Test() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        // Define joins
        CosmosJoinReference statesJoin = new CosmosJoinReference(countries, "states", "s");

        // Build conditions
        Condition populationDensityCondition = new ConditionBuilder(statesJoin) // We specify that this condition will be built on join reference
                .attribute("populationDensity")
                .equalsTo(50000000);

        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(countries)
                .join(statesJoin)
                .where(populationDensityCondition)
                .buildQuery();

        System.out.println(query.queryText());
    }

    @Test
    public void example7Test() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        // Define joins
        CosmosJoinReference statesJoin = new CosmosJoinReference(countries, "states", "s");
        CosmosJoinReference citiesJoin = new CosmosJoinReference(statesJoin, "cities", "c");

        // Build conditions
        Condition populationDensityCondition = new ConditionBuilder(citiesJoin) // We specify that this condition will be built on join reference
                .attribute("populationDensity")
                .equalsTo(3500000);

        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(countries)
                .join(statesJoin)
                .join(citiesJoin)
                .where(populationDensityCondition)
                .buildQuery();

        System.out.println(query.queryText());
    }

    @Test
    public void example8Test() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        List<String> fields = Arrays.asList("name", "code");
        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .select(fields)
                .from(countries)
                .buildQuery();

        System.out.println(query.queryText());
    }

    @Test
    public void example9Test() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .count()
                .from(countries)
                .buildQuery();

        System.out.println(query.queryText());
    }


}
