package gorbiel.stock_sim.audit.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Audit log response")
public record AuditLogResponse(
        @ArraySchema(
                schema = @Schema(implementation = AuditLogEntryResponse.class),
                arraySchema = @Schema(description = "Successful wallet operations in order"))
        List<AuditLogEntryResponse> log) {}