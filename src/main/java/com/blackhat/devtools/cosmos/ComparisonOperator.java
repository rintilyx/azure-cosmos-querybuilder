package com.blackhat.devtools.cosmos;

enum ComparisonOperator {

    EQUALS("${COLLECTION}.${JSON_PROPERTY}=${VALUE}"),
    ARRAY_CONTAINS("ARRAY_CONTAINS(${COLLECTION}.${JSON_PROPERTY}, ${VALUE}, true)"),
    CONTAINS("CONTAINS(${COLLECTION}.${JSON_PROPERTY}, ${VALUE})"),
    STARTS_WITH("STARTSWITH(${COLLECTION}.${JSON_PROPERTY}, ${VALUE})"),
    GREATER_THAN("${COLLECTION}.${JSON_PROPERTY} > ${VALUE}"),
    GREATER_THAN_EQ("${COLLECTION}.${JSON_PROPERTY} >= ${VALUE}"),
    LOWER_THAN("${COLLECTION}.${JSON_PROPERTY} < ${VALUE}"),
    LOWER_THAN_EQ("${COLLECTION}.${JSON_PROPERTY} <= ${VALUE}"),
    IN("${COLLECTION}.${JSON_PROPERTY} IN(${VALUE})");

    private String pattern;

    ComparisonOperator(String pattern) {
        this.pattern = pattern;
    }

    String buildCondition(String collection, String property, String sqlParamName) {
        return this.pattern
                .replace("${COLLECTION}", collection)
                .replace("${JSON_PROPERTY}", property)
                .replace("${VALUE}", sqlParamName);
    }

}
