package gorbiel.stock_sim.wallet.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Wallet state response")
public record WalletResponse(
        @Schema(description = "Wallet ID", example = "wallet-1") String id,
        @ArraySchema(
                schema = @Schema(implementation = WalletStockResponse.class),
                arraySchema = @Schema(description = "Stocks held by the wallet"))
        List<WalletStockResponse> stocks) {}