package gorbiel.stock_sim.wallet.repository;

import gorbiel.stock_sim.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for wallet entities.
 *
 * <p>Wallets are identified by their unique string ID.
 */
public interface WalletRepository extends JpaRepository<Wallet, String> {}
