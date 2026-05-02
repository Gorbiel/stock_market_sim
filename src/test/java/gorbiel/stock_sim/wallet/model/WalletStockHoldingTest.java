package gorbiel.stock_sim.wallet.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class WalletStockHoldingTest {

    @Test
    void shouldCreateHoldingWithPositiveQuantity() {
        Wallet wallet = new Wallet("wallet1");

        WalletStockHolding holding = new WalletStockHolding(wallet, "stock1", 10);

        assertThat(holding.getWallet()).isEqualTo(wallet);
        assertThat(holding.getStockName()).isEqualTo("stock1");
        assertThat(holding.getQuantity()).isEqualTo(10);
    }

    @Test
    void shouldRejectNegativeQuantity() {
        Wallet wallet = new Wallet("wallet1");

        assertThatThrownBy(() -> new WalletStockHolding(wallet, "stock1", -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Wallet stock quantity cannot be negative");
    }

    @Test
    void shouldIncreaseQuantity() {
        Wallet wallet = new Wallet("wallet1");
        WalletStockHolding holding = new WalletStockHolding(wallet, "stock1", 1);

        holding.increase();

        assertThat(holding.getQuantity()).isEqualTo(2);
    }

    @Test
    void shouldDecreaseQuantity() {
        Wallet wallet = new Wallet("wallet1");
        WalletStockHolding holding = new WalletStockHolding(wallet, "stock1", 1);

        holding.decrease();

        assertThat(holding.getQuantity()).isZero();
    }

    @Test
    void shouldNotDecreaseBelowZero() {
        Wallet wallet = new Wallet("wallet1");
        WalletStockHolding holding = new WalletStockHolding(wallet, "stock1", 0);

        assertThatThrownBy(holding::decrease)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Wallet stock quantity cannot be negative");
    }
}