package gorbiel.stock_sim.bank.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Current bank stock state")
public record BankStocksResponse(
        @ArraySchema(
                        schema = @Schema(implementation = BankStockItemResponse.class),
                        arraySchema = @Schema(description = "Stocks currently available in the bank"))
                List<BankStockItemResponse> stocks) {}
