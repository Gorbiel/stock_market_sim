package gorbiel.stock_sim.wallet.repository;

import gorbiel.stock_sim.wallet.model.WalletStockHolding;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletStockHoldingRepository extends JpaRepository<WalletStockHolding, Long> {

    Optional<WalletStockHolding> findByWalletIdAndStockName(String walletId, String stockName);

    List<WalletStockHolding> findAllByWalletId(String walletId);
}
