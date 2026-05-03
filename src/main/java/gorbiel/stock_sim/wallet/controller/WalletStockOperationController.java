package gorbiel.stock_sim.wallet.controller;

import gorbiel.stock_sim.wallet.dto.WalletStockOperationRequest;
import gorbiel.stock_sim.wallet.service.WalletStockOperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WalletStockOperationController {

    private final WalletStockOperationService walletStockOperationService;

    @PostMapping("/wallets/{walletId}/stocks/{stockName}")
    public ResponseEntity<Void> processOperation(
            @PathVariable String walletId,
            @PathVariable String stockName,
            @Valid @RequestBody WalletStockOperationRequest request) {
        walletStockOperationService.processOperation(walletId, stockName, request.type());
        return ResponseEntity.ok().build();
    }
}
