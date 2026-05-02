package gorbiel.stock_sim.wallet.repository;

import gorbiel.stock_sim.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, String> {}
