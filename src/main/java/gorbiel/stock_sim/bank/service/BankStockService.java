package gorbiel.stock_sim.bank.service;

import gorbiel.stock_sim.bank.dto.BankStocksResponse;
import gorbiel.stock_sim.bank.dto.UpdateBankStocksRequest;
import gorbiel.stock_sim.bank.model.BankStockHolding;

public interface BankStockService {

    void updateBankStocks(UpdateBankStocksRequest request);

    BankStocksResponse getBankStocks();

    BankStockHolding getExistingStock(String stockName);
}
