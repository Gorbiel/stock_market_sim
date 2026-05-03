package gorbiel.stock_sim.wallet.service;

import gorbiel.stock_sim.audit.model.AuditLogEntry;
import gorbiel.stock_sim.audit.model.OperationType;
import gorbiel.stock_sim.audit.repository.AuditLogEntryRepository;
import gorbiel.stock_sim.bank.model.BankStockHolding;
import gorbiel.stock_sim.bank.service.BankStockService;
import gorbiel.stock_sim.exception.model.InsufficientBankStockException;
import gorbiel.stock_sim.exception.model.InsufficientWalletStockException;
import gorbiel.stock_sim.wallet.model.Wallet;
import gorbiel.stock_sim.wallet.model.WalletStockHolding;
import gorbiel.stock_sim.wallet.repository.WalletRepository;
import gorbiel.stock_sim.wallet.repository.WalletStockHoldingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Default implementation of wallet stock operations.
 *
 * <p>Relies on database consistency (single instance, no distributed locking).
 */
@Service
@RequiredArgsConstructor
public class WalletStockOperationServiceImpl implements WalletStockOperationService {

    private final BankStockService bankStockService;
    private final WalletRepository walletRepository;
    private final WalletStockHoldingRepository walletStockHoldingRepository;
    private final AuditLogEntryRepository auditLogEntryRepository;

    @Override
    @Transactional
    public void processOperation(String walletId, String stockName, OperationType type) {
        // Delegates to specific operation to keep logic simple and explicit
        switch (type) {
            case BUY -> buyStock(walletId, stockName);
            case SELL -> sellStock(walletId, stockName);
        }
    }

    private void buyStock(String walletId, String stockName) {
        // Validate stock existence in bank (throws if missing)
        BankStockHolding bankStock = bankStockService.getExistingStock(stockName);

        // Business rule: cannot buy if bank has no stock available
        if (bankStock.getQuantity() == 0) {
            throw new InsufficientBankStockException(stockName);
        }

        // Wallet is created lazily on first interaction
        Wallet wallet =
                walletRepository.findById(walletId).orElseGet(() -> walletRepository.save(new Wallet(walletId)));

        // Create wallet holding if it does not exist yet
        WalletStockHolding walletStock = walletStockHoldingRepository
                .findByWalletIdAndStockName(walletId, stockName)
                .orElseGet(() -> new WalletStockHolding(wallet, stockName, 0));

        // Update both sides of the transaction
        bankStock.decrease();
        walletStock.increase();

        walletStockHoldingRepository.save(walletStock);

        // Only successful operations are logged
        auditLogEntryRepository.save(new AuditLogEntry(OperationType.BUY, walletId, stockName));
    }

    private void sellStock(String walletId, String stockName) {
        // Validate stock existence in bank
        BankStockHolding bankStock = bankStockService.getExistingStock(stockName);

        // Wallet is created lazily, even for invalid sell attempts
        Wallet wallet =
                walletRepository.findById(walletId).orElseGet(() -> walletRepository.save(new Wallet(walletId)));

        WalletStockHolding walletStock = walletStockHoldingRepository
                .findByWalletIdAndStockName(walletId, stockName)
                .orElseGet(() -> new WalletStockHolding(wallet, stockName, 0));

        // Business rule: cannot sell if wallet has no stock
        if (walletStock.getQuantity() == 0) {
            throw new InsufficientWalletStockException(walletId, stockName);
        }

        // Update both sides of the transaction
        walletStock.decrease();
        bankStock.increase();

        walletStockHoldingRepository.save(walletStock);

        // Only successful operations are logged
        auditLogEntryRepository.save(new AuditLogEntry(OperationType.SELL, walletId, stockName));
    }
}
