package gorbiel.stock_sim.wallet.service;

import gorbiel.stock_sim.audit.model.OperationType;

public interface WalletStockOperationService {

    void processOperation(String walletId, String stockName, OperationType type);
}
