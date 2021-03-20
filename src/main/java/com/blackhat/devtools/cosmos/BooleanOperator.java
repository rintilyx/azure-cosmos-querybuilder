package com.blackhat.devtools.cosmos;

public enum BooleanOperator {

    AND("AND"),
    OR("OR");

    private String value;

    BooleanOperator(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
