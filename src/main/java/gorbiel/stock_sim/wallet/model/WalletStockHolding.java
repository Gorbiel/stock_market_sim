package gorbiel.stock_sim.wallet.model;

//import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"wallet_id", "stock_name"})})
public class WalletStockHolding {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

//    @Column(name = "stock_name", nullable = false)
    private String stockName;

//    @Column(nullable = false)
    private int quantity;

    public WalletStockHolding(Wallet wallet, String stockName, int quantity) {
        validateQuantity(quantity);

        this.wallet = wallet;
        this.stockName = stockName;
        this.quantity = quantity;
    }

    public void increase() {
        quantity++;
    }

    public void decrease() {
        if (quantity == 0) {
            throw new IllegalStateException("Wallet stock quantity cannot be negative");
        }

        quantity--;
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Wallet stock quantity cannot be negative");
        }
    }
}
