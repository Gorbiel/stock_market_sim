package gorbiel.stock_sim.wallet.service;

import gorbiel.stock_sim.audit.model.OperationType;

/**
 * Handles wallet stock operations (buy and sell).
 *
 * <p>Business rules:
 * <ul>
 *   <li>Wallet is created automatically if it does not exist</li>
 *   <li>Stock must exist in the bank</li>
 *   <li>Buying requires bank stock quantity &gt; 0</li>
 *   <li>Selling requires wallet stock quantity &gt; 0</li>
 *   <li>On success:
 *       <ul>
 *         <li>Bank and wallet quantities are updated</li>
 *         <li>Operation is recorded in audit log</li>
 *       </ul>
 *   </li>
 *   <li>Failed operations are not logged</li>
 * </ul>
 */
public interface WalletStockOperationService {

    /**
     * Processes a wallet stock operation.
     *
     * @param walletId wallet identifier
     * @param stockName stock name
     * @param type operation type (BUY or SELL)
     *
     * @throws gorbiel.stock_sim.exception.model.StockNotFoundException
     *         if the stock does not exist
     * @throws gorbiel.stock_sim.exception.model.InsufficientBankStockException
     *         if buying and bank has no stock
     * @throws gorbiel.stock_sim.exception.model.InsufficientWalletStockException
     *         if selling and wallet has no stock
     * @throws gorbiel.stock_sim.exception.model.InvalidOperationTypeException
     *         if operation type is invalid
     */
    void processOperation(String walletId, String stockName, OperationType type);
}
