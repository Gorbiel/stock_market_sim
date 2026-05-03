package gorbiel.stock_sim.audit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import gorbiel.stock_sim.audit.model.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Single audit log entry")
public record AuditLogEntryResponse(
        @Schema(
                description = "Wallet operation type",
                example = "buy",
                allowableValues = {"buy", "sell"})
        OperationType type,
        @Schema(description = "Wallet ID", example = "wallet-1")
        @JsonProperty("wallet_id")
        String walletId,
        @Schema(description = "Stock name", example = "stock1")
        @JsonProperty("stock_name")
        String stockName) {}