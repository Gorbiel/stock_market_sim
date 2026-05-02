package gorbiel.stock_sim.bank.repository;

import gorbiel.stock_sim.bank.model.BankStockHolding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankStockHoldingRepository extends JpaRepository<BankStockHolding, String> {}
