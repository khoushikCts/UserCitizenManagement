package org.cognizant.usercitizenmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer auditLogId;
    private String action;
    private String resource;
    private LocalDateTime timestamp;
    private String ipAddress;
    private String details;
}