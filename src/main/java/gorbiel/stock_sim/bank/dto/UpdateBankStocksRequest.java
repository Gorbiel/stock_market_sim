package gorbiel.stock_sim.bank.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@Schema(description = "Request used to replace the full bank stock state")
public record UpdateBankStocksRequest(
        @ArraySchema(
                schema = @Schema(implementation = BankStockItemRequest.class),
                arraySchema =
                @Schema(
                        description = "New complete list of bank stock holdings",
                        requiredMode = Schema.RequiredMode.REQUIRED))
        @NotEmpty
        List<@Valid BankStockItemRequest> stocks) {}