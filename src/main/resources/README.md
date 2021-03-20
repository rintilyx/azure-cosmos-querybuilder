# Azure Cosmos Query Builder

## Features
- Write dynamic SQL query for Azure Cosmos using JSON documents.
- You can choose if a condition will be included in query if value is null
- Build simple or complex conditions
- JOINS support
- Supported operators: 
   - EQUALS
   - GT
   - GTE
   - LT
   - LTE
   - CONTAINS
   - STARTSWITH
   - ARRAY_CONTAINS
   - IN

## Families collection data
```JSON
[
    {
        "id": 1,
        "firstName": "Carl",
        "lastName": "Anderson",
        "age": 48,
        "country": "England",
        "childrens": [
            {
                "firstName": "Joe",
                "lastName": "Middleton",
                "age": 15,
                "friends": [
                    {
                        "firstName": "Sienna",
                        "lastName": "Malcom",
                        "age": 16
                    },
                    {
                        "firstName": "Alice",
                        "lastName": "Moody",
                        "age": 14
                    }
                ]
            }
        ]
    },
    {
        "id": 2,
        "firstName": "Walter",
        "lastName": "Anderson",
        "age": 37,
        "country": "Spain",
        "childrens": [
            {
                "firstName": "Mick",
                "lastName": "Rowe",
                "age": 8,
                "friends": [
                    {
                        "firstName": "Lane",
                        "lastName": "Nelson",
                        "age": 10
                    },
                    {
                        "firstName": "Alice",
                        "lastName": "Moody",
                        "age": 9
                    }
                ]
            }
        ]
    }
]
```
## Build query with EQUALS operator
```SQL
SELECT c
FROM Families f
WHERE f.lastName = 'Anderson' AND f.country = 'England'
```
```java
CosmosCollection cosmosCollection = new CosmosCollection("Families");
Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .equalsTo("Andersen");
Condition countryCondition = new ConditionBuilder(cosmosCollection)
        .attribute("country")
        .equalsTo("England");
SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
        .select()
        .from(cosmosCollection)
        .where(lastNameCondition)
        .and(countryCondition)
        .buildQuery();
```

## Build query with STARTSWITH operator
```SQL
SELECT c
FROM Families f
WHERE STARTSWITH(f.lastName, 'Ander')
```
```java
CosmosCollection cosmosCollection = new CosmosCollection("Families");
Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .startsWith("Ander");
SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
        .select()
        .from(cosmosCollection)
        .where(lastNameCondition)
        .buildQuery();
```
## Build query with IN operator
```SQL
SELECT c
FROM Families f
WHERE f.country IN ('England', 'Spain')
```
```java
CosmosCollection cosmosCollection = new CosmosCollection("Families");
Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .in(Arrays.asList("England", "Spain"));
SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
        .select()
        .from(cosmosCollection)
        .where(lastNameCondition)
        .buildQuery();
```

## Build query with GT, GTE, LT, LTE condition
```SQL
SELECT f
FROM Families f
WHERE f.lastName = 'Anderson' AND f.age > 40
```
```java
CosmosCollection cosmosCollection = new CosmosCollection("Families");
Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .equalsTo("Anderson");
Condition ageCondition = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .gt(40);
SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
        .select()
        .from(cosmosCollection)
        .where(lastNameCondition)
        .and(ageCondition)
        .buildQuery();
```

## Build query with CONTAINS operator
```SQL
SELECT f
FROM Families f
WHERE CONTAINS(f.lastName, 'Ander')
```
```java
CosmosCollection cosmosCollection = new CosmosCollection("Families");
Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .contains("Ander");
SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
        .select()
        .from(cosmosCollection)
        .where(lastNameCondition)
        .buildQuery();
```
## Usage of JOIN
```SQL
SELECT f
FROM Families f
JOIN c IN f.childrens
WHERE f.lastName = 'Anderson' AND c.lastName = 'Middleton'
```
```java
CosmosCollection cosmosCollection = new CosmosCollection("Families");
CosmosJoinReference childrenJoin = new CosmosJoinReference(cosmosCollection, "childrens", "c");
Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .equalsTo("Anderson");
Condition childrenCondition = new ConditionBuilder(childrenJoin)
        .attribute("lastName")
        .equalsTo("Middleton");
SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
        .select()
        .from(cosmosCollection)
        .where(lastNameCondition)
        .and(childrenCondition)
        .buildQuery();
```

## Usage of multiple JOINS
```SQL
SELECT f
FROM Families f
JOIN c IN f.childrens
JOIN fr IN c.friends
WHERE f.lastName = 'Anderson' AND c.lastName = 'Middleton' AND fr.lastName = 'Malcolm'
```
```java
CosmosCollection cosmosCollection = new CosmosCollection("Families");

CosmosJoinReference childrenJoin = new CosmosJoinReference(cosmosCollection, "childrens", "c");
CosmosJoinReference friendsJoin = new CosmosJoinReference(childrenJoin, "childrens", "f");

Condition lastNameCondition = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .equalsTo("Anderson");
Condition childrenCondition = new ConditionBuilder(childrenJoin)
        .attribute("lastName")
        .equalsTo("Middleton");
Condition friendCondition = new ConditionBuilder(childrenJoin)
        .attribute("lastName")
        .equalsTo("Moody");
SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
        .select()
        .from(cosmosCollection)
        .join(childrenJoin)
        .join(friendsJoin)
        .where(lastNameCondition)
        .and(childrenCondition)
        .buildQuery();
```

## Usage of ARRAY_CONTAINS
```SQL
SELECT f
FROM Families f
WHERE ARRAY_CONTAINS(f.childrens, {"lastName": "Anderson"}, true)
```

```java
@Getter
@Setter
public class Children {
    private String lastName;
}
// ...
Children children = new Children();
children.setLastName("Middleton");

CosmosCollection cosmosCollection = new CosmosCollection("Families");

Condition childrenCondition = new ConditionBuilder(cosmosCollection)
        .attribute("childrens")
        .arrayContains(children);
        
SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
        .select()
        .from(cosmosCollection)
        .join(childrenJoin)
        .join(friendsJoin)
        .where(childrenCondition)
        .buildQuery();
```

## Build an Expression in AND with a Condition
```SQL
SELECT f
FROM Families f
WHERE ARRAY_CONTAINS(f.childrens, {"lastName": "Anderson"}, true)
```

```java
CosmosCollection cosmosCollection = new CosmosCollection("Families");

Condition lastNameAndersonCondition = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .equalsTo("Anderson");
Condition firstNameCondition1 = new ConditionBuilder(cosmosCollection)
        .attribute("lastName")
        .equalsTo("Anderson");
Expression expression1 = new ExpressionBuilder(cosmosCollection)
        
SqlQuerySpec sqlQuerySpec = new CosmosQuery(cosmosQueryConfiguration)
        .select()
        .from(cosmosCollection)
        .join(childrenJoin)
        .join(friendsJoin)
        .where(childrenCondition)
        .buildQuery();
```

