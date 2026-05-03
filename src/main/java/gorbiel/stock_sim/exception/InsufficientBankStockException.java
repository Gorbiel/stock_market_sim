package gorbiel.stock_sim.exception;

public class InsufficientBankStockException extends BadRequestException {

    public InsufficientBankStockException(String stockName) {
        super("Insufficient bank stock: " + stockName);
    }
}