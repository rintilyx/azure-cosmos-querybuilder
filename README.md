# Azure Cosmos Query Builder

## Features
- Build dynamic SQL query for Azure Cosmos.
- You can choose if a condition will be included in query if value is null
- Build simple or complex expressions
- single or multiple JOIN support
- Supported operators (and all related negated forms):
   - EQUALS
   - GT
   - GTE
   - LT
   - LTE
   - CONTAINS
   - STARTSWITH
   - ARRAY_CONTAINS
   - IN
   - LIKE
   - IS_ARRAY
   - IS_DEFINED
   - IS_BOOL
   - IS_NULL
   - IS_OBJECT
   - IS_PRIMITIVE
   - IS_NUMBER
   - IS_PRIMITIVE
   
## 1. What you can do?
In order to explain what you can do with this library, we can use this JSON document that represent an array of countries as example

### Countries collection data
```JSON
[
    {
        "id": 1,
        "name": "United States",
        "code": "US",
        "states": [
            {
                "name": "Illinois",
                "populationDensity": 12671821,
                "cities": [
                    {
                        "name": "Chicago",
                        "populationDensity": 2693976
                    } 
                ]
            }
        ]
    }
]
```

### Example 1: Read all data from collection
```java

public void readData() {
    SqlQuerySpec query = buildQuery();

    cosmosClient.getDatabase("mydatabase")
        .getContainer("Countries")
        .queryItems(query);
}

public void buildQuery() {
    // Define collection
    CosmosCollection countries = new CosmosCollection("Countries");
    
    // Build query
    SqlQuerySpec query = new CosmosQuery()
            .select()
            .from(countries)
            .buildQuery();
}
```
Result
```SQL
SELECT c
FROM Countries c
```


### Example 2: Use a Condition

#### How to build a Condition
```java
CosmosCollection countries = new CosmosCollection("Countries");

Condition condition = new ConditionBuilder(countries)
                .attribute("name")
                .equalsTo(name);
```

#### Full example
```java
public void readData() {
    SqlQuerySpec query = buildQuery("United States"); // Search United States from Countries

    cosmosClient.getDatabase("mydatabase")
        .getContainer("Countries")
        .queryItems(query);
}

public SqlQuerySpec buildQuery(String name) {
    // Define collection
    CosmosCollection countries = new CosmosCollection("Countries");

    // Build condition
    Condition United StatesCondition = new ConditionBuilder(countries)
                    .attribute("name")
                    .equalsTo(name);

    // Build query
    return new CosmosQuery()
            .select()
            .from(countries)
            .where(United StatesCondition)
            .buildQuery();
}
```
Result
```SQL
SELECT c
FROM Countries c
WHERE c.name = "United States"
```

### Example 3: Exclude Condition if value is null
If you need to build dynamic query, excluding null values from condition, you can use .includeIfNull(true/false) attribute provided by ConditionBuilder (default value is false)
```java
public void readData() {
    SqlQuerySpec query = buildQuery(null); // Pass null to method

    cosmosClient.getDatabase("mydatabase")
        .getContainer("Countries")
        .queryItems(query);
}

public SqlQuerySpec buildQuery(String name) {
    // Define collection
    CosmosCollection countries = new CosmosCollection("Countries");

    // Build condition
    Condition United StatesCondition = new ConditionBuilder(countries) // This condition will be built using countries collection reference
                    .attribute("name")
                    .includeIfNull(false) // if name is null, the condition will be removed from query
                    .equalsTo(name);

    // Build query
    return new CosmosQuery()
            .select()
            .from(countries)
            .where(United StatesCondition)
            .buildQuery();
}
```
Result
```SQL
SELECT c
FROM Countries c
```
If a Condition contains null as value it will be removed from query

### Example 4: Include Condition if value is null
If you need to build dynamic query, excluding null values from condition, you can use .includeIfNull(true/false) attribute provided by ConditionBuilder (default value is false)
```java
public void readData() {
    SqlQuerySpec query = buildQuery(null); // Pass null to method

    cosmosClient.getDatabase("mydatabase")
        .getContainer("Countries")
        .queryItems(query);
}

public SqlQuerySpec buildQuery(String name) {
    // Define collection
    CosmosCollection countries = new CosmosCollection("Countries");

    // Build condition
    Condition United StatesCondition = new ConditionBuilder(countries) // This condition will be built using countries collection reference
                    .attribute("name")
                    .includeIfNull(true) // if name is null, the condition will be removed from query
                    .equalsTo(name);

    // Build query
    return new CosmosQuery()
            .select()
            .from(countries)
            .where(United StatesCondition)
            .buildQuery();
}
```
Result
```SQL
SELECT c
FROM Countries c
WHERE c.name = null
```
If a Condition contains null as value it will be removed from query

### Example 5: Combine multiple Conditions
Search Countries having "United States" as name and "US" as code.
```java
public void readData() {
    SqlQuerySpec query = buildQuery("United States", "US");

    cosmosClient.getDatabase("mydatabase")
        .getContainer("Countries")
        .queryItems(query);
}

public SqlQuerySpec buildQuery(String name, String code) {
    // Define collection
    CosmosCollection countries = new CosmosCollection("Countries");

    // Build condition
    Condition nameCondition = new ConditionBuilder(countries)
                    .attribute("name")
                    .equalsTo(name);
    Condition codeCondition = new ConditionBuilder(countries)
                        .attribute("code")
                        .equalsTo(code);

    // Build query
    return new CosmosQuery()
            .select()
            .from(countries)
            .where(nameCondition)
            .and(codeCondition)
            .buildQuery();
}
```
Result
```SQL
SELECT c
FROM Countries c
WHERE c.name = "United States" AND c.code = "US"
```


