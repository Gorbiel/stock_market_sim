package gorbiel.stock_sim.audit.repository;

import gorbiel.stock_sim.audit.model.AuditLogEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogEntryRepository extends JpaRepository<AuditLogEntry, Long> {

    List<AuditLogEntry> findAllByOrderByIdAsc();
}
