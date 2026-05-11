package org.cognizant.usercitizenmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cognizant.usercitizenmanagement.Enum.CitizenStatus;
import org.cognizant.usercitizenmanagement.Enum.Gender;
import org.cognizant.usercitizenmanagement.Enum.Role;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String email;
    private String name;
    private Role role;
    private CitizenStatus verificationStatus;
    private Integer citizenId;
    private String phone;
    private String address;
    private Gender gender;
    private LocalDate dob;
}
