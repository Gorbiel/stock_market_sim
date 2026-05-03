package gorbiel.stock_sim.audit.service;

import gorbiel.stock_sim.audit.dto.AuditLogResponse;

public interface AuditLogService {

    AuditLogResponse getLog();
}
