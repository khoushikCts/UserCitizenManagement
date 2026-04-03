package org.cognizant.usercitizenmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.cognizant.usercitizenmanagement.Enum.DocType;
import org.cognizant.usercitizenmanagement.Enum.VerificationStatus;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CitizenDocumentRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Citizen ID must not be null")
    @Positive(message = "Citizen ID must be greater than zero")
    private Integer citizenId;

    @NotNull(message = "Document type must not be null")
    private DocType docType;

    @NotBlank(message = "File URI must not be blank")
    @Pattern(
            regexp = "^[a-zA-Z0-9_./-]+$",
            message = "File URI contains invalid characters"
    )
    private String fileURI;

    @NotNull(message = "Verification status must not be null")
    private VerificationStatus verificationStatus;

    // Explicit getters & setters (same style as others)

    public Integer getCitizenId() { return citizenId; }
    public void setCitizenId(Integer citizenId) { this.citizenId = citizenId; }

    public DocType getDocType() { return docType; }
    public void setDocType(DocType docType) { this.docType = docType; }

    public String getFileURI() { return fileURI; }
    public void setFileURI(String fileURI) { this.fileURI = fileURI; }

    public VerificationStatus getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}