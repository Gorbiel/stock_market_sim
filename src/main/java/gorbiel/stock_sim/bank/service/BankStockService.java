package gorbiel.stock_sim.bank.service;

import gorbiel.stock_sim.bank.dto.BankStocksResponse;
import gorbiel.stock_sim.bank.dto.UpdateBankStocksRequest;
import gorbiel.stock_sim.bank.model.BankStockHolding;

/**
 * Manages the state of stocks available in the bank.
 *
 * <p>The bank represents the global pool of available stocks used by wallet operations.
 * All stock quantities must be non-negative.
 */
public interface BankStockService {

    /**
     * Replaces the entire bank stock state.
     *
     * <p>This operation overwrites all existing stock quantities.
     *
     * @param request new complete stock state
     */
    void updateBankStocks(UpdateBankStocksRequest request);

    /**
     * Retrieves the current bank stock state.
     *
     * @return all stocks with their quantities
     */
    BankStocksResponse getBankStocks();

    /**
     * Retrieves an existing stock by name.
     *
     * @param stockName stock identifier
     * @return existing stock holding
     *
     * @throws gorbiel.stock_sim.exception.model.StockNotFoundException
     *         if the stock does not exist in the bank
     */
    BankStockHolding getExistingStock(String stockName);
}
