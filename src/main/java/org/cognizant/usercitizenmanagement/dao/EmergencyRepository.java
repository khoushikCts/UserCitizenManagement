package org.cognizant.usercitizenmanagement.dao;

import org.cognizant.usercitizenmanagement.entity.EmergencyReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRepository extends JpaRepository<EmergencyReport, Integer> {
}