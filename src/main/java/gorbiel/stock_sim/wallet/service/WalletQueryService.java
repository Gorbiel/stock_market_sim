package gorbiel.stock_sim.wallet.service;

import gorbiel.stock_sim.wallet.dto.WalletResponse;

/**
 * Provides read-only access to wallet state.
 */
public interface WalletQueryService {

    /**
     * Retrieves full wallet state.
     *
     * @param walletId wallet identifier
     * @return wallet with all stock holdings
     *
     * @throws gorbiel.stock_sim.exception.model.ResourceNotFoundException
     *         if the wallet does not exist
     */
    WalletResponse getWallet(String walletId);

    /**
     * Retrieves quantity of a specific stock in a wallet.
     *
     * @param walletId wallet identifier
     * @param stockName stock name
     * @return quantity of the stock (0 if not present)
     *
     * @throws gorbiel.stock_sim.exception.model.ResourceNotFoundException
     *         if the wallet does not exist
     */
    int getWalletStockQuantity(String walletId, String stockName);
}
