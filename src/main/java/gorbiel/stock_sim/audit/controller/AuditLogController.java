package gorbiel.stock_sim.audit.controller;

import gorbiel.stock_sim.audit.dto.AuditLogResponse;
import gorbiel.stock_sim.audit.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Audit Log", description = "Endpoint for retrieving successful wallet operation history")
@RestController
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @Operation(summary = "Get audit log", description = "Returns successful wallet operations in order of occurrence.")
    @ApiResponse(
            responseCode = "200",
            description = "Audit log retrieved successfully",
            content = @Content(schema = @Schema(implementation = AuditLogResponse.class)))
    @GetMapping(value = "/log", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuditLogResponse> getLog() {
        return ResponseEntity.ok(auditLogService.getLog());
    }
}
