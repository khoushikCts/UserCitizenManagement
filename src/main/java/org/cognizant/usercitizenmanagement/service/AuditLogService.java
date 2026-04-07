    /*package org.cognizant.disastermanagement.service;

import org.cognizant.disastermanagement.entity.AuditLog;
import org.cognizant.disastermanagement.dao.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditRepo;

    public AuditLog logAction(AuditLog log) {
        return auditRepo.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return auditRepo.findAll();
    }
}*/
/*package org.cognizant.disastermanagement.service;

import org.cognizant.disastermanagement.entity.AuditLog;
import org.cognizant.disastermanagement.dao.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditRepo;

    public AuditLog logAction(AuditLog log) {
        return auditRepo.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return auditRepo.findAll();
    }
}*/
package org.cognizant.usercitizenmanagement.service;

import org.cognizant.usercitizenmanagement.dao.AuditLogRepository;
import org.cognizant.usercitizenmanagement.entity.AuditLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditRepo;

    @Autowired
    public AuditLogService(AuditLogRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    /**
     * This method fixes the "cannot find symbol" error in the Controller.
     */
    public AuditLog logAction(AuditLog log) {
        if (log.getTimestamp() == null) {
            log.setTimestamp(LocalDateTime.now());
        }
        return auditRepo.save(log);
    }

    /**
     * Standardized logging method for internal service use.
     */
    public void quickLog(String action, String resourceName, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setResource(resourceName);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        auditRepo.save(log);
    }

    public Page<AuditLog> getAllLogs(Pageable pageable) {
        return auditRepo.findAll(pageable);
    }
}