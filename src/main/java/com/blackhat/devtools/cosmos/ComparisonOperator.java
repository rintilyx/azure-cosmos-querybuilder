package com.blackhat.devtools.cosmos;

import org.apache.commons.lang3.BooleanUtils;

enum ComparisonOperator {

    EQUALS("${REFERENCE}.${JSON_PROPERTY}${NOT}=${VALUE}"),
    LIKE("${REFERENCE}.${JSON_PROPERTY} ${NOT} LIKE ${VALUE}"),
    ARRAY_CONTAINS("ARRAY_CONTAINS(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    CONTAINS("${NOT}CONTAINS(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    STARTS_WITH("${NOT}STARTSWITH(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    ENDS_WITH("${NOT}ENDSWITH(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    STRING_EQUALS("${NOT}STRINGEQUALS(${REFERENCE}.${JSON_PROPERTY}, ${VALUE}${BOOL_EXPR})"),
    GREATER_THAN("${REFERENCE}.${JSON_PROPERTY} ${NOT}> ${VALUE}"),
    GREATER_THAN_EQ("${REFERENCE}.${JSON_PROPERTY} ${NOT}>= ${VALUE}"),
    LOWER_THAN("${REFERENCE}.${JSON_PROPERTY} ${NOT}< ${VALUE}"),
    LOWER_THAN_EQ("${REFERENCE}.${JSON_PROPERTY} ${NOT}<= ${VALUE}"),
    IN("${REFERENCE}.${JSON_PROPERTY} ${NOT}IN(${VALUE})"),
    IS_ARRAY("${NOT}IS_ARRAY(${REFERENCE}.${JSON_PROPERTY})"),
    IS_BOOL("${NOT}IS_BOOL(${REFERENCE}.${JSON_PROPERTY})"),
    IS_DEFINED("${NOT}IS_DEFINED(${REFERENCE}.${JSON_PROPERTY})"),
    IS_NULL("${NOT}IS_NULL(${REFERENCE}.${JSON_PROPERTY})"),
    IS_NUMBER("${NOT}IS_NUMBER(${REFERENCE}.${JSON_PROPERTY})"),
    IS_OBJECT("${NOT}IS_OBJECT(${REFERENCE}.${JSON_PROPERTY})"),
    IS_PRIMITIVE("${NOT}IS_PRIMITIVE(${REFERENCE}.${JSON_PROPERTY})"),
    IS_STRING("${NOT}IS_STRING(${REFERENCE}.${JSON_PROPERTY})");

    private final String pattern;

    ComparisonOperator(String pattern) {
        this.pattern = pattern;
    }

    String buildCondition(Condition condition) {
        String result = this.pattern
                .replace("${REFERENCE}", condition.getCosmosReference().getAlias())
                .replace("${JSON_PROPERTY}", condition.getAttribute())
                .replace("${NOT}", BooleanUtils.isTrue(condition.getNegated()) ? "NOT " : "");

        if (this.pattern.contains("${VALUE}") && condition.getSqlParamName() != null)
            result = result.replace("${VALUE}", condition.getSqlParamName());

        if (this.pattern.contains("${BOOL_EXPR}"))
            result = result.replace("${BOOL_EXPR}", condition.getBoolExpr() != null ? ", " + condition.getBoolExpr() : "");

        return result;
    }

}
