package org.cognizant.usercitizenmanagement.controller;

import org.cognizant.usercitizenmanagement.dto.request.EmergencyReportRequestDTO;
import org.cognizant.usercitizenmanagement.dto.response.EmergencyReportDetailsResponseDTO;
import org.cognizant.usercitizenmanagement.dto.response.EmergencyReportResponseDTO;
import org.cognizant.usercitizenmanagement.service.EmergencyReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class EmergencyReportController {

    private final EmergencyReportService service;

    public EmergencyReportController(EmergencyReportService service) {
        this.service = service;
    }

    // CREATE REPORT
    @PostMapping
    public EmergencyReportResponseDTO createReport(@RequestBody EmergencyReportRequestDTO requestDTO) {
        return service.createReport(requestDTO);
    }

    // GET ALL REPORTS
    @GetMapping
    public List<EmergencyReportResponseDTO> getAllReports() {
        return service.getAllReports();
    }

    // GET REPORT BY ID
    @GetMapping("/{id}")
    public EmergencyReportResponseDTO getReportById(@PathVariable int id) {
        return service.getReportById(id);
    }

    // GET REPORT WITH CITIZEN DETAILS
    @GetMapping("/{id}/details")
    public EmergencyReportDetailsResponseDTO getReportWithCitizen(@PathVariable int id) {
        return service.getReportWithCitizen(id);
    }

    @DeleteMapping("/{id}")
    public String deleteReport(@PathVariable int id) {
        return service.deleteReport(id);
    }

}