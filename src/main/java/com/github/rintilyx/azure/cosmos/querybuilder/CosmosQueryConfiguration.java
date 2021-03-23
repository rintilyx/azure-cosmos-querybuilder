package com.github.rintilyx.azure.cosmos.querybuilder;

import com.azure.data.cosmos.CosmosClient;
import com.azure.data.cosmos.sync.CosmosSyncClient;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CosmosQueryConfiguration {

    private CosmosClient cosmosClient;
    private CosmosSyncClient cosmosSyncClient;
    private String database;
    private Logger logger;
    private long maxAttempts = 1L;
    private Duration onRetryFixedDelay;
    private SelectionType selectionType = SelectionType.SELECT;
    private List<String> fields;
    private CosmosCollection collection;
    private List<CosmosJoinReference> joins;
    private List<InternalExpression> internalExpressionList = new ArrayList<>();
    private List<OrderByClause> orderBy;
    private Integer offset;
    private Integer limit;

    public CosmosClient getCosmosClient() {
        return cosmosClient;
    }

    public void setCosmosClient(CosmosClient cosmosClient) {
        this.cosmosClient = cosmosClient;
    }

    public CosmosSyncClient getCosmosSyncClient() {
        return cosmosSyncClient;
    }

    public void setCosmosSyncClient(CosmosSyncClient cosmosSyncClient) {
        this.cosmosSyncClient = cosmosSyncClient;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public long getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(long maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Duration getOnRetryFixedDelay() {
        return onRetryFixedDelay;
    }

    public void setOnRetryFixedDelay(Duration onRetryFixedDelay) {
        this.onRetryFixedDelay = onRetryFixedDelay;
    }

    List<String> getFields() {
        return fields;
    }

    void setFields(List<String> fields) {
        this.fields = fields;
    }

    CosmosCollection getCollection() {
        return collection;
    }

    void setCollection(CosmosCollection collection) {
        this.collection = collection;
    }

    List<CosmosJoinReference> getJoins() {
        return joins;
    }

    void setJoins(List<CosmosJoinReference> joins) {
        this.joins = joins;
    }

    List<InternalExpression> getInternalExpressionList() {
        return internalExpressionList;
    }

    void setInternalExpressionList(List<InternalExpression> internalExpressionList) {
        this.internalExpressionList = internalExpressionList;
    }

    List<OrderByClause> getOrderBy() {
        return orderBy;
    }

    void setOrderBy(List<OrderByClause> orderBy) {
        this.orderBy = orderBy;
    }

    Integer getOffset() {
        return offset;
    }

    void setOffset(Integer offset) {
        this.offset = offset;
    }

    Integer getLimit() {
        return limit;
    }

    void setLimit(Integer limit) {
        this.limit = limit;
    }

    SelectionType getSelectionType() {
        return selectionType;
    }

    void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

}
