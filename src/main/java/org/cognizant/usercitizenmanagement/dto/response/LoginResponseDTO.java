package org.cognizant.usercitizenmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private UserLoginDTO user;
}
