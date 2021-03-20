package com.blackhat.devtools.cosmos;

import java.util.List;
import java.util.stream.Collectors;

enum SelectionType {

    SELECT("SELECT ${FIELDS}"),
    COUNT("SELECT VALUE COUNT(1)");

    private String pattern;

    SelectionType(String pattern) {
        this.pattern = pattern;
    }

    public String buildPattern(String alias, List<String> fields) {
        String fs;
        if (fields == null || fields.isEmpty()) {
            fs = alias;
        } else {
            fs = fields.stream().map(f -> alias + "." + f).collect(Collectors.joining(", "));
        }
        return this.pattern.replace("${FIELDS}", fs);
    }
}
