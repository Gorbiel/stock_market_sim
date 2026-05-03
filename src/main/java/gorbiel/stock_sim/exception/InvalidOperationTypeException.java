package gorbiel.stock_sim.exception;

public class InvalidOperationTypeException extends BadRequestException {

    public InvalidOperationTypeException(String value) {
        super("Invalid operation type: " + value);
    }
}
