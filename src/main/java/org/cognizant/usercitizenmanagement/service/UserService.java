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

    public String UserLoginValidation(User user) {
        // 1. Fetch the user from the database by email
        Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // 2. Verification Check: If user is a CITIZEN, they must be VERIFIED to login
            if (existingUser.getRole() == Role.CITIZEN) {
                Citizen citizen = citizenRepository.findByUser(existingUser);
                if (citizen == null || citizen.getStatus() != CitizenStatus.VERIFIED) {
                    return "Verification Pending";
                }
            }

            // 3. Manually validate the raw password against the stored hash
            if (passwordEncoder.matches(user.getPasswordHash(), existingUser.getPasswordHash())) {

                // 4. If valid, generate the token using the stored user's details
                return jwtService.generateToken(existingUser.getEmail(), existingUser.getRole().name());
            }
        }

        return "Login Failed";
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
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