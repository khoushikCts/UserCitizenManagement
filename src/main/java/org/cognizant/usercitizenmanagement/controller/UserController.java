
package org.cognizant.usercitizenmanagement.controller;

import jakarta.validation.Valid;
import org.cognizant.usercitizenmanagement.dto.request.UserRequestDTO;
import org.cognizant.usercitizenmanagement.entity.User;
import org.cognizant.usercitizenmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ CREATE USER WITH VALIDATION
    @PostMapping("/createUser")
    public User createUser(@Valid @RequestBody UserRequestDTO requestDTO) {

        User user = new User();
        user.setName(requestDTO.getName());
        user.setRole(requestDTO.getRole());
        user.setEmail(requestDTO.getEmail());
        user.setPhone(requestDTO.getPhone());
        user.setPasswordHash(requestDTO.getPasswordHash());
        user.setStatus(requestDTO.getStatus());

        return userService.createUser(user);
    }
    @PostMapping("/login")
    public ResponseEntity<?> UserLoginValidation(@RequestBody  User user){
        System.out.println(user.getEmail()+" "+user.getPasswordHash());
        var resp = userService.UserLoginValidation(user);
        if (resp == null) {
            // Could be verification pending or invalid credentials
            // Check if user exists to decide message
            var existingUser = userService.getUserByEmail(user.getEmail());
            if (existingUser != null && existingUser.getRole() == org.cognizant.usercitizenmanagement.Enum.Role.CITIZEN) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Verification Pending");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
        }

        return ResponseEntity.ok(resp);
    }
    @GetMapping("/getByUserId/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getUserIdByEmail")
    public Integer getUserIdByEmail(@RequestParam String email) {
        return userService.getUserIdByEmail(email);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ✅ UPDATE USER WITH VALIDATION
    @PutMapping("/update/{id}")
    public User updateUser(
            @PathVariable int id,
            @Valid @RequestBody UserRequestDTO requestDTO) {

        User user = new User();
        user.setUserId(id);
        user.setName(requestDTO.getName());
        user.setRole(requestDTO.getRole());
        user.setEmail(requestDTO.getEmail());
        user.setPhone(requestDTO.getPhone());
        user.setPasswordHash(requestDTO.getPasswordHash());
        user.setStatus(requestDTO.getStatus());

        return userService.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        System.out.println("user deleted");
    }
}