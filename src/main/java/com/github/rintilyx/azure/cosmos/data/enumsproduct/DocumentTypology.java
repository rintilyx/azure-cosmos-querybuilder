
package com.github.rintilyx.azure.cosmos.data.enumsproduct;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;


/**
 * DocumentTypology
 */
public enum DocumentTypology {

    PRODUCT("PRODUCT");
    private final String value;
    private final static Map<String, DocumentTypology> CONSTANTS = new HashMap<String, DocumentTypology>();

    static {
        for (DocumentTypology c : values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private DocumentTypology(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static DocumentTypology fromValue(String value) {
        DocumentTypology constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
