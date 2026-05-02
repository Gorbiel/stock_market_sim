package gorbiel.stock_sim.audit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;

public enum OperationType {
    BUY("buy"),
    SELL("sell");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static OperationType fromValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported operation type: " + value));
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
