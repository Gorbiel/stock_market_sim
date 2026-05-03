package gorbiel.stock_sim.wallet.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gorbiel.stock_sim.audit.repository.AuditLogEntryRepository;
import gorbiel.stock_sim.bank.model.BankStockHolding;
import gorbiel.stock_sim.bank.repository.BankStockHoldingRepository;
import gorbiel.stock_sim.wallet.repository.WalletRepository;
import gorbiel.stock_sim.wallet.repository.WalletStockHoldingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    void shouldBuyStock() throws Exception {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));

        mockMvc.perform(post("/wallets/wallet-1/stocks/stock1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "type": "buy" }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wallets/wallet-1/stocks/stock1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void shouldSellStock() throws Exception {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));

        mockMvc.perform(post("/wallets/wallet-1/stocks/stock1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "type": "buy" }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(post("/wallets/wallet-1/stocks/stock1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "type": "sell" }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wallets/wallet-1/stocks/stock1"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void shouldReturnNotFoundForUnknownStock() throws Exception {
        mockMvc.perform(post("/wallets/wallet-1/stocks/unknown")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "type": "buy" }
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenBankHasNoStock() throws Exception {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 0));

        mockMvc.perform(post("/wallets/wallet-1/stocks/stock1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "type": "buy" }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenWalletHasNoStock() throws Exception {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));

        mockMvc.perform(post("/wallets/wallet-1/stocks/stock1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "type": "sell" }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnWalletState() throws Exception {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 2));

        mockMvc.perform(post("/wallets/wallet-1/stocks/stock1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "type": "buy" }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wallets/wallet-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("wallet-1"))
                .andExpect(jsonPath("$.stocks", hasSize(1)))
                .andExpect(jsonPath("$.stocks[0].name").value("stock1"))
                .andExpect(jsonPath("$.stocks[0].quantity").value(1));
    }

    @Test
    void shouldReturnWalletStockQuantity() throws Exception {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));

        mockMvc.perform(post("/wallets/wallet-1/stocks/stock1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "type": "buy" }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/wallets/wallet-1/stocks/stock1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}
