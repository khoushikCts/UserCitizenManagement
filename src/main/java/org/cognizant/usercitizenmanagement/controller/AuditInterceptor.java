package org.cognizant.usercitizenmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cognizant.usercitizenmanagement.dao.UserRepository;
import org.cognizant.usercitizenmanagement.entity.AuditLog;
import org.cognizant.usercitizenmanagement.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class AuditInterceptor implements HandlerInterceptor {

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Only log successful requests (optional: check if response.getStatus() < 400)

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // 1. Identify the 'Who' - This will now correctly find the Manager/User
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser"))
                ? auth.getName() : "GUEST";

        // 2. Identify the 'What'
        String details = "Unknown Action";
        if (uri.contains("/api/users")) details = "User Management: " + method + " action";
        else if (uri.contains("/api/citizens")) details = "Citizen Record: " + method + " action";
        else if (uri.contains("/api/documents")) details = "Document Store: " + method + " action";
        else if (uri.contains("/api/reports")) details = "Emergency Reporting: " + method + " action";

        // 3. Create the log entry
        AuditLog log = new AuditLog();
        log.setAction(method);
        log.setResource(uri);
        log.setIpAddress(request.getRemoteAddr());
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(details + " by " + email);

        // Link to User entity
        userRepository.findByEmail(email).ifPresent(log::setUser);

        auditLogService.logAction(log);
    }
}