package gorbiel.stock_sim.audit.service;

import gorbiel.stock_sim.audit.dto.AuditLogResponse;

/**
 * Provides access to audit log entries representing successful wallet operations.
 *
 * <p>Only successful buy/sell operations are recorded. Failed operations and
 * bank state updates are not included in the audit log.
 *
 * <p>Entries are returned in the order of occurrence.
 */
public interface AuditLogService {

    /**
     * Retrieves the full audit log.
     *
     * @return audit log entries in chronological order
     */
    AuditLogResponse getLog();
}
