package com.bookStore.utility;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.bookStore.dto.UserLoginDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JWTUtility {
	String secrateKey = "Vibha@123";
	
	
   public String getTokenGenrated(String email, String password) {
	   HashMap<String, Object> map = new HashMap<>();
	   map.put("email", email);
	   map.put("password", password);
		String key = Jwts.builder().setClaims(map).signWith(SignatureAlgorithm.HS256, secrateKey).compact();

//	   String key = Jwts.builder().setClaims(map).signWith(SignatureAlgorithm.HS256, secrateKey).compact();
	   return key;
	   		
   }
   public UserLoginDto decodeToken(String token) {
	   try {
		   Jwts.parser().setSigningKey(secrateKey).parseClaimsJws(token);
		   
	   }catch(Exception e){
		   
	   }
	   Claims decodedToken = Jwts.parser().setSigningKey(secrateKey ).parseClaimsJws(token).getBody();
	   UserLoginDto user = new UserLoginDto();
	   user.setEmail((String)(decodedToken.get("email")));
	   user.setPassword((String)(decodedToken.get("password")));
	   return user;
	   
	   
   }
}
