package com.blackhat.devtools.cosmos;

enum BooleanOperator {

    AND("AND"),
    OR("OR");

    private final String value;

    BooleanOperator(String value) {
        this.value = value;
    }

    String value() {
        return this.value;
    }

}
