package gorbiel.stock_sim.exception.model;

public class InsufficientBankStockException extends BadRequestException {

    public InsufficientBankStockException(String stockName) {
        super("Insufficient bank stock: " + stockName);
    }
}
