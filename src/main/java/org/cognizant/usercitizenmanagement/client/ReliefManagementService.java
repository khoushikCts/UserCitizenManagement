package org.cognizant.usercitizenmanagement.client;

import org.cognizant.usercitizenmanagement.entity.Citizen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// "name" must match the spring.application.name of the target microservice in Eureka
@FeignClient(name = "RELIEFMANAGEMENT")
public interface ReliefManagementService {

    @GetMapping("/api/Distributions/getDistributionById/{id}")
    Citizen getCitizenById(@PathVariable("id") int id);

}