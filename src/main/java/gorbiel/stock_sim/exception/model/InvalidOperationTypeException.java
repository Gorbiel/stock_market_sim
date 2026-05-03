package gorbiel.stock_sim.exception.model;

public class InvalidOperationTypeException extends BadRequestException {

    public InvalidOperationTypeException(String value) {
        super("Invalid operation type: " + value);
    }
}
