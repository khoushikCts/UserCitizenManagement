package org.cognizant.usercitizenmanagement.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.cognizant.usercitizenmanagement.dto.request.CitizenDocumentRequestDTO;
import org.cognizant.usercitizenmanagement.entity.CitizenDocument;
import org.cognizant.usercitizenmanagement.service.CitizenDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
@Validated  // ✅ REQUIRED FOR PATH VARIABLE VALIDATION
public class CitizenDocumentController {

    private final CitizenDocumentService documentService;

    public CitizenDocumentController(CitizenDocumentService documentService) {
        this.documentService = documentService;
    }

    // ✅ UPLOAD DOCUMENT
    @PostMapping("/upload")
    public ResponseEntity<CitizenDocument> uploadDocument(
            @Valid @RequestBody CitizenDocumentRequestDTO requestDTO) {

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

    // ✅ DELETE (ID MUST BE POSITIVE)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocument(
            @Positive(message = "Document ID must be greater than zero")
            @PathVariable int id) {

        documentService.deleteDocument(id);
        return ResponseEntity.ok("Document deleted successfully");
    }
}