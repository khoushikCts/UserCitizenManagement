package org.cognizant.usercitizenmanagement.dto.response;

import lombok.Data;
import org.cognizant.usercitizenmanagement.Enum.EmergencyType;
import org.cognizant.usercitizenmanagement.Enum.ReportStatus;

import java.time.LocalDateTime;

@Data
public class EmergencyReportResponseDTO {



    private Integer reportId;
    private Integer citizenId;
    private EmergencyType type;
    private String location;
    private ReportStatus status;
    private LocalDateTime date;

    // Getters and Setters
}