package com.example.cloudgateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cloudgateway.entity.AuthRequestEntity;
import com.example.cloudgateway.service.JwtUtil;
import com.example.cloudgateway.service.UserService;


//import com.example.SecurityPOC.security.JwtTokenHelper;

@RestController
@RequestMapping("/auth")
public class JwtController {
	
	@Autowired
    private JwtUtil jwtUtil;
	
//	@Autowired
//    private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;

	@PostMapping("/generate")
	public ResponseEntity<Map<String, String>> generate(@RequestBody AuthRequestEntity authRequest) throws Exception {
		System.out.println(authRequest);
		try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
//            );
			userService.authenticate(authRequest.getUserName(), authRequest.getPassword());
        } catch (Exception ex) {
        	Map<String, String> res = new HashMap<>();
        	res.put("status", ""+HttpStatus.UNAUTHORIZED.value());
        	res.put("error", "Server Error");
        	res.put("message", ex.getMessage());
        	return new ResponseEntity<Map<String,String>>(res, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Map<String,String>>(jwtUtil.generateToken(authRequest.getUserName()), HttpStatus.OK);
	}
	
	@GetMapping("/public")
	public ResponseEntity<String> home () {
		return new ResponseEntity<>("HOME", HttpStatus.OK);
	}
//	@PostMapping("/register")
//	public ResponseEntity<String> register(@RequestBody String userName) {
//		// Persist user to some persistent storage
////		System.out.println("Info saved...");
//
//		return new ResponseEntity<String>("Registered", HttpStatus.OK);
//	}
}
