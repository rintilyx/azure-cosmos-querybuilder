package com.github.rintilyx.azure.cosmos.querybuilder;

import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible to build a Condition to add in WHERE clause
 */
public class ConditionBuilder {

    private final CosmosReference cosmosReference;
    private final String attribute;
    private Object value;
    private ComparisonOperator comparisonOperator;
    private Boolean includeIfNullIndicator = false;
    private Boolean boolExpr;

    public ConditionBuilder(CosmosReference cosmosReference, String attribute) {
        this.cosmosReference = cosmosReference;
        this.attribute = attribute;
    }

    /**
     * If you set this indicator to "true", this condition will be included to final query though the provided value is null
     * If you set this indicator to "false", this condition will NOT be included to final query if the provided value is null
     *
     * @param includeIfNullIndicator indicates if condition must be/not be included in query if value is null
     * @return this class
     */
    public ConditionBuilder(CosmosReference cosmosReference, String attribute, Boolean includeIfNullIndicator) {
        this.cosmosReference = cosmosReference;
        this.attribute = attribute;
        this.includeIfNullIndicator = includeIfNullIndicator;
    }


    /**
     * Create a condition using = operator
     * <p>
     * Final result will be: c.country = "Europe"
     * <p>
     * For more details please visit: https://docs.microsoft.com/it-it/azure/cosmos-db/sql-query-operators#equality-and-comparison-operators
     *
     * @param value indicates the value to be checked using EQUALS operator
     * @return a Condition properly built
     */
    public Condition equalsTo(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.EQUALS;
        return buildCondition();
    }

    /**
     * This is the negated expression of EQUALS
     *
     */
    public Condition notEqualsTo(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.NOT_EQUALS;
        return buildCondition();
    }

    /**
     * Create a condition using LIKE operator
     * <p>
     * Final result will be: c.country LIKE "%urope"
     * <p>
     * For more details please visit: https://devblogs.microsoft.com/cosmosdb/like-keyword-cosmosdb/
     *
     * @param value indicates the value to be checked using LIKE operator
     * @return a Condition properly built
     */
    public Condition like(String value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.LIKE;
        return this.buildCondition();
    }

    /**
     * This is the negated expression of LIKE
     *
     */
    public Condition notLike(String value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.NOT_LIKE;
        return this.buildCondition();
    }

    /**
     * Create a condition using ARRAY_CONTAINS operator
     * <p>
     * Final result will be: ARRAY_CONTAINS(c.states, {"name":"Italy"})
     * <p>
     * For more details please visit: https://docs.microsoft.com/it-it/azure/cosmos-db/sql-query-array-contains
     *
     * @param value indicates the value to be checked using ARRAY_CONTAINS operator
     * @return a Condition properly built
     */
    public Condition arrayContains(Object value) {
        return this.arrayContains(value, null);
    }

    /**
     * This is the negated expression of ARRAY_CONTAINS
     *
     * @param value indicates the value to be checked using NOT ARRAY_CONTAINS operator
     * @return a Condition properly built
     */
    public Condition notArrayContains(Object value) {
        return this.notArrayContains(value, null);
    }


    /**
     * Create a condition using ARRAY_CONTAINS operator
     * <p>
     * Final result will be: ARRAY_CONTAINS(c.states, {"name":"Italy"}, true)
     * <p>
     * For more details please visit: https://docs.microsoft.com/it-it/azure/cosmos-db/sql-query-array-contains
     *
     * @param value    indicates the value to be checked using ARRAY_CONTAINS operator
     * @param boolExpr Is a boolean expression.
     *                 If it evaluates to 'true' and if the specified search value is an object, the command checks for a partial match (the search object is a subset of one of the objects).
     *                 If it evaluates to 'false', the command checks for a full match of all objects within the array. The default value if not specified is false.
     * @return a Condition properly built
     */
    public Condition arrayContains(Object value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.ARRAY_CONTAINS;
        this.boolExpr = boolExpr;
        return buildCondition();
    }

    /**
     * This is the negated expression of ARRAY_CONTAINS
     *
     * @param value indicates the value to be checked using NOT ARRAY_CONTAINS operator
     * @return a Condition properly built
     */
    public Condition notArrayContains(Object value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.NOT_ARRAY_CONTAINS;
        this.boolExpr = boolExpr;
        return buildCondition();
    }


