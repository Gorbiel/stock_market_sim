package gorbiel.stock_sim.wallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Single stock entry in wallet")
public record WalletStockResponse(
        @Schema(description = "Stock name", example = "stock1") String name,
        @Schema(description = "Quantity held in wallet", example = "5") int quantity) {}