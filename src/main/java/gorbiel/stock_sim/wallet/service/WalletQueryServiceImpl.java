package gorbiel.stock_sim.wallet.service;

import gorbiel.stock_sim.wallet.dto.WalletResponse;
import gorbiel.stock_sim.wallet.dto.WalletStockResponse;
import gorbiel.stock_sim.wallet.model.WalletStockHolding;
import gorbiel.stock_sim.wallet.repository.WalletStockHoldingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletQueryServiceImpl implements WalletQueryService {

    private final WalletStockHoldingRepository walletStockHoldingRepository;

    @Override
    public WalletResponse getWallet(String walletId) {
        // Returns empty stock list if wallet has no holdings
        List<WalletStockResponse> stocks = walletStockHoldingRepository.findAllByWalletId(walletId).stream()
                .map(holding -> new WalletStockResponse(holding.getStockName(), holding.getQuantity()))
                .toList();

        return new WalletResponse(walletId, stocks);
    }

    @Override
    public int getWalletStockQuantity(String walletId, String stockName) {
        // Missing stock is treated as zero quantity
        return walletStockHoldingRepository
                .findByWalletIdAndStockName(walletId, stockName)
                .map(WalletStockHolding::getQuantity)
                .orElse(0);
    }
}
