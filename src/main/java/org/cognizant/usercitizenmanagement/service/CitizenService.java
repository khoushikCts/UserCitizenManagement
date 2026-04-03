package org.cognizant.usercitizenmanagement.service;

import org.cognizant.usercitizenmanagement.Enum.CitizenStatus;
import org.cognizant.usercitizenmanagement.Enum.Role;
import org.cognizant.usercitizenmanagement.Enum.UserStatus;
import org.cognizant.usercitizenmanagement.dao.CitizenRepository;
import org.cognizant.usercitizenmanagement.dao.UserRepository;
import org.cognizant.usercitizenmanagement.dto.request.CitizenRequestDTO;
import org.cognizant.usercitizenmanagement.entity.Citizen;
import org.cognizant.usercitizenmanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Citizen createCitizenWithUser(CitizenRequestDTO requestDTO) {
        // STEP 1: Create User Account with Default Status
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(requestDTO.getPassword()));
        user.setPhone(requestDTO.getPhone()); // Using phone from DTO
        user.setRole(Role.CITIZEN);
        user.setStatus(UserStatus.INACTIVE); // Default hardcoded

        User savedUser = userRepository.save(user);

        // STEP 2: Create Citizen Profile linked to User
        Citizen citizen = new Citizen();
        citizen.setUser(savedUser);
        citizen.setName(requestDTO.getName());
        citizen.setDob(requestDTO.getDob());
        citizen.setGender(requestDTO.getGender());
        citizen.setAddress(requestDTO.getAddress());
        citizen.setContactInfo(requestDTO.getPhone()); // Mapping same phone here
        citizen.setStatus(CitizenStatus.PENDING);    // Default hardcoded

        return citizenRepository.save(citizen);
    }

    public Citizen getCitizenById(int id) {
        return citizenRepository.findById(id).orElse(null);
    }

    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }

    public Citizen updateCitizen(int id, Citizen citizenDetails) {
        Citizen existing = citizenRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setAddress(citizenDetails.getAddress());
            existing.setContactInfo(citizenDetails.getContactInfo());
            existing.setStatus(citizenDetails.getStatus());
            return citizenRepository.save(existing);
        }
        return null;
    }

    public void deleteCitizen(int id) {
        citizenRepository.deleteById(id);
    }
}