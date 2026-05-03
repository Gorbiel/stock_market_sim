package gorbiel.stock_sim.audit.repository;

import gorbiel.stock_sim.audit.model.AuditLogEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for accessing audit log entries.
 *
 * <p>Entries are stored in insertion order and can be retrieved in ascending order of occurrence.
 */
public interface AuditLogEntryRepository extends JpaRepository<AuditLogEntry, Long> {

    /**
     * Retrieves all audit log entries ordered by their insertion order.
     *
     * @return list of audit log entries sorted by ID ascending
     */
    List<AuditLogEntry> findAllByOrderByIdAsc();
}
