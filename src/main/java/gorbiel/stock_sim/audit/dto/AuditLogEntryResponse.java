package gorbiel.stock_sim.audit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import gorbiel.stock_sim.audit.model.OperationType;

public record AuditLogEntryResponse(
        OperationType type, @JsonProperty("wallet_id") String walletId, @JsonProperty("stock_name") String stockName) {}
