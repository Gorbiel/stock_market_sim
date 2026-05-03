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
        switch (type) {
            case BUY -> buyStock(walletId, stockName);
            case SELL -> sellStock(walletId, stockName);
        }
    }

    private void buyStock(String walletId, String stockName) {
        BankStockHolding bankStock = bankStockService.getExistingStock(stockName);

        if (bankStock.getQuantity() == 0) {
            throw new InsufficientBankStockException(stockName);
        }

        Wallet wallet =
                walletRepository.findById(walletId).orElseGet(() -> walletRepository.save(new Wallet(walletId)));

        WalletStockHolding walletStock = walletStockHoldingRepository
                .findByWalletIdAndStockName(walletId, stockName)
                .orElseGet(() -> new WalletStockHolding(wallet, stockName, 0));

        bankStock.decrease();
        walletStock.increase();

        walletStockHoldingRepository.save(walletStock);
        auditLogEntryRepository.save(new AuditLogEntry(OperationType.BUY, walletId, stockName));
    }

    private void sellStock(String walletId, String stockName) {
        BankStockHolding bankStock = bankStockService.getExistingStock(stockName);

        Wallet wallet =
                walletRepository.findById(walletId).orElseGet(() -> walletRepository.save(new Wallet(walletId)));

        WalletStockHolding walletStock = walletStockHoldingRepository
                .findByWalletIdAndStockName(walletId, stockName)
                .orElseGet(() -> new WalletStockHolding(wallet, stockName, 0));

        if (walletStock.getQuantity() == 0) {
            throw new InsufficientWalletStockException(walletId, stockName);
        }

        walletStock.decrease();
        bankStock.increase();

        walletStockHoldingRepository.save(walletStock);
        auditLogEntryRepository.save(new AuditLogEntry(OperationType.SELL, walletId, stockName));
    }
}
