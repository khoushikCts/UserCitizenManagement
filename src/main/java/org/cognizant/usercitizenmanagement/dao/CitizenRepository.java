package org.cognizant.usercitizenmanagement.dao;

import org.cognizant.usercitizenmanagement.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
    // Find citizens by their verification status (Pending, Verified, etc.)
    List<Citizen> findByStatus(String status);
}