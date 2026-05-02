package gorbiel.stock_sim.bank.service;

import gorbiel.stock_sim.bank.dto.BankStocksResponse;
import gorbiel.stock_sim.bank.dto.UpdateBankStocksRequest;

public interface BankStockService {

    void updateBankStocks(UpdateBankStocksRequest request);

    BankStocksResponse getBankStocks();
}
