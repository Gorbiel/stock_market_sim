package gorbiel.stock_sim.wallet.repository;

import gorbiel.stock_sim.wallet.model.WalletStockHolding;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for wallet stock holdings.
 *
 * <p>Each entry represents quantity of a specific stock within a wallet.
 */
public interface WalletStockHoldingRepository extends JpaRepository<WalletStockHolding, Long> {

    /**
     * Finds a specific stock holding for a given wallet.
     *
     * @param walletId wallet identifier
     * @param stockName stock name
     * @return optional holding if present
     */
    Optional<WalletStockHolding> findByWalletIdAndStockName(String walletId, String stockName);

    /**
     * Retrieves all stock holdings for a given wallet.
     *
     * @param walletId wallet identifier
     * @return list of holdings for the wallet
     */
    List<WalletStockHolding> findAllByWalletId(String walletId);
}
