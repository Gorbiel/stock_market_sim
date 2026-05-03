package gorbiel.stock_sim.wallet.dto;

import gorbiel.stock_sim.audit.model.OperationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request to perform a wallet stock operation")
public record WalletStockOperationRequest(
        @Schema(
                description = "Operation type",
                example = "BUY",
                allowableValues = {"BUY", "SELL"},
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        OperationType type) {}