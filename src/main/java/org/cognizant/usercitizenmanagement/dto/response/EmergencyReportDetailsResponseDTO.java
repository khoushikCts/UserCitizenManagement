package org.cognizant.usercitizenmanagement.dto.response;

import lombok.Data;

@Data
public class EmergencyReportDetailsResponseDTO {

    private EmergencyReportResponseDTO report;



    private String citizenName;
    private String citizenAddress;

    // Getters and Setters
}