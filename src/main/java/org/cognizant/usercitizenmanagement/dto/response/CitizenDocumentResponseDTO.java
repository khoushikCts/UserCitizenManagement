package org.cognizant.usercitizenmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    
    private Integer citizenId;
    
    private DocType docType;

    // Store original filename for identification
    private String fileName;

    // Instead of sending the actual bytes, we send a link
    // that the frontend can use to download the file
    private String downloadUrl;

    private VerificationStatus verificationStatus;
    
    private LocalDateTime uploadedDate;
    
    // ✅ IMPORTANT: Exclude the actual file content from JSON serialization
    // to prevent sending huge binary data in API responses
    @JsonIgnore
    private byte[] fileContent;
}