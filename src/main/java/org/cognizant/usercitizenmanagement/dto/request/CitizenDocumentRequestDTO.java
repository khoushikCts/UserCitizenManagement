package org.cognizant.usercitizenmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.cognizant.usercitizenmanagement.Enum.DocType;
import org.cognizant.usercitizenmanagement.Enum.VerificationStatus;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * MultipartFile to hold the actual binary data from the request.
     * Parameter name must match form-data key in Postman: "fileURI"
     */
    @NotNull(message = "File must not be null")
    private MultipartFile fileURI;

    @NotNull(message = "Verification status must not be null")
    private VerificationStatus verificationStatus;

    // Explicit getters & setters

    public Integer getCitizenId() { return citizenId; }
    public void setCitizenId(Integer citizenId) { this.citizenId = citizenId; }

    public DocType getDocType() { return docType; }
    public void setDocType(DocType docType) { this.docType = docType; }

    public MultipartFile getFileURI() { return fileURI; }
    public void setFileURI(MultipartFile fileURI) { this.fileURI = fileURI; }

    public VerificationStatus getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}