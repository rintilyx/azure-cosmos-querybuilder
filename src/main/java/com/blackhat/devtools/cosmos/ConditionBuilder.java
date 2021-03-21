package com.blackhat.devtools.cosmos;

import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible to build a Condition to add in WHERE clause
 */
public class ConditionBuilder {

    private final CosmosReference cosmosReference;
    private String attribute;
    private Object value;
    private ComparisonOperator comparisonOperator;
    private Boolean includeIfNullIndicator = false;
    private Boolean boolExpr;

    public ConditionBuilder(CosmosReference cosmosReference) {
        this.cosmosReference = cosmosReference;
    }

    public ConditionBuilder attribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    /**
     * If you set this indicator to "true", this condition will be included to final query though the provided value is null
     * If you set this indicator to "false", this condition will NOT be included to final query if the provided value is null
     *
     * @param includeIfNullIndicator indicates if condition must be/not be included in query if value is null
     * @return this class
     */
    public ConditionBuilder includeIfNull(boolean includeIfNullIndicator) {
        this.includeIfNullIndicator = includeIfNullIndicator;
        return this;
    }

    /**
     * Create a condition using EQUALS operator
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
     * Create a condition using STARTSWITH operator
     * <p>
     * Final result will be: STARTSWITH(c.name, "Euro")
     * <p>
     * For more details please visit: https://docs.microsoft.com/en-us/azure/cosmos-db/sql-query-startswith
     *
     * @param value indicates the value to be checked using STARTSWITH operator
     * @return a Condition properly built
     */
    public Condition startsWith(Object value) {
        return this.startsWith(value, null);
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
    public Condition startsWith(Object value, Boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.STARTS_WITH;
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
     * @param value    indicates the value to be checked using ENDSWITH operator
     * @param boolExpr Optional value for ignoring case. When set to true, STARTSWITH will do a case-insensitive search. When unspecified, this value is false.
     * @return a Condition properly built
     */
    public Condition endsWith(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.ENDS_WITH;
        return buildCondition();
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
    public Condition endsWith(Object value, boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.ENDS_WITH;
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
    public Condition stringEquals(Object value) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.STRING_EQUALS;
        return buildCondition();
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
    public Condition stringEquals(Object value, boolean boolExpr) {
        this.value = value;
        this.comparisonOperator = ComparisonOperator.STRING_EQUALS;
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
    public Condition gte(Object value) {
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
    public Condition gt(Object value) {
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
    public Condition lt(Object value) {
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
    public Condition lte(Object value) {
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

    private Condition buildCondition() {
        return new Condition(this.cosmosReference, this.attribute, this.value, this.comparisonOperator, this.includeIfNullIndicator, this.boolExpr);
    }

}
