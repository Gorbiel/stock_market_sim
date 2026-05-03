package gorbiel.stock_sim.bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Single stock entry used to define bank stock state")
public record BankStockItemRequest(
        @Schema(description = "Stock name", example = "stock1", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank
                String name,
        @Schema(
                        description = "Quantity available in the bank",
                        example = "99",
                        minimum = "0",
                        requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(0)
                int quantity) {}
