package gorbiel.stock_sim.exception.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(description = "Standard API error response")
public record ApiErrorResponse(
        @Schema(description = "HTTP status code", example = "400") int status,
        @Schema(description = "HTTP error reason", example = "Bad Request") String error,
        @Schema(description = "Detailed error message", example = "Insufficient bank stock: stock1") String message,
        @Schema(description = "Request path", example = "/wallets/wallet-1/stocks/stock1") String path,
        @Schema(description = "Error timestamp", example = "2026-05-03T20:15:30.123Z") Instant timestamp) {}
