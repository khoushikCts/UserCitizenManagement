package org.cognizant.usercitizenmanagement.controller;

import jakarta.validation.Valid;
import org.cognizant.usercitizenmanagement.dto.request.CitizenRequestDTO;
import org.cognizant.usercitizenmanagement.entity.Citizen;
import org.cognizant.usercitizenmanagement.service.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @PostMapping("/createCitizen")
    public Citizen createCitizen(@Valid @RequestBody CitizenRequestDTO requestDTO) {
        return citizenService.createCitizenWithUser(requestDTO);
    }

    @GetMapping("/getCitizenById/{id}")
    public Citizen getCitizenById(@PathVariable int id) {
        return citizenService.getCitizenById(id);
    }

    @GetMapping("/getAllCitizens")
    public List<Citizen> getAllCitizens() {
        return citizenService.getAllCitizens();
    }

    @PutMapping("/update/{id}")
    public Citizen updateCitizen(@PathVariable int id, @Valid @RequestBody CitizenRequestDTO requestDTO) {
        Citizen existing = citizenService.getCitizenById(id);
        if (existing != null) {
            existing.setDob(requestDTO.getDob());
            existing.setGender(requestDTO.getGender());
            existing.setAddress(requestDTO.getAddress());
            existing.setContactInfo(requestDTO.getPhone()); // Keep synced
            return citizenService.updateCitizen(id, existing);
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCitizen(@PathVariable int id) {
        citizenService.deleteCitizen(id);
    }
}