
package com.github.rintilyx.azure.cosmos.data.enumsproduct;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;


/**
 * DrugClassTypology
 */
public enum DrugClassTypology {

    C_2("C2"),
    C_3("C3"),
    C_4("C4"),
    C_5("C5"),
    NA("NA");
    private final String value;
    private final static Map<String, DrugClassTypology> CONSTANTS = new HashMap<String, DrugClassTypology>();

    static {
        for (DrugClassTypology c : values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    private DrugClassTypology(String value) {
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
    public static DrugClassTypology fromValue(String value) {
        DrugClassTypology constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
