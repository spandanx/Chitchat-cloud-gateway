package com.example.cloudgateway.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cloudgateway.entity.UserEntity;
import com.example.cloudgateway.repository.UserRepository;


@Service
public class UserService{ 
    @Autowired
    private UserRepository repository;

    public UserEntity loadUserByUsername(String username){
    	return repository.findByUserName(username);
    }
    
    public void authenticate(String username, String password) throws Exception {
//    	System.out.println("Called authenticate()");
    	UserEntity user = loadUserByUsername(username);
//    	System.out.println("From database");
//    	System.out.println(user);
//    	System.out.println("user.getUserName(): "+user.getUserName());
//    	System.out.println("user.getPassword(): "+user.getPassword());
    	if (user == null) {
    		throw new Exception("Username not found!");
    	}
    	if (!password.equals(user.getPassword())) {
    		throw new Exception("Invalid password!");
    	}
    	//If no exception, then the user is authenticated.
    }
    
}