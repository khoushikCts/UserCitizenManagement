package org.cognizant.usercitizenmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RegisterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterServiceApplication.class, args);
    }

}
