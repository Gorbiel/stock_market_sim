package gorbiel.stock_sim.exception.dto;

import java.time.Instant;

public record ApiErrorResponse(int status, String error, String message, String path, Instant timestamp) {}
