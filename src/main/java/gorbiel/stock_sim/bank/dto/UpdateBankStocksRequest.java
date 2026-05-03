package gorbiel.stock_sim.bank.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record UpdateBankStocksRequest(@NotEmpty List<@Valid BankStockItemRequest> stocks) {}
