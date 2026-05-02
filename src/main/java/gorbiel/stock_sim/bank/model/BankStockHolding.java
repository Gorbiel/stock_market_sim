package gorbiel.stock_sim.bank.model;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankStockHolding {

//    @Id
//    @Column(name = "stock_name", nullable = false)
    private String stockName;

//    @Column(nullable = false)
    private int quantity;

    public BankStockHolding(String stockName, int quantity) {
        validateQuantity(quantity);

        this.stockName = stockName;
        this.quantity = quantity;
    }

    public void increase() {
        quantity++;
    }

    public void decrease() {
        if (quantity == 0) {
            throw new IllegalStateException("Bank stock quantity cannot be negative");
        }

        quantity--;
    }

    public void updateQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Bank stock quantity cannot be negative");
        }
    }
}