    /**
     * Create a condition using IN operator
     * <p>
     * Final result will be: IN ("A", "B", "C")
     * <p>
     * Use the IN keyword to check whether a specified value matches any value in a list.
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-keywords#in
     *
     * @param values indicates the values to be included in IN operator
     * @return a Condition properly built
     */
    public Condition in(List<Object> values) {
        this.value = values;
        this.comparisonOperator = ComparisonOperator.IN;
        return buildCondition();
    }

    /**
     * This is the negated expression of IN
     *
     * @param values indicates the values to be included in IN operator
     * @return a Condition properly built
     */
    public Condition notIn(List<Object> values) {
        this.value = values;
        this.comparisonOperator = ComparisonOperator.NOT_IN;
        return buildCondition();
    }

    /**
     * Create a condition using IN operator
     * <p>
     * Final result will be: IN ("A", "B", "C")
     * <p>
     * Use the IN keyword to check whether a specified value matches any value in a list.
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-keywords#in
     *
     * @param values indicates the values to be included in IN operator
     * @return a Condition properly built
     */
    public Condition in(Object... values) {
        return this.in(Arrays.asList(values));
    }

    /**
     * This is the negated expression of IN
     *
     * @param values indicates the values to be included in IN operator
     * @return a Condition properly built
     */
    public Condition notIn(Object... values) {
        return this.notIn(Arrays.asList(values));
    }

    /**
     * Create a condition using CONTAINS operator
     * <p>
     * Final result will be: CONTAINS(c.name, "Euro")
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-contains
     *
     * @param value indicates the value to be checked using CONTAINS operator
     * @return a Condition properly built
     */
    public Condition contains(String value) {
        return this.contains(value, null);
    }

    /**
     * This is the negated expression of CONTAINS
     *
     * @param value indicates the value to be checked using CONTAINS operator
     * @return a Condition properly built
     */
    public Condition notContains(String value) {
        return this.notContains(value, null);
    }

    /**
     * Create a condition using CONTAINS operator
     * <p>
     * Final result will be: CONTAINS(c.name, "Euro")
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-contains
     *
     * @param value    indicates the value to be checked using CONTAINS operator
     * @param boolExpr Optional value for ignoring case. When set to true, CONTAINS will do a case-insensitive search. When unspecified, this value is false.
     * @return a Condition properly built
     */
    public Condition contains(String value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.CONTAINS;
        this.boolExpr = boolExpr;
        return buildCondition();
    }

    /**
     * This is the negated expression of CONTAINS
     *
     * @param value    indicates the value to be checked using CONTAINS operator
     * @param boolExpr Optional value for ignoring case. When set to true, CONTAINS will do a case-insensitive search. When unspecified, this value is false.
     * @return a Condition properly built
     */
    public Condition notContains(String value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.NOT_CONTAINS;
        this.boolExpr = boolExpr;
        return buildCondition();
    }


    /**
     * Create a condition using STARTSWITH operator
     * <p>
     * Final result will be: STARTSWITH(c.name, "Euro")
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-startswith
     *
     * @param value indicates the value to be checked using STARTSWITH operator
     * @return a Condition properly built
     */
    public Condition startsWith(String value) {
        return this.startsWith(value, null);
    }

    /**
     * This is the negated expression of STARTSWITH
     *
     * @param value indicates the value to be checked using STARTSWITH operator
     * @return a Condition properly built
     */
    public Condition notStartsWith(String value) {
        return this.notStartsWith(value, null);
    }

    /**
     * Create a condition using STARTSWITH operator
     * <p>
     * Final result will be: STARTSWITH(c.name, "Euro", true)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-startswith
     *
     * @param value    indicates the value to be checked using STARTSWITH operator
     * @param boolExpr Optional value for ignoring case. When set to true, STARTSWITH will do a case-insensitive search. When unspecified, this value is false.
     * @return a Condition properly built
     */
    public Condition startsWith(String value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.STARTS_WITH;
        this.boolExpr = boolExpr;
        return buildCondition();
    }

    /**
     * This is the negated expression of STARTSWITH
     *
     * @param value    indicates the value to be checked using STARTSWITH operator
     * @param boolExpr Optional value for ignoring case. When set to true, STARTSWITH will do a case-insensitive search. When unspecified, this value is false.
     * @return a Condition properly built
     */
    public Condition notStartsWith(String value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.NOT_STARTS_WITH;
        this.boolExpr = boolExpr;
        return buildCondition();
    }

    /**
     * Create a condition using ENDSWITH operator
     * <p>
     * Final result will be: ENDSWITH(c.name, "ope")
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-endswith
     *
     * @param value indicates the value to be checked using ENDSWITH operator
     * @return a Condition properly built
     */
    public Condition endsWith(String value) {
        return this.endsWith(value, null);
    }


