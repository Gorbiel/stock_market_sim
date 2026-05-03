package gorbiel.stock_sim.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Single stock entry returned from bank state")
public record BankStockItemResponse(
        @Schema(description = "Stock name", example = "stock1")
        String name,

        @Schema(description = "Quantity available in the bank", example = "99")
        int quantity) {}
