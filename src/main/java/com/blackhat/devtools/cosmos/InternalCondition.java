package com.blackhat.devtools.cosmos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
class InternalCondition {

    private Condition condition;
    private BooleanOperator booleanOperator;
    private Integer order;
    @Builder.Default
    private boolean isRoot = false;

}
