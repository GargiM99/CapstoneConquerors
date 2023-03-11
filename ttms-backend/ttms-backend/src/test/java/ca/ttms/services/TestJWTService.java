package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.ttms.beans.Roles;
import ca.ttms.beans.User;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * @author Hamza
 * date: 2023/03/08
 * description: Tests the services for JWTokens
 */

class TestJWTService {
	private final JWTService jwtService = new JWTService();;
	private User user1, user2;
	private Map<String, Object> extraClaims1, extraClaims2;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;
	
	@BeforeEach
	void init(){
		user1 = User
				.builder()
		        .firstname("Hamza")
		        .lastname("Atcha")
		        .username("atchah")
		        .email("atchah@sheridancollege.ca")
		        .password(passwordEncoder.encode("1234"))
		        .role(Roles.USER)
		        .build();	
		
		user2 = User
				.builder()
		        .firstname("John")
		        .lastname("Smith")
		        .username("johns")
		        .email("johns@sheridancollege.ca")
		        .password(passwordEncoder.encode("4567"))
		        .role(Roles.USER)
		        .build();
		
		extraClaims1 = new HashMap<String, Object>();
		extraClaims1.put("Role", user1.getRole());
		
		extraClaims2 = new HashMap<String, Object>();
		extraClaims2.put("Firstname", user2.getFirstname());
		extraClaims2.put("Lastname", user2.getFirstname());
	}
	
	@Test
	//Tests if a blank JWT is different depending on the subject 
	void testPassGenerateJwtokenBlank1() {
		String jwtoken1 = jwtService.generateJwtoken(user1);
		String jwtoken2 = jwtService.generateJwtoken(user2);
		
		assertFalse( jwtoken1.equals(jwtoken2), "Token should be unique" );
	}
	
	@Test
	//Tests if a blank JWT can be generated by checking the length
	void testPassGenerateJwtokenBlank2() {
		String jwtoken = jwtService.generateJwtoken(user1);
		
		assertTrue( jwtoken.length() > 150, "Token should be longer than 150" );
	}
	
	@Test
	//Tests if a blank JWT will generate if the user is null
	void testFailGenerateJwtokenBlank() {		 
		assertThrows( NullPointerException.class, () -> {jwtService.generateJwtoken(null);} );
	}
	
	
	@Test
	//Tests if a JWT with claim can be generated by checking the length
	void testPassGenerateJwtoken1() {
		String jwtoken = jwtService.generateJwtoken(extraClaims1,user1);
		
		assertTrue( jwtoken.length() > 150, "Token should be longer than 150" );
	}
	
	@Test
	//Tests if a JWT with claims are unique with different claims but same subject
	void testPassGenerateJwtoken2() {
		String jwtoken1 = jwtService.generateJwtoken(extraClaims1,user1);
		String jwtoken2 = jwtService.generateJwtoken(extraClaims2,user1);
		
		assertFalse( jwtoken1.equals(jwtoken2), "Token should be unique" );
	}
	
	@Test
	//Tests if claims is null be user is still entered
	void testFailGenerateJwtoken() {		 
		assertThrows( IllegalArgumentException.class, () -> {jwtService.generateJwtoken(null, user1);} );
	}
	
	
	@Test
	//Tests if extracting the subject from a JWT
	void testPassExtractSubject1() {
		String jwtoken = jwtService.generateJwtoken(extraClaims1,user1);
		String subject = jwtService.extractSubject(jwtoken);
		
		assertTrue( subject.equals(user1.getUsername()), "Subject should be the same as the username" );
	}
	
	@Test
	//Tests if extracting the subject from a 2 JWT to check if they are different
	void testPassExtractSubject2() {
		String jwtoken1 = jwtService.generateJwtoken(extraClaims1,user1);
		String jwtoken2 = jwtService.generateJwtoken(extraClaims2,user2);
		
		String subject1 = jwtService.extractSubject(jwtoken1);
		String subject2 = jwtService.extractSubject(jwtoken2);
		
		assertFalse( subject1.equals(subject2), "Subject should be different" );
	}
	
	@Test
	//Tests if token is null for extracting claims
	void testFailExtractSubject() {	
		assertThrows( IllegalArgumentException.class, () -> {jwtService.extractSubject(null);} );
	}
	
	
	@Test
	//Tests if token validation works by creating a JWT
	void testPassIsJwtokenValid1() {
		String jwtoken = jwtService.generateJwtoken(extraClaims1,user1);
		
		assertTrue(jwtService.isJwtokenValid(jwtoken, user1), "Token should be valid" );
	}
	
	@Test
	//Tests if token validation works by creating a JWT with different username then subject
	void testPassIsJwtokenValid2() {
		String jwtoken = jwtService.generateJwtoken(extraClaims1,user1);
		
		assertFalse(jwtService.isJwtokenValid(jwtoken, user2), "Token should be invalid" );
	}
	
	@Test
	//Tests if token validation will throw an error if token is expired
	void testFailIsJwtokenValid() {
		String jwtoken = jwtService.generateJwtoken(extraClaims1,user1,-1000L);
		
		assertThrows(ExpiredJwtException.class, () -> {jwtService.isJwtokenValid(jwtoken, user1);});
	}
}
