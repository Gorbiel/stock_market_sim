package gorbiel.stock_sim.wallet.controller;

import gorbiel.stock_sim.wallet.dto.WalletResponse;
import gorbiel.stock_sim.wallet.dto.WalletStockOperationRequest;
import gorbiel.stock_sim.wallet.service.WalletQueryService;
import gorbiel.stock_sim.wallet.service.WalletStockOperationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletStockOperationService walletStockOperationService;
    private final WalletQueryService walletQueryService;

    @PostMapping("/wallets/{walletId}/stocks/{stockName}")
    public ResponseEntity<Void> processOperation(
            @PathVariable String walletId,
            @PathVariable String stockName,
            @Valid @RequestBody WalletStockOperationRequest request) {
        walletStockOperationService.processOperation(walletId, stockName, request.type());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable String walletId) {
        return ResponseEntity.ok(walletQueryService.getWallet(walletId));
    }
}
