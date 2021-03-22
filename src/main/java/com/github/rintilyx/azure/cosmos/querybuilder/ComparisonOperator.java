package com.github.rintilyx.azure.cosmos.querybuilder;

enum ComparisonOperator {

    EQUALS("${REFERENCE}.${JSON_PROPERTY}=${VALUE}"),
    NOT_EQUALS("${REFERENCE}.${JSON_PROPERTY}!=${VALUE}"),
    LIKE("${REFERENCE}.${JSON_PROPERTY} LIKE ${VALUE}"),
    NOT_LIKE("${REFERENCE}.${JSON_PROPERTY} NOT LIKE ${VALUE}"),
    ARRAY_CONTAINS("ARRAY_CONTAINS(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    NOT_ARRAY_CONTAINS("NOT ARRAY_CONTAINS(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    CONTAINS("CONTAINS(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    NOT_CONTAINS("NOT CONTAINS(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    STARTS_WITH("STARTSWITH(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    NOT_STARTS_WITH("STARTSWITH(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    ENDS_WITH("NOT ENDSWITH(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    NOT_ENDS_WITH("NOT ENDSWITH(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    STRING_EQUALS("STRINGEQUALS(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    NOT_STRING_EQUALS("NOT STRINGEQUALS(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    GREATER_THAN("${REFERENCE}.${JSON_PROPERTY} > ${VALUE}"),
    GREATER_THAN_EQ("${REFERENCE}.${JSON_PROPERTY} >= ${VALUE}"),
    LOWER_THAN("${REFERENCE}.${JSON_PROPERTY} < ${VALUE}"),
    LOWER_THAN_EQ("${REFERENCE}.${JSON_PROPERTY} <= ${VALUE}"),
    IN("${REFERENCE}.${JSON_PROPERTY} IN(${VALUE})"),
    NOT_IN("${REFERENCE}.${JSON_PROPERTY} NOT IN(${VALUE})"),
    IS_ARRAY("IS_ARRAY(${REFERENCE}.${JSON_PROPERTY})"),
    NOT_IS_ARRAY("NOT IS_ARRAY(${REFERENCE}.${JSON_PROPERTY})"),
    IS_BOOL("IS_BOOL(${REFERENCE}.${JSON_PROPERTY})"),
    NOT_IS_BOOL("NOT IS_BOOL(${REFERENCE}.${JSON_PROPERTY})"),
    IS_DEFINED("IS_DEFINED(${REFERENCE}.${JSON_PROPERTY})"),
    NOT_IS_DEFINED("NOT IS_DEFINED(${REFERENCE}.${JSON_PROPERTY})"),
    IS_NULL("IS_NULL(${REFERENCE}.${JSON_PROPERTY})"),
    NOT_IS_NULL("NOT IS_NULL(${REFERENCE}.${JSON_PROPERTY})"),
    IS_NUMBER("IS_NUMBER(${REFERENCE}.${JSON_PROPERTY})"),
    NOT_IS_NUMBER("NOT IS_NUMBER(${REFERENCE}.${JSON_PROPERTY})"),
    IS_OBJECT("IS_OBJECT(${REFERENCE}.${JSON_PROPERTY})"),
    NOT_IS_OBJECT("NOT IS_OBJECT(${REFERENCE}.${JSON_PROPERTY})"),
    IS_PRIMITIVE("IS_PRIMITIVE(${REFERENCE}.${JSON_PROPERTY})"),
    NOT_IS_PRIMITIVE("NOT IS_PRIMITIVE(${REFERENCE}.${JSON_PROPERTY})"),
    IS_STRING("IS_STRING(${REFERENCE}.${JSON_PROPERTY})"),
    NOT_IS_STRING("NOT IS_STRING(${REFERENCE}.${JSON_PROPERTY})");

    private final String pattern;

    ComparisonOperator(String pattern) {
        this.pattern = pattern;
    }

    String buildCondition(Condition condition) {
        String result = this.pattern
                .replace("${REFERENCE}", condition.getCosmosReference().getAlias())
                .replace("${JSON_PROPERTY}", condition.getAttribute());

        if (this.pattern.contains("${VALUE}") && condition.getSqlParamName() != null)
            result = result.replace("${VALUE}", condition.getSqlParamName());

        if (this.pattern.contains("${BOOL_EXPR}"))
            result = result.replace("${BOOL_EXPR}", condition.getBoolExpr() != null ? ", " + condition.getBoolExpr() : "");

        return result;
    }

}
