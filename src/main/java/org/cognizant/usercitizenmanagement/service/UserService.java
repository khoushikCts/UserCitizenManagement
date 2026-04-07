package org.cognizant.usercitizenmanagement.service;

import org.cognizant.usercitizenmanagement.dao.UserRepository;
import org.cognizant.usercitizenmanagement.entity.User;
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

            // 2. Manually validate the raw password against the stored hash
            // passwordEncoder.matches(rawPassword, encodedPassword)
            if (passwordEncoder.matches(user.getPasswordHash(), existingUser.getPasswordHash())) {

                // 3. If valid, generate the token using the stored user's details
                return jwtService.generateToken(existingUser.getEmail(), existingUser.getRole().name());
            }
        }

        return "fail";
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        // Ensure we re-hash if the password was changed, or handle partially
        return userRepository.save(user);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}