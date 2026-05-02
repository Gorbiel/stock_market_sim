package gorbiel.stock_sim.bank.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateBankStocksRequest(@NotNull List<@Valid BankStockItemRequest> stocks) {}
