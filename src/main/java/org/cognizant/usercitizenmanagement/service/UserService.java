package org.cognizant.usercitizenmanagement.service;

import org.cognizant.usercitizenmanagement.dao.UserRepository;
import org.cognizant.usercitizenmanagement.dao.CitizenRepository;
import org.cognizant.usercitizenmanagement.entity.User;
import org.cognizant.usercitizenmanagement.entity.Citizen;
import org.cognizant.usercitizenmanagement.Enum.Role;
import org.cognizant.usercitizenmanagement.Enum.CitizenStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    public User createUser(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    public org.cognizant.usercitizenmanagement.dto.response.LoginResponseDTO UserLoginValidation(User user) {
        // 1. Fetch the user from the database by email
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // 2. Verification Check: If user is a CITIZEN, they must be VERIFIED to login
            Citizen citizen = null;
            if (existingUser.getRole() == Role.CITIZEN) {
                citizen = citizenRepository.findByUser(existingUser);
                if (citizen == null || citizen.getStatus() != CitizenStatus.VERIFIED) {
                    return null; // Caller will interpret as verification pending
                }
            }

            // 3. Manually validate the raw password against the stored hash
            if (passwordEncoder.matches(user.getPasswordHash(), existingUser.getPasswordHash())) {

                // 4. If valid, generate the token using the stored user's details
                String token = jwtService.generateToken(existingUser.getEmail(), existingUser.getRole().name());

                // 5. Build response DTO including citizen details when available
                org.cognizant.usercitizenmanagement.dto.response.UserLoginDTO userDto = new org.cognizant.usercitizenmanagement.dto.response.UserLoginDTO();
                userDto.setUserId(existingUser.getUserId());
                userDto.setEmail(existingUser.getEmail());
                userDto.setName(existingUser.getName());
                userDto.setRole(existingUser.getRole());
                userDto.setPhone(existingUser.getPhone());

                if (citizen != null) {
                    userDto.setVerificationStatus(citizen.getStatus());
                    userDto.setCitizenId(citizen.getCitizenId());
                    userDto.setAddress(citizen.getAddress());
                    userDto.setGender(citizen.getGender());
                    userDto.setDob(citizen.getDob());
                }

                org.cognizant.usercitizenmanagement.dto.response.LoginResponseDTO resp = new org.cognizant.usercitizenmanagement.dto.response.LoginResponseDTO();
                resp.setToken(token);
                resp.setUser(userDto);

                return resp;
            }
        }

        return null;
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Integer getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getUserId)
                .orElse(null);
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}