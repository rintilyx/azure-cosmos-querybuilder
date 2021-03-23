package com.github.rintilyx.azure.cosmos.test;

import com.azure.data.cosmos.CosmosClient;
import com.azure.data.cosmos.SqlQuerySpec;
import com.github.rintilyx.azure.cosmos.querybuilder.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
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
        Condition europeCondition = new ConditionBuilder(countries, "name")
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
        Condition europeCondition = new ConditionBuilder(countries, "name") // This condition will be built using countries collection reference
                .equalsTo(null);

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
        Condition nameCondition = new ConditionBuilder(countries, "name")
                .equalsTo("Europe");
        Condition codeCondition = new ConditionBuilder(countries, "code")
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
        Condition idCondition = new ConditionBuilder(countries, "id")
                .equalsTo(1);
        Condition nameCondition = new ConditionBuilder(countries, "name")
                .startsWith("Europe");
        Condition codeCondition = new ConditionBuilder(countries, "code")
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
        Condition populationDensityCondition = new ConditionBuilder(statesJoin, "populationDensity") // We specify that this condition will be built on join reference
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
        Condition populationDensityCondition = new ConditionBuilder(citiesJoin, "populationDensity") // We specify that this condition will be built on join reference
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
                .select()
                .from(countries)
                .buildQuery();

        System.out.println(query.queryText());
    }

    @Test
    public void arrayContainsNegatedTest() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        State state = new State();
        state.setName("Europe");

        Condition condition = new ConditionBuilder(countries, "states")
                .arrayContains(state, true);

        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(countries)
                .where(condition)
                .buildQuery();

        System.out.println(query.queryText());
    }

    @Test
    public void arrayContainsTest() {
        // Define collection
        CosmosCollection countries = new CosmosCollection("Countries");

        State state = new State();
        state.setName("Europe");

        Condition condition = new ConditionBuilder(countries, "states")
                .arrayContains(state, true);

        // Build query
        SqlQuerySpec query = new CosmosQuery()
                .select()
                .from(countries)
                .where(condition)
                .buildQuery();

        System.out.println(query.queryText());
    }


    private static class State {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
