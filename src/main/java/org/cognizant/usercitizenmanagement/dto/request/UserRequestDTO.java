
package org.cognizant.usercitizenmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.cognizant.usercitizenmanagement.Enum.Role;
import org.cognizant.usercitizenmanagement.Enum.UserStatus;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Name cannot be blank")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Name must contain only alphabets and spaces"
    )
    private String name;

    @NotNull(message = "Role cannot be null")
    private Role role;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must be exactly 10 digits"
    )
    private String phone;

    @NotBlank(message = "Password cannot be blank")
    private String passwordHash;

    @NotNull(message = "Status cannot be null")
    private UserStatus status;

    // Explicit getters & setters (as you requested)

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus status) { this.status = status; }
}