package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.ttms.beans.ResponseToken;
import ca.ttms.beans.UserRegisterDetails;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;

@SpringBootTest
class TestAuthenticationService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private TokenRepo tokenRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AuthenticationService service;

	@Test
	//Description: Register a user and checks that there is a token
	void testPassRegisterUser1() {
		
		UserRegisterDetails inputUser = UserRegisterDetails.builder()
				.firstname("Akshat")
				.lastname("Amin")
				.email("aa@gmail.com")
				.username("aa")
				.password("1234")
				.build();
		
		ResponseToken resultToken = service.registerUser(inputUser);
		
		
		assertTrue(resultToken.getToken().length() > 150, "Token should be generated: " + resultToken );
	}
	
	@Test
	//Description: Register a user and checks that there is a token and user
	void testPassRegisterUser2() {
		
		UserRegisterDetails inputUser = UserRegisterDetails.builder()
				.firstname("Hamza")
				.lastname("Atcha")
				.email("ha@gmail.com")
				.username("ha")
				.password("2345")
				.build();
		
		ResponseToken expectedToken = service.registerUser(inputUser);
		var respnoseUser = userRepo.findByUsername(inputUser.getUsername());
		var resultUser = respnoseUser.orElse(null);
		
		var respnoseToken = tokenRepo.findAllValidTokenByUser(resultUser.getId());
		var resultToken = respnoseToken.get(0);
		
		assertEquals(resultUser.getUsername(),inputUser.getUsername());
		assertEquals(resultToken.getToken(),expectedToken.getToken());
	}
	
	@Test
	//Description: Register a user and checks that there is a token and user
	void testFailRegisterUser() {
		
		UserRegisterDetails inputUser = null;
		
		ResponseToken expectedToken = service.registerUser(inputUser);
		
	}

}
