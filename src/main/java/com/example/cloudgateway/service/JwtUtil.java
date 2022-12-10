package com.example.cloudgateway.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

    private String secret = "secret0_";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Map<String, String> generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private Map<String, String> createToken(Map<String, Object> claims, String subject) {

    	Map<String, String> response = new HashMap<>();
    	
    	Date expiry = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10);
    	
    	String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    	
        response.put("token", token);
        response.put("expiry", expiry.toString());
        
        return response;
    }
    public Claims getClaims(final String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			return body;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
	public String validateToken(String token) {
//		System.out.println("calling validateToken()");
//		final String username = extractUsername(token);
		Claims data = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		String userName = (String) data.get("sub");
//		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//		System.out.println("Called validateToken()");
//		System.out.println("data : "+data);
		return userName;
	}
}