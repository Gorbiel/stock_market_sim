package gorbiel.stock_sim.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankStockHoldingRepository bankStockHoldingRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletStockHoldingRepository walletStockHoldingRepository;

    @BeforeEach
    void setUp() {
        walletStockHoldingRepository.deleteAll();
        walletRepository.deleteAll();
        bankStockHoldingRepository.deleteAll();
    }

    @Test
    void shouldReturnNotFoundForUnknownStock() throws Exception {
        mockMvc.perform(
                        post("/wallets/wallet-1/stocks/unknown")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                { "type": "buy" }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/wallets/wallet-1/stocks/unknown"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturnBadRequestWhenBuyingWithNoBankStock() throws Exception {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 0));

        mockMvc.perform(
                        post("/wallets/wallet-1/stocks/stock1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                { "type": "buy" }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/wallets/wallet-1/stocks/stock1"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturnBadRequestWhenSellingWithNoWalletStock() throws Exception {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));

        mockMvc.perform(
                        post("/wallets/wallet-1/stocks/stock1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                { "type": "sell" }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/wallets/wallet-1/stocks/stock1"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturnBadRequestForInvalidOperationType() throws Exception {
        bankStockHoldingRepository.save(new BankStockHolding("stock1", 1));

        mockMvc.perform(
                        post("/wallets/wallet-1/stocks/stock1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                { "type": "hold" }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/wallets/wallet-1/stocks/stock1"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturnBadRequestForMalformedRequestBody() throws Exception {
        mockMvc.perform(post("/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "stocks": [
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/stocks"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturnBadRequestForNegativeStockQuantity() throws Exception {
        mockMvc.perform(
                        post("/stocks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                  "stocks": [
                                    { "name": "stock1", "quantity": -1 }
                                  ]
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/stocks"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
