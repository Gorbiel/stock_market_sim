package gorbiel.stock_sim.audit.dto;

import java.util.List;

public record AuditLogResponse(List<AuditLogEntryResponse> log) {}
