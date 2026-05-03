package gorbiel.stock_sim.wallet.dto;

import java.util.List;

public record WalletResponse(String id, List<WalletStockResponse> stocks) {}
