package gorbiel.stock_sim.wallet.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import gorbiel.stock_sim.audit.model.OperationType;
import gorbiel.stock_sim.audit.repository.AuditLogEntryRepository;
import gorbiel.stock_sim.bank.model.BankStockHolding;
import gorbiel.stock_sim.bank.repository.BankStockHoldingRepository;
import gorbiel.stock_sim.exception.model.BadRequestException;
import gorbiel.stock_sim.exception.model.ResourceNotFoundException;
import gorbiel.stock_sim.wallet.model.Wallet;
import gorbiel.stock_sim.wallet.model.WalletStockHolding;
import gorbiel.stock_sim.wallet.repository.WalletRepository;
import gorbiel.stock_sim.wallet.repository.WalletStockHoldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WalletStockOperationServiceImplTest {

    @Autowired
    private WalletStockOperationService walletStockOperationService;

    @Autowired
    private BankStockHoldingRepository bankStockHoldingRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletStockHoldingRepository walletStockHoldingRepository;

    @Autowired
    private AuditLogEntryRepository auditLogEntryRepository;

    @BeforeEach
    void setUp() {
        auditLogEntryRepository.deleteAll();
        walletStockHoldingRepository.deleteAll();
        walletRepository.deleteAll();
        bankStockHoldingRepository.deleteAll();
    }

    @Test
    void shouldCreateWalletWhenBuyingForMissingWallet() {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));

        walletStockOperationService.processOperation("wallet-1", "stock1", OperationType.BUY);

        assertThat(walletRepository.existsById("wallet-1")).isTrue();
    }

    @Test
    void shouldFailSellingForMissingWalletWithNoStock() {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));

        assertThatThrownBy(() -> walletStockOperationService.processOperation("wallet-1", "stock1", OperationType.SELL))
                .isInstanceOf(BadRequestException.class);

        assertThat(auditLogEntryRepository.findAll()).isEmpty();
    }

    @Test
    void shouldFailBuyingWhenStockDoesNotExist() {
        assertThatThrownBy(() -> walletStockOperationService.processOperation("wallet-1", "unknown", OperationType.BUY))
                .isInstanceOf(ResourceNotFoundException.class);

        assertThat(auditLogEntryRepository.findAll()).isEmpty();
    }

    @Test
    void shouldFailBuyingWhenBankHasNoStock() {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 0));

        assertThatThrownBy(() -> walletStockOperationService.processOperation("wallet-1", "stock1", OperationType.BUY))
                .isInstanceOf(BadRequestException.class);

        assertThat(auditLogEntryRepository.findAll()).isEmpty();
    }

    @Test
    void shouldFailSellingWhenWalletHasNoStock() {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));
        Wallet wallet = walletRepository.save(new Wallet("wallet-1"));
        walletStockHoldingRepository.save(new WalletStockHolding(wallet, "stock1", 0));

        assertThatThrownBy(() -> walletStockOperationService.processOperation("wallet-1", "stock1", OperationType.SELL))
                .isInstanceOf(BadRequestException.class);

        assertThat(auditLogEntryRepository.findAll()).isEmpty();
    }

    @Test
    void shouldUpdateBankAndWalletQuantitiesWhenBuying() {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 2));

        walletStockOperationService.processOperation("wallet-1", "stock1", OperationType.BUY);

        assertThat(bankStockHoldingRepository.findById("stock1").orElseThrow().getQuantity())
                .isEqualTo(1);
        assertThat(walletStockHoldingRepository
                        .findByWalletIdAndStockName("wallet-1", "stock1")
                        .orElseThrow()
                        .getQuantity())
                .isEqualTo(1);
    }

    @Test
    void shouldUpdateBankAndWalletQuantitiesWhenSelling() {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));
        Wallet wallet = walletRepository.save(new Wallet("wallet-1"));
        walletStockHoldingRepository.save(new WalletStockHolding(wallet, "stock1", 2));

        walletStockOperationService.processOperation("wallet-1", "stock1", OperationType.SELL);

        assertThat(bankStockHoldingRepository.findById("stock1").orElseThrow().getQuantity())
                .isEqualTo(2);
        assertThat(walletStockHoldingRepository
                        .findByWalletIdAndStockName("wallet-1", "stock1")
                        .orElseThrow()
                        .getQuantity())
                .isEqualTo(1);
    }

    @Test
    void shouldLogOnlySuccessfulOperations() {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));

        assertThatThrownBy(() -> walletStockOperationService.processOperation("wallet-1", "unknown", OperationType.BUY))
                .isInstanceOf(ResourceNotFoundException.class);

        walletStockOperationService.processOperation("wallet-1", "stock1", OperationType.BUY);

        var logs = auditLogEntryRepository.findAllByOrderByIdAsc();

        assertThat(logs).hasSize(1);
        assertThat(logs.getFirst().getType()).isEqualTo(OperationType.BUY);
        assertThat(logs.getFirst().getWalletId()).isEqualTo("wallet-1");
        assertThat(logs.getFirst().getStockName()).isEqualTo("stock1");
    }
}
