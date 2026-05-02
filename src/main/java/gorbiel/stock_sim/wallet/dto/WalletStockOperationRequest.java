package gorbiel.stock_sim.wallet.dto;

import gorbiel.stock_sim.audit.model.OperationType;
import jakarta.validation.constraints.NotNull;

public record WalletStockOperationRequest(@NotNull OperationType type) {}
