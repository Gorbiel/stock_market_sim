package gorbiel.stock_sim.audit.service;

import gorbiel.stock_sim.audit.dto.AuditLogEntryResponse;
import gorbiel.stock_sim.audit.dto.AuditLogResponse;
import gorbiel.stock_sim.audit.repository.AuditLogEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogEntryRepository auditLogEntryRepository;

    @Override
    public AuditLogResponse getLog() {
        return new AuditLogResponse(auditLogEntryRepository.findAllByOrderByIdAsc().stream()
                .map(entry -> new AuditLogEntryResponse(entry.getType(), entry.getWalletId(), entry.getStockName()))
                .toList());
    }
}
