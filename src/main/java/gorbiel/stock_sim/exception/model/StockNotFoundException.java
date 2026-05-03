package gorbiel.stock_sim.exception.model;

public class StockNotFoundException extends ResourceNotFoundException {

    public StockNotFoundException(String stockName) {
        super("Stock not found: " + stockName);
    }
}
