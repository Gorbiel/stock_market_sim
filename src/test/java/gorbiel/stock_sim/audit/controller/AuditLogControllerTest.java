package gorbiel.stock_sim.audit.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gorbiel.stock_sim.audit.repository.AuditLogEntryRepository;
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
class AuditLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuditLogEntryRepository auditLogEntryRepository;

    @Autowired
    private WalletStockHoldingRepository walletStockHoldingRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BankStockHoldingRepository bankStockHoldingRepository;

    @BeforeEach
    void setUp() {
        auditLogEntryRepository.deleteAll();
        walletStockHoldingRepository.deleteAll();
        walletRepository.deleteAll();
        bankStockHoldingRepository.deleteAll();
    }

    @Test
    void shouldLogSuccessfulBuyOperation() throws Exception {
        setBankState(
                """
                {
                  "stocks": [
                    { "name": "stock1", "quantity": 1 }
                  ]
                }
                """);

        buy("wallet-1", "stock1");

        mockMvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.log", hasSize(1)))
                .andExpect(jsonPath("$.log[0].type").value("buy"))
                .andExpect(jsonPath("$.log[0].wallet_id").value("wallet-1"))
                .andExpect(jsonPath("$.log[0].stock_name").value("stock1"));
    }

    @Test
    void shouldLogSuccessfulSellOperation() throws Exception {
        setBankState(
                """
                {
                  "stocks": [
                    { "name": "stock1", "quantity": 1 }
                  ]
                }
                """);

        buy("wallet-1", "stock1");
        sell("wallet-1", "stock1");

        mockMvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.log", hasSize(2)))
                .andExpect(jsonPath("$.log[1].type").value("sell"))
                .andExpect(jsonPath("$.log[1].wallet_id").value("wallet-1"))
                .andExpect(jsonPath("$.log[1].stock_name").value("stock1"));
    }

    @Test
    void shouldNotLogFailedBuyOperation() throws Exception {
        setBankState(
                """
                {
                  "stocks": [
                    { "name": "stock1", "quantity": 0 }
                  ]
                }
                """);

        mockMvc.perform(
                        post("/wallets/wallet-1/stocks/stock1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                { "type": "buy" }
                                """))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/log")).andExpect(status().isOk()).andExpect(jsonPath("$.log", hasSize(0)));
    }

    @Test
    void shouldNotLogFailedSellOperation() throws Exception {
        setBankState(
                """
                {
                  "stocks": [
                    { "name": "stock1", "quantity": 1 }
                  ]
                }
                """);

        mockMvc.perform(
                        post("/wallets/wallet-1/stocks/stock1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                { "type": "sell" }
                                """))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/log")).andExpect(status().isOk()).andExpect(jsonPath("$.log", hasSize(0)));
    }

    @Test
    void shouldNotLogBankStateUpdates() throws Exception {
        setBankState(
                """
                {
                  "stocks": [
                    { "name": "stock1", "quantity": 10 }
                  ]
                }
                """);

        mockMvc.perform(get("/log")).andExpect(status().isOk()).andExpect(jsonPath("$.log", hasSize(0)));
    }

    @Test
    void shouldReturnLogEntriesInOrder() throws Exception {
        setBankState(
                """
                {
                  "stocks": [
                    { "name": "stock1", "quantity": 2 }
                  ]
                }
                """);

        buy("wallet-1", "stock1");
        sell("wallet-1", "stock1");
        buy("wallet-2", "stock1");

        mockMvc.perform(get("/log"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.log", hasSize(3)))
                .andExpect(jsonPath("$.log[0].type").value("buy"))
                .andExpect(jsonPath("$.log[0].wallet_id").value("wallet-1"))
                .andExpect(jsonPath("$.log[1].type").value("sell"))
                .andExpect(jsonPath("$.log[1].wallet_id").value("wallet-1"))
                .andExpect(jsonPath("$.log[2].type").value("buy"))
                .andExpect(jsonPath("$.log[2].wallet_id").value("wallet-2"));
    }

    private void setBankState(String body) throws Exception {
        mockMvc.perform(post("/stocks").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());
    }

    private void buy(String walletId, String stockName) throws Exception {
        mockMvc.perform(
                        post("/wallets/%s/stocks/%s".formatted(walletId, stockName))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                { "type": "buy" }
                                """))
                .andExpect(status().isOk());
    }

    private void sell(String walletId, String stockName) throws Exception {
        mockMvc.perform(
                        post("/wallets/%s/stocks/%s".formatted(walletId, stockName))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                { "type": "sell" }
                                """))
                .andExpect(status().isOk());
    }
}
