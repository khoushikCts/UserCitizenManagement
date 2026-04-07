package org.cognizant.usercitizenmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.cognizant.usercitizenmanagement.Enum.EmergencyType;
import org.cognizant.usercitizenmanagement.Enum.ReportStatus;

@Data
public class EmergencyReportRequestDTO {

    @NotNull(message = "Citizen ID is required")
    private Integer citizenId;

    @NotNull(message = "Emergency type is required")
    private EmergencyType type;

    @NotBlank(message = "Location is required")
    private String location;
    @NotNull(message = "Status is required")
    private ReportStatus status;







}