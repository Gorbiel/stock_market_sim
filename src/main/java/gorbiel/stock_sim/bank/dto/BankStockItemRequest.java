package gorbiel.stock_sim.bank.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BankStockItemRequest(@NotBlank String name, @Min(0) int quantity) {}
