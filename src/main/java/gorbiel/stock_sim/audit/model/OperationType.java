package gorbiel.stock_sim.audit.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import gorbiel.stock_sim.exception.model.InvalidOperationTypeException;
import java.util.Arrays;

/**
 * Supported wallet stock operation types.
 *
 * <p>JSON values are lowercase to match the public API contract.
 */
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
                .orElseThrow(() -> new InvalidOperationTypeException(value));
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
