package gorbiel.stock_sim.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gorbiel.stock_sim.audit.model.AuditLogEntry;
import gorbiel.stock_sim.audit.model.OperationType;
import gorbiel.stock_sim.audit.repository.AuditLogEntryRepository;
import gorbiel.stock_sim.bank.model.BankStockHolding;
import gorbiel.stock_sim.bank.repository.BankStockHoldingRepository;
import gorbiel.stock_sim.wallet.model.Wallet;
import gorbiel.stock_sim.wallet.model.WalletStockHolding;
import gorbiel.stock_sim.wallet.repository.WalletRepository;
import gorbiel.stock_sim.wallet.repository.WalletStockHoldingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class CoreRepositoryTest {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletStockHoldingRepository walletStockHoldingRepository;

    @Autowired
    private BankStockHoldingRepository bankStockHoldingRepository;

    @Autowired
    private AuditLogEntryRepository auditLogEntryRepository;

    @Test
    void shouldSaveAndLoadWalletStockHolding() {
        Wallet wallet = walletRepository.save(new Wallet("wallet-1"));
        walletStockHoldingRepository.save(new WalletStockHolding(wallet, "AAPL", 3));

        WalletStockHolding result = walletStockHoldingRepository
                .findByWalletIdAndStockName("wallet-1", "AAPL")
                .orElseThrow();

        assertThat(result.getWallet().getId()).isEqualTo("wallet-1");
        assertThat(result.getStockName()).isEqualTo("AAPL");
        assertThat(result.getQuantity()).isEqualTo(3);
    }

    @Test
    void shouldSaveAndLoadBankStockHolding() {
        bankStockHoldingRepository.save(new BankStockHolding("AAPL", 10));

        BankStockHolding result = bankStockHoldingRepository.findById("AAPL").orElseThrow();

        assertThat(result.getStockName()).isEqualTo("AAPL");
        assertThat(result.getQuantity()).isEqualTo(10);
    }

    @Test
    void shouldSaveAuditLogEntriesInOrder() {
        auditLogEntryRepository.save(new AuditLogEntry(OperationType.BUY, "wallet-1", "AAPL"));
        auditLogEntryRepository.save(new AuditLogEntry(OperationType.SELL, "wallet-1", "AAPL"));

        var result = auditLogEntryRepository.findAllByOrderByIdAsc();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getType()).isEqualTo(OperationType.BUY);
        assertThat(result.get(1).getType()).isEqualTo(OperationType.SELL);
    }

    @Test
    void shouldRejectDuplicateWalletStockHolding() {
        Wallet wallet = walletRepository.save(new Wallet("wallet-1"));

        walletStockHoldingRepository.save(new WalletStockHolding(wallet, "AAPL", 1));
        walletStockHoldingRepository.flush();

        assertThatThrownBy(() -> walletStockHoldingRepository.saveAndFlush(new WalletStockHolding(wallet, "AAPL", 2)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldRejectNegativeBankStockQuantity() {
        assertThatThrownBy(() -> bankStockHoldingRepository.saveAndFlush(new BankStockHolding("AAPL", -1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldRejectNegativeWalletStockQuantity() {
        Wallet wallet = walletRepository.save(new Wallet("wallet-1"));

        assertThatThrownBy(() -> new WalletStockHolding(wallet, "AAPL", -1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
