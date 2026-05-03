package gorbiel.stock_sim.wallet.service;

import gorbiel.stock_sim.wallet.dto.WalletResponse;

public interface WalletQueryService {

    WalletResponse getWallet(String walletId);
}
