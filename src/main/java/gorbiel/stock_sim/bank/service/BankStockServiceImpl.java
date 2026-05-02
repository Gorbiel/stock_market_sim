package gorbiel.stock_sim.bank.service;

import gorbiel.stock_sim.bank.dto.BankStockItemRequest;
import gorbiel.stock_sim.bank.dto.BankStockItemResponse;
import gorbiel.stock_sim.bank.dto.BankStocksResponse;
import gorbiel.stock_sim.bank.dto.UpdateBankStocksRequest;
import gorbiel.stock_sim.bank.model.BankStockHolding;
import gorbiel.stock_sim.bank.repository.BankStockHoldingRepository;
import gorbiel.stock_sim.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankStockServiceImpl implements BankStockService {

    private final BankStockHoldingRepository bankStockHoldingRepository;

    @Override
    @Transactional
    public void updateBankStocks(UpdateBankStocksRequest request) {
        bankStockHoldingRepository.deleteAll();

        List<BankStockHolding> holdings =
                request.stocks().stream().map(this::toBankStockHolding).toList();

        bankStockHoldingRepository.saveAll(holdings);
    }

    @Override
    public BankStocksResponse getBankStocks() {
        return new BankStocksResponse(bankStockHoldingRepository.findAll().stream()
                .map(h -> new BankStockItemResponse(h.getStockName(), h.getQuantity()))
                .toList());
    }

    @Override
    public BankStockHolding getExistingStock(String stockName) {
        return bankStockHoldingRepository
                .findById(stockName)
                .orElseThrow(() -> new ResourceNotFoundException("Stock not found: " + stockName));
    }

    private BankStockHolding toBankStockHolding(BankStockItemRequest request) {
        return new BankStockHolding(request.name(), request.quantity());
    }
}