### Example 6: Introduction to Expression (multiple conditions binding)
If you want to check a tuple of conditions with another one, you can use the Expressions!

#### How to build an Expression
```java
CosmosCollection countries = new CosmosCollection("Countries");

Condition condition1 = new ConditionBuilder(countries)
                .attribute("id")
                .equalsTo(1);
Condition condition2 = new ConditionBuilder(countries)
                .attribute("name")
                .equalsTo("United States");
Condition condition3 = new ConditionBuilder(countries)
                .attribute("code")
                .equalsTo("US");

new ExpressionBuilder(condition1).and(condition2).or(condition3).build(); // You can aggregate all conditions in order to check togheter all of them

```

In this example we want to find countries with id = 1 OR (name startsWith "United" AND code equals to "US")

```java
public void readData() {
    SqlQuerySpec query = buildQuery(1, "United", "US");

    cosmosClient.getDatabase("mydatabase")
        .getContainer("Countries")
        .queryItems(query);
}

public SqlQuerySpec buildQuery(Integer id, String name, String code) {
    // Define collection
    CosmosCollection countries = new CosmosCollection("Countries");

    // Build conditions
    Condition idCondition = new ConditionBuilder(countries)
                        .attribute("id")
                        .equalsTo(id);
    Condition nameCondition = new ConditionBuilder(countries)
                        .attribute("name")
                        .startsWith(name);
    Condition codeCondition = new ConditionBuilder(countries)
                        .attribute("code")
                        .equalsTo(code);
    
    // Build expressions
    Expression nameAndCodeExpression = new ExpressionBuilder(nameCondition).and(codeCondition).build(); // This conditions will be checked together

    // Build query
    return new CosmosQuery()
            .select()
            .from(countries)
            .where(idCondition)
            .or(nameAndCodeExpression)
            .buildQuery();
}
```
Result
```SQL
SELECT c
FROM Countries c
WHERE c.id = 1 OR (STARTSWITH(c.name, "United States") AND c.code = "US")
```


### Example 7: Introduction to JOIN
In this example we want to find Country having states with a populationDensity > 50.000.000
```java
public void readData() {
    SqlQuerySpec query = buildQuery(50000000);

    cosmosClient.getDatabase("mydatabase")
        .getContainer("Countries")
        .queryItems(query);
}

public SqlQuerySpec buildQuery(Integer populationDensity) {
    // Define collection
    CosmosCollection countries = new CosmosCollection("Countries");

    // Define joins
    CosmosJoinReference statesJoin = new CosmosJoinReference(countries, "states", "s");

    // Build conditions
    Condition populationDensityCondition = new ConditionBuilder(statesJoin) // We specify that this condition will be built on join reference
                        .attribute("populationDensity")
                        .gt(populationDensity);

    // Build query
    return new CosmosQuery()
            .select()
            .from(countries)
            .join(statesJoin)
            .where(populationDensityCondition)
            .buildQuery();
}
```
Result
```SQL
SELECT c
FROM Countries c
JOIN s_jref IN c.states
WHERE s_jref.populationDensity > 50000000
```

### Example 8: Usage of multiple JOINS
In this example we want to search countries having a cities with populationDensity > 3.500.000
```java
public void readData() {
    SqlQuerySpec query = buildQuery(3500000);

    cosmosClient.getDatabase("mydatabase")
        .getContainer("Countries")
        .queryItems(query);
}

public SqlQuerySpec buildQuery(Integer populationDensity) {
    // Define collection
    CosmosCollection countries = new CosmosCollection("Countries");

    // Define joins
    CosmosJoinReference statesJoin = new CosmosJoinReference(countries, "states", "s");
    CosmosJoinReference citiesJoin = new CosmosJoinReference(statesJoin, "cities", "c");

    // Build conditions
    Condition populationDensityCondition = new ConditionBuilder(citiesJoin) // We specify that this condition will be built on join reference
                        .attribute("populationDensity")
                        .gt(populationDensity);

    // Build query
    return new CosmosQuery()
            .select()
            .from(countries)
            .join(statesJoin)
            .join(citiesJoin)
            .where(populationDensityCondition)
            .buildQuery();
}
```
Result
```SQL
SELECT c
FROM Countries c
JOIN s_jref IN c.states
JOIN c_jref IN s_jref.cities
WHERE c_jref.populationDensity > 3500000
```

### Example 9: Select partial fields
```java
    List<String> fields = Arrays.asList("name","code");
    // Build query
    return new CosmosQuery()
            .select(fields)
            .from(countries)
            .buildQuery();
    // OR ...
    return new CosmosQuery()
            .select("name", "code")
            .from(countries)
            .buildQuery();
}
```
Result
```SQL
SELECT c.name, c.code
FROM Countries c
```


### EXTRA: Example with multiple conditions and expressions
```java
new CosmosQuery()
        .select()
        .from(collection)
        .where(condition1)
        .and(condition2)
        .and(condition3)
        .and(expression1)
        .or(expression2)
        .buildQuery();
```
Result
```sql
SELECT c
FROM Countries c
WHERE c.attribute1 = @condition1
AND c.attribute2 = @condition2
AND c.attribute3 = @condition3
AND (c.attribute4 = @ex1_condition_1 AND c.attribute5 = @ex1_condition_2)
OR (c.attribute6 = @ex2_condition_1 AND c.attribute7 = @ex2_condition_2)
```
