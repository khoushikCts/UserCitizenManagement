package org.cognizant.usercitizenmanagement.dao;

import org.cognizant.usercitizenmanagement.entity.CitizenDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenDocumentRepository extends JpaRepository<CitizenDocument, Integer> {

    // ✅ FIND ALL DOCUMENTS BELONGING TO A SPECIFIC CITIZEN
    List<CitizenDocument> findByCitizen_CitizenId(Integer citizenId);
}