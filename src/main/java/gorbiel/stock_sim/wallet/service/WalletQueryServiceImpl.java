package gorbiel.stock_sim.wallet.service;

import gorbiel.stock_sim.wallet.dto.WalletResponse;
import gorbiel.stock_sim.wallet.dto.WalletStockResponse;
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
        List<WalletStockResponse> stocks = walletStockHoldingRepository.findAllByWalletId(walletId).stream()
                .map(holding -> new WalletStockResponse(holding.getStockName(), holding.getQuantity()))
                .toList();

        return new WalletResponse(walletId, stocks);
    }
}
