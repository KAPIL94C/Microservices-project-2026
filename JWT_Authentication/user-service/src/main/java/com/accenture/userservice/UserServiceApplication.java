package com.accenture.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


// Github link : https://github.com/LearnCodeWithDurgesh/auth-app-boot-react/blob/dev/auth-backend/src/main/java/com/substring/auth/app/auth/repositories/UserRepository.java
//Tutorial link : https://www.youtube.com/watch?v=64LIU1Wfmug&list=PL0zysOflRCem2SLBwhDMok05hwLtRTRDr&index=19

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	// to sign in with google http://localhost:8083/oauth2/authorization/google
	
	@Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
