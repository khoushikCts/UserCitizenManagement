package org.cognizant.usercitizenmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cognizant.usercitizenmanagement.Enum.DocType;
import org.cognizant.usercitizenmanagement.Enum.VerificationStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenDocumentResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer documentId;
    private DocType docType;
    private String fileURI;
    private VerificationStatus verificationStatus;
    private LocalDateTime uploadedDate;
}