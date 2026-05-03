package gorbiel.stock_sim.wallet.controller;

import gorbiel.stock_sim.exception.dto.ApiErrorResponse;
import gorbiel.stock_sim.wallet.dto.WalletResponse;
import gorbiel.stock_sim.wallet.dto.WalletStockOperationRequest;
import gorbiel.stock_sim.wallet.service.WalletQueryService;
import gorbiel.stock_sim.wallet.service.WalletStockOperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Wallets", description = "Endpoints for wallet stock operations and queries")
@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletStockOperationService walletStockOperationService;
    private final WalletQueryService walletQueryService;

    @Operation(
            summary = "Buy or sell stock",
            description = "Performs a buy or sell operation on a given wallet and stock.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid operation or insufficient stock",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Stock not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping(
            value = "/wallets/{walletId}/stocks/{stockName}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> processOperation(
            @Parameter(description = "Wallet ID", example = "wallet-1", required = true)
            @PathVariable String walletId,
            @Parameter(description = "Stock name", example = "stock1", required = true)
            @PathVariable String stockName,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content =
                    @Content(
                            schema =
                            @Schema(
                                    implementation =
                                            WalletStockOperationRequest.class)))
            @Valid
            @RequestBody
            WalletStockOperationRequest request) {

        walletStockOperationService.processOperation(walletId, stockName, request.type());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Get wallet state",
            description = "Returns all stocks currently held by the wallet.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Wallet retrieved successfully",
                    content = @Content(schema = @Schema(implementation = WalletResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Wallet not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping(value = "/wallets/{walletId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WalletResponse> getWallet(
            @Parameter(description = "Wallet ID", example = "wallet-1", required = true)
            @PathVariable String walletId) {
        return ResponseEntity.ok(walletQueryService.getWallet(walletId));
    }

    @Operation(
            summary = "Get wallet stock quantity",
            description = "Returns the quantity of a specific stock in a wallet.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantity retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Stock or wallet not found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping(value = "/wallets/{walletId}/stocks/{stockName}")
    public ResponseEntity<Integer> getWalletStockQuantity(
            @Parameter(description = "Wallet ID", example = "wallet-1", required = true)
            @PathVariable String walletId,
            @Parameter(description = "Stock name", example = "stock1", required = true)
            @PathVariable String stockName) {

        return ResponseEntity.ok(
                walletQueryService.getWalletStockQuantity(walletId, stockName));
    }
}