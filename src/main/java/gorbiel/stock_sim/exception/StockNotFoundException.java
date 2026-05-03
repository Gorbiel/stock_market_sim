package gorbiel.stock_sim.exception;

public class StockNotFoundException extends ResourceNotFoundException {

    public StockNotFoundException(String stockName) {
        super("Stock not found: " + stockName);
    }
}