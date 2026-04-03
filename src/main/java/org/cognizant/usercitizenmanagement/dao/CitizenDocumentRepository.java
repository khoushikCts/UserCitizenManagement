package org.cognizant.usercitizenmanagement.dao;

import org.cognizant.usercitizenmanagement.entity.CitizenDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenDocumentRepository extends JpaRepository<CitizenDocument, Integer> {
}
