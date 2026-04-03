package org.cognizant.usercitizenmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cognizant.usercitizenmanagement.Enum.CitizenStatus;
import org.cognizant.usercitizenmanagement.Enum.Gender;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer citizenId;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String address;
    private String contactInfo;
    private CitizenStatus status;
}