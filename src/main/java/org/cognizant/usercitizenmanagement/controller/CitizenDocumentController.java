package org.cognizant.usercitizenmanagement.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.cognizant.usercitizenmanagement.dto.request.CitizenDocumentRequestDTO;
import org.cognizant.usercitizenmanagement.entity.CitizenDocument;
import org.cognizant.usercitizenmanagement.service.CitizenDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@Validated  // ✅ REQUIRED FOR PATH VARIABLE VALIDATION
public class CitizenDocumentController {

    private final CitizenDocumentService documentService;

    public CitizenDocumentController(CitizenDocumentService documentService) {
        this.documentService = documentService;
    }

    // ✅ UPLOAD DOCUMENT
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CitizenDocument> uploadDocument(
            @Valid @ModelAttribute CitizenDocumentRequestDTO requestDTO) {
        // The requestDTO now contains the MultipartFile instead of a String URI
        CitizenDocument savedDoc = documentService.uploadDocument(requestDTO);
        return new ResponseEntity<>(savedDoc, HttpStatus.CREATED);
    }

    // ✅ GET BY ID (ID MUST BE POSITIVE)
    @GetMapping("/getDocById/{id}")
    public ResponseEntity<CitizenDocument> getDocumentById(
            @Positive(message = "Document ID must be greater than zero")
            @PathVariable int id) {

        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    // ✅ GET DOCUMENTS BY CITIZEN ID
    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<CitizenDocument>> getDocumentsByCitizenId(
            @Positive(message = "Citizen ID must be greater than zero")
            @PathVariable Integer citizenId) {

        List<CitizenDocument> documents = documentService.getDocumentsByCitizenId(citizenId);
        return ResponseEntity.ok(documents);
    }

    // ✅ DELETE (ID MUST BE POSITIVE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocument(
            @Positive(message = "Document ID must be greater than zero")
            @PathVariable int id) {

        documentService.deleteDocument(id);
        return ResponseEntity.ok("Document deleted successfully");
    }

    // ✅ DOWNLOAD DOCUMENT (RETRIEVE BINARY DATA)
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(
            @Positive(message = "Document ID must be greater than zero")
            @PathVariable int id) {

        CitizenDocument document = documentService.getDocumentById(id);
        byte[] fileContent = documentService.getDocumentBytes(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileContent.length))
                .body(fileContent);
    }
}