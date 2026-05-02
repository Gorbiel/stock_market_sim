package gorbiel.stock_sim.audit.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AuditLogEntryTest {

    @Test
    void shouldCreateBuyAuditLogEntry() {
        AuditLogEntry entry = new AuditLogEntry(OperationType.BUY, "wallet1", "stock1");

        assertThat(entry.getType()).isEqualTo(OperationType.BUY);
        assertThat(entry.getWalletId()).isEqualTo("wallet1");
        assertThat(entry.getStockName()).isEqualTo("stock1");
        assertThat(entry.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldCreateSellAuditLogEntry() {
        AuditLogEntry entry = new AuditLogEntry(OperationType.SELL, "wallet1", "stock1");

        assertThat(entry.getType()).isEqualTo(OperationType.SELL);
    }
}