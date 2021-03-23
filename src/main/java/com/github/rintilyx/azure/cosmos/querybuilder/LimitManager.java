package com.github.rintilyx.azure.cosmos.querybuilder;

import com.azure.data.cosmos.CosmosItemProperties;
import com.azure.data.cosmos.FeedOptions;
import com.azure.data.cosmos.FeedResponse;
import com.azure.data.cosmos.SqlQuerySpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class LimitManager {

    private final CosmosQueryConfiguration cosmosQueryConfiguration;

    public LimitManager(CosmosQueryConfiguration cosmosQueryConfiguration) {
        this.cosmosQueryConfiguration = cosmosQueryConfiguration;
    }

    public SqlQuerySpec buildQuery() {
        return new CosmosQueryBuilder(cosmosQueryConfiguration)
                .buildQuery();
    }

    public Flux<FeedResponse<CosmosItemProperties>> queryItems() {
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItems();
    }

    public Flux<FeedResponse<CosmosItemProperties>> queryItems(FeedOptions feedOptions) {
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItems(feedOptions);
    }

    public List<FeedResponse<CosmosItemProperties>> queryItemsSync(FeedOptions feedOptions) {
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItemsSync(feedOptions);
    }

    public <T> Flux<T> queryItems(FeedOptions feedOptions, Class<T> targetClass) {
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItems(feedOptions, targetClass);
    }

    public <T> List<T> queryItemsSync(FeedOptions feedOptions, Class<T> targetClass) {
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItemsSync(feedOptions, targetClass);
    }

    public <T> Flux<T> queryItems(FeedOptions feedOptions, CosmosItemPropertiesConverter<T> customConverter) {
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItems(feedOptions, customConverter);
    }

    public <T> List<T> queryItemsSync(FeedOptions feedOptions, CosmosItemPropertiesConverter<T> customConverter) {
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).queryItemsSync(feedOptions, customConverter);
    }

    public Mono<Long> countItems(FeedOptions feedOptions) {
        this.cosmosQueryConfiguration.setSelectionType(SelectionType.COUNT);
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).countItems(feedOptions);
    }

    public Long countItemsSync(FeedOptions feedOptions) {
        this.cosmosQueryConfiguration.setSelectionType(SelectionType.COUNT);
        return new CosmosQueryBuilder(this.cosmosQueryConfiguration).countItemsSync(feedOptions);
    }

}