    /**
     * This is the negated expression of ENDSWITH
     *
     * @param value indicates the value to be checked using ENDSWITH operator
     * @return a Condition properly built
     */
    public Condition notEndsWith(String value) {
        return this.notEndsWith(value, null);
    }


    /**
     * Create a condition using ENDSWITH operator
     * <p>
     * Final result will be: ENDSWITH(c.name, "ope", true)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-endswith
     *
     * @param value    indicates the value to be checked using ENDSWITH operator
     * @param boolExpr Optional value for ignoring case. When set to true, ENDSWITH will do a case-insensitive search. When unspecified, this value is false.
     * @return a Condition properly built
     */
    public Condition endsWith(String value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.ENDS_WITH;
        this.boolExpr = boolExpr;
        return buildCondition();
    }


    /**
     * This is the negated expression of ENDSWITH
     *
     * @param value    indicates the value to be checked using ENDSWITH operator
     * @param boolExpr Optional value for ignoring case. When set to true, ENDSWITH will do a case-insensitive search. When unspecified, this value is false.
     * @return a Condition properly built
     */
    public Condition notEndsWith(String value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.NOT_ENDS_WITH;
        this.boolExpr = boolExpr;
        return buildCondition();
    }


    /**
     * Create a condition using STRINGEQUALS operator
     * <p>
     * Final result will be: STRINGEQUALS(c.name, "Europe")
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-stringequals
     *
     * @param value indicates the value to be checked using STRINGEQUALS operator
     * @return a Condition properly built
     */
    public Condition stringEquals(String value) {
        return this.stringEquals(value, null);
    }

    /**
     * This is the negated expression of STRINGEQUALS
     *
     * @param value indicates the value to be checked using STRINGEQUALS operator
     * @return a Condition properly built
     */
    public Condition notStringEquals(String value) {
        return this.notStringEquals(value, null);
    }

    /**
     * Create a condition using STRINGEQUALS operator
     * <p>
     * Final result will be: STRINGEQUALS(c.name, "Europe")
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-stringequals
     *
     * @param value    indicates the value to be checked using StringEquals operator
     * @param boolExpr Optional value for ignoring case. When set to true, StringEquals will do a case-insensitive search. When unspecified, this value is false.
     * @return a Condition properly built
     */
    public Condition stringEquals(String value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.STRING_EQUALS;
        this.boolExpr = boolExpr;
        return buildCondition();
    }


    /**
     * This is the negated expression of STRINGEQUALS
     *
     * @param value    indicates the value to be checked using StringEquals operator
     * @param boolExpr Optional value for ignoring case. When set to true, StringEquals will do a case-insensitive search. When unspecified, this value is false.
     * @return a Condition properly built
     */
    public Condition notStringEquals(String value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.NOT_STRING_EQUALS;
        this.boolExpr = boolExpr;
        return buildCondition();
    }


    /**
     * Create a condition using GREATER THAN EQUALS operator
     * <p>
     * Final result will be: c.populationDensity >= 5000000
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-where#scalar-expressions-in-the-where-clause
     *
     * @param value indicates the value to be checked using >= operator
     * @return a Condition properly built
     */
    public Condition gte(Number value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.GREATER_THAN_EQ;
        return buildCondition();
    }

    /**
     * Create a condition using GREATER THAN operator
     * <p>
     * Final result will be: c.populationDensity > 5000000
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-where#scalar-expressions-in-the-where-clause
     *
     * @param value indicates the value to be checked using > operator
     * @return a Condition properly built
     */
    public Condition gt(Number value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.GREATER_THAN;
        return buildCondition();
    }

    /**
     * Create a condition using LOWER THAN operator
     * <p>
     * Final result will be: c.populationDensity < 5000000
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-where#scalar-expressions-in-the-where-clause
     *
     * @param value indicates the value to be checked using < operator
     * @return a Condition properly built
     */
    public Condition lt(Number value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.LOWER_THAN;
        return buildCondition();
    }

    /**
     * Create a condition using LOWER THAN EQUALS operator
     * <p>
     * Final result will be: c.populationDensity <= 5000000
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-where#scalar-expressions-in-the-where-clause
     *
     * @param value indicates the value to be checked using <= operator
     * @return a Condition properly built
     */
    public Condition lte(Number value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.LOWER_THAN_EQ;
        return buildCondition();
    }

