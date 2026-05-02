package gorbiel.stock_sim.bank.controller;

import gorbiel.stock_sim.bank.dto.UpdateBankStocksRequest;
import gorbiel.stock_sim.bank.service.BankStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BankStockController {

    private final BankStockService bankStockService;

    @PostMapping("/stocks")
    public ResponseEntity<Void> updateBankStocks(@Valid @RequestBody UpdateBankStocksRequest request) {
        bankStockService.updateBankStocks(request);
        return ResponseEntity.ok().build();
    }
}
