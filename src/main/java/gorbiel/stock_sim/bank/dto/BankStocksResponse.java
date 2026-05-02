package gorbiel.stock_sim.bank.dto;

import java.util.List;

public record BankStocksResponse(List<BankStockItemResponse> stocks) {}
