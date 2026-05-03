package gorbiel.stock_sim.bank.controller;

import gorbiel.stock_sim.bank.dto.BankStocksResponse;
import gorbiel.stock_sim.bank.dto.UpdateBankStocksRequest;
import gorbiel.stock_sim.bank.service.BankStockService;
import gorbiel.stock_sim.exception.dto.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "Bank Stocks", description = "Endpoints for managing stocks available in the bank")
@RestController
@RequiredArgsConstructor
public class BankStockController {

    private final BankStockService bankStockService;

    @Operation(
            summary = "Set bank stock state",
            description = "Replaces the entire bank stock state with the provided stock list.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Bank stock state updated successfully"),
        @ApiResponse(
                responseCode = "400",
                description = "Invalid request body or validation error",
                content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping(value = "/stocks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateBankStocks(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            required = true,
                            content = @Content(schema = @Schema(implementation = UpdateBankStocksRequest.class)))
                    @Valid
                    @RequestBody
                    UpdateBankStocksRequest request) {
        bankStockService.updateBankStocks(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get bank stock state", description = "Returns all stocks currently available in the bank.")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Bank stock state retrieved successfully",
                content = @Content(schema = @Schema(implementation = BankStocksResponse.class)))
    })
    @GetMapping(value = "/stocks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BankStocksResponse> getBankStocks() {
        return ResponseEntity.ok(bankStockService.getBankStocks());
    }
}
