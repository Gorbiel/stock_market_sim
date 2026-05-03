package gorbiel.stock_sim.wallet.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a wallet that can hold stocks.
 *
 * <p>Wallets are created lazily when wallet operations target a missing wallet.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet {

    @Id
    private String id;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<WalletStockHolding> holdings = new HashSet<>();

    public Wallet(String id) {
        this.id = id;
    }
}
