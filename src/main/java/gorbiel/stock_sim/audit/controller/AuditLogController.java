package gorbiel.stock_sim.audit.controller;

import gorbiel.stock_sim.audit.dto.AuditLogResponse;
import gorbiel.stock_sim.audit.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping("/log")
    public ResponseEntity<AuditLogResponse> getLog() {
        return ResponseEntity.ok(auditLogService.getLog());
    }
}
