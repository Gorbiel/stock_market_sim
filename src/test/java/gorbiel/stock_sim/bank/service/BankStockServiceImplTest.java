package gorbiel.stock_sim.bank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import gorbiel.stock_sim.bank.model.BankStockHolding;
import gorbiel.stock_sim.bank.repository.BankStockHoldingRepository;
import gorbiel.stock_sim.exception.model.ResourceNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BankStockServiceImplTest {

    @Mock
    private BankStockHoldingRepository bankStockHoldingRepository;

    @InjectMocks
    private BankStockServiceImpl bankStockService;

    @Test
    void shouldReturnExistingStock() {
        BankStockHolding holding = new BankStockHolding("AAPL", 10);
        when(bankStockHoldingRepository.findById("AAPL")).thenReturn(Optional.of(holding));

        BankStockHolding result = bankStockService.getExistingStock("AAPL");

        assertThat(result).isEqualTo(holding);
    }

    @Test
    void shouldThrowWhenStockDoesNotExist() {
        when(bankStockHoldingRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bankStockService.getExistingStock("UNKNOWN"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Stock not found: UNKNOWN");
    }
}
