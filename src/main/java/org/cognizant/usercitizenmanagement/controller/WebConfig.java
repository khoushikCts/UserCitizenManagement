package org.cognizant.usercitizenmanagement.controller;

import org.cognizant.usercitizenmanagement.controller.AuditInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuditInterceptor auditInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // This line connects the Interceptor to all your API paths
        registry.addInterceptor(auditInterceptor)
                .addPathPatterns("/api/**")           // Covers /api/users, /api/citizens, etc.
                .excludePathPatterns("/api/logs/**")  // Prevent the log viewer from logging itself
                .excludePathPatterns("/actuator/**"); // Ignore Spring Boot management endpoints
    }
}