    /**
     * Create a condition using IS_ARRAY operator
     * <p>
     * Final result will be: IS_ARRAY(c.states)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-is-array
     *
     * @return a Condition properly built
     */
    public Condition isArray() {
        this.comparisonOperator = ComparisonOperator.IS_ARRAY;
        return buildCondition();
    }



    /**
     * This is the negated expression of IS_ARRAY
     *
     * @return a Condition properly built
     */
    public Condition isNotArray() {
        this.comparisonOperator = ComparisonOperator.NOT_IS_ARRAY;
        return buildCondition();
    }


    /**
     * Create a condition using IS_BOOL operator
     * <p>
     * Final result will be: IS_BOOL(c.name)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-is-bool
     *
     * @return a Condition properly built
     */
    public Condition isBool() {
        this.comparisonOperator = ComparisonOperator.IS_BOOL;
        return buildCondition();
    }



    /**
     *
     * This is the negated expression of IS_BOOL
     *
     * @return a Condition properly built
     */
    public Condition isNotBool() {
        this.comparisonOperator = ComparisonOperator.NOT_IS_BOOL;
        return buildCondition();
    }



    /**
     * Create a condition using IS_DEFINED operator
     * <p>
     * Final result will be: IS_DEFINED(c.name)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-is-defined
     *
     * @return a Condition properly built
     */
    public Condition isDefined() {
        this.comparisonOperator = ComparisonOperator.IS_DEFINED;
        return buildCondition();
    }

    /**
     * This is the negated expression of IS_DEFINED
     *
     * @return a Condition properly built
     */
    public Condition isNotDefined() {
        this.comparisonOperator = ComparisonOperator.NOT_IS_DEFINED;
        return buildCondition();
    }


    /**
     * Create a condition using IS_NULL operator
     * <p>
     * Final result will be: IS_NULL(c.name)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-is-null
     *
     * @return a Condition properly built
     */
    public Condition isNull() {
        this.comparisonOperator = ComparisonOperator.IS_NULL;
        return buildCondition();
    }


    /**
     * This is the negated expression of IS_NULL
     *
     * @return a Condition properly built
     */
    public Condition isNotNull() {
        this.comparisonOperator = ComparisonOperator.NOT_IS_NULL;
        return buildCondition();
    }

    /**
     * Create a condition using IS_NUMBER operator
     * <p>
     * Final result will be: IS_NUMBER(c.name)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-is-number
     *
     * @return a Condition properly built
     */
    public Condition isNumber() {
        this.comparisonOperator = ComparisonOperator.IS_NUMBER;
        return buildCondition();
    }


    /**
     * This is the negated expression of IS_NUMBER
     *
     * @return a Condition properly built
     */
    public Condition isNotNumber() {
        this.comparisonOperator = ComparisonOperator.NOT_IS_NUMBER;
        return buildCondition();
    }


    /**
     * Create a condition using IS_OBJECT operator
     * <p>
     * Final result will be: IS_OBJECT(c.name)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-is-object
     *
     * @return a Condition properly built
     */
    public Condition isObject() {
        this.comparisonOperator = ComparisonOperator.IS_OBJECT;
        return buildCondition();
    }

    /**
     * This is the negated expression of IS_OBJECT
     *
     * @return a Condition properly built
     */
    public Condition isNotObject() {
        this.comparisonOperator = ComparisonOperator.NOT_IS_OBJECT;
        return buildCondition();
    }


    /**
     * Create a condition using IS_PRIMITIVE operator
     * <p>
     * Final result will be: IS_PRIMITIVE(c.name)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-is-primitive
     *
     * @return a Condition properly built
     */
    public Condition isPrimitive() {
        this.comparisonOperator = ComparisonOperator.IS_PRIMITIVE;
        return buildCondition();
    }


    /**
     * This is the negated expression of IS_PRIMITIVE
     *
     * @return a Condition properly built
     */
    public Condition isNotPrimitive() {
        this.comparisonOperator = ComparisonOperator.NOT_IS_PRIMITIVE;
        return buildCondition();
    }


    /**
     * Create a condition using IS_STRING operator
     * <p>
     * Final result will be: IS_STRING(c.name)
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-is-string
     *
     * @return a Condition properly built
     */
    public Condition isString() {
        this.comparisonOperator = ComparisonOperator.IS_STRING;
        return buildCondition();
    }


    /**
     * This is the negated expression of IS_STRING
     *
     * @return a Condition properly built
     */
    public Condition isNotString() {
        this.comparisonOperator = ComparisonOperator.NOT_IS_STRING;
        return buildCondition();
    }


    private Condition buildCondition() {
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator, this.boolExpr);
    }

}
