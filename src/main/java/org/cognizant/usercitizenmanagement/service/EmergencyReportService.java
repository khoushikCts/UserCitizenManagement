package org.cognizant.usercitizenmanagement.service;

import org.cognizant.usercitizenmanagement.dao.CitizenRepository;
import org.cognizant.usercitizenmanagement.dao.EmergencyRepository;
import org.cognizant.usercitizenmanagement.dto.request.EmergencyReportRequestDTO;
import org.cognizant.usercitizenmanagement.dto.response.EmergencyReportDetailsResponseDTO;
import org.cognizant.usercitizenmanagement.dto.response.EmergencyReportResponseDTO;
import org.cognizant.usercitizenmanagement.entity.Citizen;
import org.cognizant.usercitizenmanagement.entity.EmergencyReport;
import org.cognizant.usercitizenmanagement.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmergencyReportService {

    private final EmergencyRepository reportRepo;
    private final CitizenRepository citizenRepo;

    public EmergencyReportService(EmergencyRepository reportRepo, CitizenRepository citizenRepo) {
        this.reportRepo = reportRepo;
        this.citizenRepo = citizenRepo;
    }

    // --------------------------------------
    // CREATE EMERGENCY REPORT
    // --------------------------------------
    public EmergencyReportResponseDTO createReport(EmergencyReportRequestDTO req) {

        Citizen citizen = citizenRepo.findById(req.getCitizenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Citizen not found with ID: " + req.getCitizenId()));

        EmergencyReport report = new EmergencyReport();
        report.setCitizen(citizen);
        report.setLocation(req.getLocation());
        report.setType(req.getType());
        report.setStatus(req.getStatus());

        EmergencyReport saved = reportRepo.save(report);

        return toResponseDTO(saved);
    }

    // --------------------------------------
    // GET ALL REPORTS
    // --------------------------------------
    public List<EmergencyReportResponseDTO> getAllReports() {
        return reportRepo.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --------------------------------------
    // GET REPORT BY ID
    // --------------------------------------
    public EmergencyReportResponseDTO getReportById(int id) {
        EmergencyReport report = reportRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Report not found with ID: " + id));
        return toResponseDTO(report);
    }

    // --------------------------------------
    // GET REPORT + CITIZEN DETAILS
    // --------------------------------------
    public EmergencyReportDetailsResponseDTO getReportWithCitizen(int id) {

        EmergencyReport report = reportRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Report not found with ID: " + id));

        EmergencyReportDetailsResponseDTO details = new EmergencyReportDetailsResponseDTO();
        details.setReport(toResponseDTO(report));
        details.setCitizenName(report.getCitizen().getName());
        details.setCitizenAddress(report.getCitizen().getAddress());

        return details;
    }

    // --------------------------------------
    // DELETE REPORT
    // --------------------------------------
    public String deleteReport(int id) {

        EmergencyReport report = reportRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Report not found with ID: " + id));

        reportRepo.delete(report);

        return "Emergency Report deleted successfully with ID: " + id;
    }

    // --------------------------------------
    // ENTITY → RESPONSE DTO
    // --------------------------------------
    private EmergencyReportResponseDTO toResponseDTO(EmergencyReport entity) {

        EmergencyReportResponseDTO dto = new EmergencyReportResponseDTO();

        dto.setReportId(entity.getReportId());
        dto.setCitizenId(entity.getCitizen().getCitizenId());
        dto.setLocation(entity.getLocation());
        dto.setType(entity.getType());
        dto.setStatus(entity.getStatus());
        dto.setDate(entity.getDate());

        return dto;
    }
}