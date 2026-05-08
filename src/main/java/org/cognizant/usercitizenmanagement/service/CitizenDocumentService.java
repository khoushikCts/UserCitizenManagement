package org.cognizant.usercitizenmanagement.service;

import org.cognizant.usercitizenmanagement.dao.CitizenDocumentRepository;
import org.cognizant.usercitizenmanagement.dao.CitizenRepository;
import org.cognizant.usercitizenmanagement.dto.request.CitizenDocumentRequestDTO;
import org.cognizant.usercitizenmanagement.entity.Citizen;
import org.cognizant.usercitizenmanagement.entity.CitizenDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.List;

@Service
public class CitizenDocumentService {

    private final CitizenDocumentRepository documentRepository;
    private final CitizenRepository citizenRepository;

    public CitizenDocumentService(CitizenDocumentRepository documentRepository,
                                  CitizenRepository citizenRepository) {
        this.documentRepository = documentRepository;
        this.citizenRepository = citizenRepository;
    }

    // ✅ UPLOAD DOCUMENT
    public CitizenDocument uploadDocument(CitizenDocumentRequestDTO requestDTO) {

        Citizen citizen = citizenRepository.findById(requestDTO.getCitizenId())
                .orElseThrow(() ->
                        new RuntimeException("Citizen not found with ID: "
                                + requestDTO.getCitizenId()));

        CitizenDocument document = new CitizenDocument();
        document.setCitizen(citizen);
        document.setDocType(requestDTO.getDocType());
        document.setVerificationStatus(requestDTO.getVerificationStatus());

        try {
            // 3. Extract the binary data (BLOB) from the MultipartFile
            // requestDTO.getFileURI() returns the MultipartFile from form-data
            byte[] binaryData = requestDTO.getFileURI().getBytes();

            // Validate file size (max 10MB as per application.properties)
            if (binaryData.length > 10 * 1024 * 1024) {
                throw new RuntimeException("File size exceeds maximum limit of 10MB");
            }

            // 4. Set the binary data to the Entity field marked with @Lob
            document.setFileContent(binaryData);

            // 5. Save the original filename for easier downloading/identification
            String originalFileName = requestDTO.getFileURI().getOriginalFilename();
            if (originalFileName == null || originalFileName.isBlank()) {
                originalFileName = "uploaded-file";
            }

            document.setFileName(originalFileName);
            document.setFileURI(originalFileName);

        } catch (IOException e) {
            // Handle potential issues reading the file stream
            throw new RuntimeException("Failed to read file content for upload", e);
        }

        // 6. Persist the record (including the BLOB) to MySQL
        return documentRepository.save(document);
    }

    // ✅ GET DOCUMENTS BY USER ID
    public List<CitizenDocument> getDocumentsByUserId(Integer userId) {
        return documentRepository.findByCitizen_User_UserId(userId);
    }

    // ✅ GET BY ID
    public CitizenDocument getDocumentById(int docId) {
        return documentRepository.findById(docId)
                .orElseThrow(() ->
                        new RuntimeException("CitizenDocument not found with ID: " + docId));
    }

    // ✅ DELETE
    public void deleteDocument(int docId) {
        documentRepository.deleteById(docId);
    }

    // ✅ GET DOCUMENT BYTES FOR DOWNLOAD
    public byte[] getDocumentBytes(int docId) {
        CitizenDocument document = documentRepository.findById(docId)
                .orElseThrow(() ->
                        new RuntimeException("CitizenDocument not found with ID: " + docId));
        return document.getFileContent();
    }
}