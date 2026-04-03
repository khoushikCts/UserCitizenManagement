package org.cognizant.usercitizenmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cognizant.usercitizenmanagement.Enum.DocType;
import org.cognizant.usercitizenmanagement.Enum.VerificationStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "CitizenDocument")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentID")
    private Integer documentId;

    @ManyToOne
    @JoinColumn(name = "CitizenID", nullable = false)
    @JsonIgnoreProperties({"documents", "user"})
    private Citizen citizen;

    @Enumerated(EnumType.STRING)
    @Column(name = "DocType", nullable = false)
    private DocType docType;

    @Column(name = "FileURI", nullable = false)
    private String fileURI;

    @Enumerated(EnumType.STRING)
    @Column(name = "VerificationStatus", nullable = false)
    private VerificationStatus verificationStatus;

    @CreationTimestamp
    @Column(name = "UploadedDate", updatable = false)
    private LocalDateTime uploadedDate;
}
