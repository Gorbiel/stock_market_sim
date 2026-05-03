package gorbiel.stock_sim.exception;

public class InsufficientWalletStockException extends BadRequestException {

    public InsufficientWalletStockException(String walletId, String stockName) {
        super("Insufficient wallet stock: walletId=%s, stockName=%s".formatted(walletId, stockName));
    }
}