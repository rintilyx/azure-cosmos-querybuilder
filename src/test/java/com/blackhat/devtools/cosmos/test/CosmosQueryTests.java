package com.blackhat.devtools.cosmos.test;

import com.azure.data.cosmos.SqlQuerySpec;
import com.blackhat.devtools.cosmos.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class CosmosQueryTests {

    @Test
    public void oneConditionTest() {
        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(null);
        cosmosQueryConfiguration.setLogger(log);
        cosmosQueryConfiguration.setDatabase("myapp-db");
        cosmosQueryConfiguration.setMaxAttempts(3);
        cosmosQueryConfiguration.setOnRetryFixedDelay(Duration.ofSeconds(1));

        CosmosCollection cosmosCollection = new CosmosCollection("Families");

        Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Andersen");

        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .where(lastNameCondition)
                .buildQuery();

        System.out.println(sqlQuerySpec.queryText());
    }

    @Test
    public void twoConditionInAndTest() {
        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(null);
        cosmosQueryConfiguration.setLogger(log);
        cosmosQueryConfiguration.setDatabase("myapp-db");
        cosmosQueryConfiguration.setMaxAttempts(3);
        cosmosQueryConfiguration.setOnRetryFixedDelay(Duration.ofSeconds(1));

        CosmosCollection cosmosCollection = new CosmosCollection("Families");

        Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Andersen");

        Condition countryCondition = new ConditionBuilder(cosmosCollection)
                .attribute("country")
                .equalsTo("Italy");

        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .where(lastNameCondition)
                .and(countryCondition)
                .buildQuery();

        System.out.println(sqlQuerySpec.queryText());
    }

    @Test
    public void twoConditionInOrTest() {
        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(null);
        cosmosQueryConfiguration.setLogger(log);
        cosmosQueryConfiguration.setDatabase("myapp-db");
        cosmosQueryConfiguration.setMaxAttempts(3);
        cosmosQueryConfiguration.setOnRetryFixedDelay(Duration.ofSeconds(1));

        CosmosCollection cosmosCollection = new CosmosCollection("Families");

        Condition andersenCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Andersen");

        Condition rossiCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Rossi");

        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .where(andersenCondition)
                .or(rossiCondition)
                .buildQuery();

        System.out.println(sqlQuerySpec.queryText());
    }


    @Test
    public void oneExpressionTest() {
        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(null);
        cosmosQueryConfiguration.setLogger(log);
        cosmosQueryConfiguration.setDatabase("myapp-db");
        cosmosQueryConfiguration.setMaxAttempts(3);
        cosmosQueryConfiguration.setOnRetryFixedDelay(Duration.ofSeconds(1));

        CosmosCollection cosmosCollection = new CosmosCollection("Families");

        Condition andersenCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Andersen");
        Condition rossiCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Rossi");
        Condition countryCondition = new ConditionBuilder(cosmosCollection)
                .attribute("country")
                .equalsTo("Italy");

        Expression lastNameExpression = new ExpressionBuilder(andersenCondition).or(rossiCondition).build();

        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .where(lastNameExpression)
                .and(countryCondition)
                .buildQuery();

        System.out.println(sqlQuerySpec.queryText());
    }

    @Test
    public void twoExpressionTest() {
        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(null);
        cosmosQueryConfiguration.setLogger(log);
        cosmosQueryConfiguration.setDatabase("myapp-db");
        cosmosQueryConfiguration.setMaxAttempts(3);
        cosmosQueryConfiguration.setOnRetryFixedDelay(Duration.ofSeconds(1));

        CosmosCollection cosmosCollection = new CosmosCollection("Families");

        Condition andersenCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Andersen");
        Condition rossiCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Rossi");
        Condition countryItalyCondition = new ConditionBuilder(cosmosCollection)
                .attribute("country")
                .equalsTo("Italy");
        Condition countrySpainCondition = new ConditionBuilder(cosmosCollection)
                .attribute("country")
                .equalsTo("Spain");

        Expression lastNameExpression = new ExpressionBuilder(andersenCondition).or(rossiCondition).build();
        Expression countryExpression = new ExpressionBuilder(countryItalyCondition).or(countrySpainCondition).build();

        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .where(lastNameExpression)
                .and(countryExpression)
                .buildQuery();

        System.out.println(sqlQuerySpec.queryText());
    }

    @Test
    public void oneJoinTest() {
        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(null);
        cosmosQueryConfiguration.setLogger(log);
        cosmosQueryConfiguration.setDatabase("myapp-db");
        cosmosQueryConfiguration.setMaxAttempts(3);
        cosmosQueryConfiguration.setOnRetryFixedDelay(Duration.ofSeconds(1));

        CosmosCollection cosmosCollection = new CosmosCollection("Families");
        CosmosJoinClause childrensJoin = new CosmosJoinClause(cosmosCollection,"childrens", "c");

        Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Andersen");

        Condition childrenCondition = new ConditionBuilder(childrensJoin)
                .attribute("firstName")
                .equalsTo("Carl");

        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .join(childrensJoin)
                .where(lastNameCondition)
                .and(childrenCondition)
                .buildQuery();

        System.out.println(sqlQuerySpec.queryText());
    }

    @Test
    public void nestedJoinTest() {
        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(null);
        cosmosQueryConfiguration.setLogger(log);
        cosmosQueryConfiguration.setDatabase("myapp-db");
        cosmosQueryConfiguration.setMaxAttempts(3);
        cosmosQueryConfiguration.setOnRetryFixedDelay(Duration.ofSeconds(1));

        CosmosCollection cosmosCollection = new CosmosCollection("Families");
        CosmosJoinClause childrensJoin = new CosmosJoinClause(cosmosCollection,"childrens", "c");
        CosmosJoinClause childrensFriendsJoin = new CosmosJoinClause(childrensJoin,"friends", "f");

        Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Andersen");

        Condition childrenCondition = new ConditionBuilder(childrensJoin)
                .attribute("firstName")
                .equalsTo("Carl");

        Condition childrenFriendsCondition = new ConditionBuilder(childrensFriendsJoin)
                .attribute("age")
                .equalsTo(18);

        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .join(childrensJoin)
                .join(childrensFriendsJoin)
                .where(lastNameCondition)
                .and(childrenCondition)
                .and(childrenFriendsCondition)
                .buildQuery();

        System.out.println(sqlQuerySpec.queryText());
    }

    @Test
    public void offsetLimitTest() {
        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(null);
        cosmosQueryConfiguration.setLogger(log);
        cosmosQueryConfiguration.setDatabase("myapp-db");
        cosmosQueryConfiguration.setMaxAttempts(3);
        cosmosQueryConfiguration.setOnRetryFixedDelay(Duration.ofSeconds(1));

        CosmosCollection cosmosCollection = new CosmosCollection("Families");
        CosmosJoinClause childrensJoin = new CosmosJoinClause(cosmosCollection, "childrens", "c");

        Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Andersen");

        Condition childrenCondition = new ConditionBuilder(childrensJoin)
                .attribute("firstName")
                .equalsTo("Carl");

        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .join(childrensJoin)
                .where(lastNameCondition)
                .and(childrenCondition)
                .offset(10)
                .limit(5)
                .buildQuery();

        System.out.println(sqlQuerySpec.queryText());
    }

    @Test
    public void orderByTest() {
        CosmosQueryConfiguration cosmosQueryConfiguration = new CosmosQueryConfiguration();
        cosmosQueryConfiguration.setCosmosClient(null);
        cosmosQueryConfiguration.setLogger(log);
        cosmosQueryConfiguration.setDatabase("myapp-db");
        cosmosQueryConfiguration.setMaxAttempts(3);
        cosmosQueryConfiguration.setOnRetryFixedDelay(Duration.ofSeconds(1));

        CosmosCollection cosmosCollection = new CosmosCollection("Families");
        CosmosJoinClause childrensJoin = new CosmosJoinClause(cosmosCollection, "childrens", "c");

        Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
                .attribute("lastName")
                .equalsTo("Andersen");

        Condition childrenCondition = new ConditionBuilder(childrensJoin)
                .attribute("firstName")
                .equalsTo("Carl");

        SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
                .select()
                .from(cosmosCollection)
                .join(childrensJoin)
                .where(lastNameCondition)
                .and(childrenCondition)
                .orderBy(new OrderByClause("lastName", SortingCriteria.ASC), new OrderByClause("createdAt", SortingCriteria.DESC))
                .offset(10)
                .limit(5)
                .buildQuery();

        System.out.println(sqlQuerySpec.queryText());
    }


}
