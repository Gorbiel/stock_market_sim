package gorbiel.stock_sim.bank.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class BankStockHoldingTest {

    @Test
    void shouldCreateHoldingWithPositiveQuantity() {
        BankStockHolding holding = new BankStockHolding("stock1", 10);

        assertThat(holding.getStockName()).isEqualTo("stock1");
        assertThat(holding.getQuantity()).isEqualTo(10);
    }

    @Test
    void shouldRejectNegativeQuantity() {
        assertThatThrownBy(() -> new BankStockHolding("stock1", -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Bank stock quantity cannot be negative");
    }

    @Test
    void shouldIncreaseQuantity() {
        BankStockHolding holding = new BankStockHolding("stock1", 1);

        holding.increase();

        assertThat(holding.getQuantity()).isEqualTo(2);
    }

    @Test
    void shouldDecreaseQuantity() {
        BankStockHolding holding = new BankStockHolding("stock1", 1);

        holding.decrease();

        assertThat(holding.getQuantity()).isZero();
    }

    @Test
    void shouldNotDecreaseBelowZero() {
        BankStockHolding holding = new BankStockHolding("stock1", 0);

        assertThatThrownBy(holding::decrease)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Bank stock quantity cannot be negative");
    }

    @Test
    void shouldUpdateQuantity() {
        BankStockHolding holding = new BankStockHolding("stock1", 1);

        holding.updateQuantity(5);

        assertThat(holding.getQuantity()).isEqualTo(5);
    }
}
