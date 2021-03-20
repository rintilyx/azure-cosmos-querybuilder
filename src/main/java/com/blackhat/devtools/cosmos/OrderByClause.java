package com.blackhat.devtools.cosmos;

public class OrderByClause {

    private String field;
    private SortingCriteria criteria;

    public OrderByClause(String field, SortingCriteria criteria) {
        this.field = field;
        this.criteria = criteria;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SortingCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(SortingCriteria criteria) {
        this.criteria = criteria;
    }
}