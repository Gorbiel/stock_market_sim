package gorbiel.stock_sim.bank.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gorbiel.stock_sim.bank.repository.BankStockHoldingRepository;
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
class BankStockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BankStockHoldingRepository bankStockHoldingRepository;

    @BeforeEach
    void setUp() {
        bankStockHoldingRepository.deleteAll();
    }

    @Test
    void shouldReturnBankStocks() throws Exception {
        mockMvc.perform(
                        post("/stocks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                  "stocks": [
                                    { "name": "stock1", "quantity": 99 },
                                    { "name": "stock2", "quantity": 1 }
                                  ]
                                }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks", hasSize(2)))
                .andExpect(jsonPath("$.stocks[0].name").exists())
                .andExpect(jsonPath("$.stocks[0].quantity").exists());
    }

    @Test
    void shouldSetBankStockState() throws Exception {
        mockMvc.perform(
                        post("/stocks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                  "stocks": [
                                    { "name": "stock1", "quantity": 99 }
                                  ]
                                }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks", hasSize(1)))
                .andExpect(jsonPath("$.stocks[0].name").value("stock1"))
                .andExpect(jsonPath("$.stocks[0].quantity").value(99));
    }

    @Test
    void shouldReplacePreviousBankStockState() throws Exception {
        mockMvc.perform(
                        post("/stocks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                  "stocks": [
                                    { "name": "stock1", "quantity": 99 }
                                  ]
                                }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(
                        post("/stocks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                  "stocks": [
                                    { "name": "stock2", "quantity": 1 }
                                  ]
                                }
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/stocks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stocks", hasSize(1)))
                .andExpect(jsonPath("$.stocks[0].name").value("stock2"))
                .andExpect(jsonPath("$.stocks[0].quantity").value(1));
    }

    @Test
    void shouldRejectNegativeQuantity() throws Exception {
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
                .andExpect(status().isBadRequest());
    }
}
