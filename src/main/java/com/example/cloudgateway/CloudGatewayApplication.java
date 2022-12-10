package com.example.cloudgateway;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.cloudgateway.entity.UserEntity;
import com.example.cloudgateway.repository.UserRepository;


@SpringBootApplication
@EnableEurekaClient
public class CloudGatewayApplication {
	
	@Autowired
    private UserRepository repository;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	

    @PostConstruct
    public void initUsers() {
        List<UserEntity> users = Stream.of(
                new UserEntity(101, "user1", "pwd1", "user1@gmail.com"),
                new UserEntity(102, "user2", "pwd2", "user2@gmail.com"),
                new UserEntity(103, "user3", "pwd3", "user3@gmail.com")
        ).collect(Collectors.toList());
        repository.saveAll(users);
    }

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}
}
