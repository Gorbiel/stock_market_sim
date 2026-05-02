package gorbiel.stock_sim.bank.service;

import gorbiel.stock_sim.bank.dto.BankStockItemRequest;
import gorbiel.stock_sim.bank.dto.UpdateBankStocksRequest;
import gorbiel.stock_sim.bank.model.BankStockHolding;
import gorbiel.stock_sim.bank.repository.BankStockHoldingRepository;
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

    private BankStockHolding toBankStockHolding(BankStockItemRequest request) {
        return new BankStockHolding(request.name(), request.quantity());
    }
}
