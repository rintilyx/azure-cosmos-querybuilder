package com.github.rintilyx.azure.cosmos.querybuilder;

class ResultWrapper<T> {

    private T c;

    T getC() {
        return c;
    }

    void setC(T c) {
        this.c = c;
    }
}
