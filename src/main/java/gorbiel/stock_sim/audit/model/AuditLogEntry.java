package gorbiel.stock_sim.audit.model;

//import jakarta.persistence.*;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuditLogEntry {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
    private OperationType type;

//    @Column(nullable = false)
    private String walletId;

//    @Column(nullable = false)
    private String stockName;

//    @Column(nullable = false)
    private Instant createdAt;

    public AuditLogEntry(OperationType type, String walletId, String stockName) {
        this.type = type;
        this.walletId = walletId;
        this.stockName = stockName;
        this.createdAt = Instant.now();
    }
}
