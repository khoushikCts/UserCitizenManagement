package org.cognizant.usercitizenmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UserCitizenManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCitizenManagementApplication.class, args);
    }

}
