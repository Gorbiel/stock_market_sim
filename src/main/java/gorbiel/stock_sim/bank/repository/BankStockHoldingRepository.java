package gorbiel.stock_sim.bank.repository;

import gorbiel.stock_sim.bank.model.BankStockHolding;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for managing bank stock holdings.
 *
 * <p>Each stock is uniquely identified by its name.
 */
public interface BankStockHoldingRepository extends JpaRepository<BankStockHolding, String> {}
