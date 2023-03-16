package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.ttms.beans.ResponseToken;
import ca.ttms.beans.User;
import ca.ttms.beans.UserAuthenticationDetails;
import ca.ttms.beans.UserRegisterDetails;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;

@SpringBootTest
class TestAuthenticationService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private TokenRepo tokenRepo;
	
	/**
	 * @Autowired
	 * private PasswordEncoder passwordEncoder;
	 */
	
	/**
	 * @Autowired
	 * private JWTService jwtService;
	 */
	
	@Autowired
	private AuthenticationService service;

	/**
	 * Register a user and checks that there is a token
	 */
	@Test
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
	
	/**
	 * Register a user and checks that there is a token and user
	 */
	@Test
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
	
	/**
	 * Register a null user and checks if it returns null
	 */
	@Test
	void testFailRegisterUser() {
		
		UserRegisterDetails inputUser = null;
		ResponseToken expectedToken = null;
		
		ResponseToken resultToken = service.registerUser(inputUser);
		assertEquals(expectedToken, resultToken);
	}

	
	/**
	 * Authenticate a user and checks that there is a token and it's valid
	 */
	@Test
	void testPassAuthenticateUser1() {
		
		UserRegisterDetails inputUser = UserRegisterDetails.builder()
				.firstname("Gargi")
				.lastname("Matoo")
				.email("gm@gmail.com")
				.username("gm")
				.password("3456")
				.build();
		
		ResponseToken inputToken = service.registerUser(inputUser);
		
		UserAuthenticationDetails inputAuth = UserAuthenticationDetails.builder()
				.username(inputUser.getUsername())
				.password(inputUser.getPassword())
				.build();
	
		ResponseToken resultToken = service.authenticateUser(inputAuth);
		var respnoseUser = userRepo.findByUsername(inputUser.getUsername());
		var resultUser = respnoseUser.orElse(null);
		
		var respnoseToken = tokenRepo.findAllValidTokenByUser(resultUser.getId());
		var expectedToken = respnoseToken.get(1);
		
		assertEquals(resultToken.getToken(),expectedToken.getToken());
	}
	
	/**
	 * Authenticate a user and checks if token is valid
	 */
	@Test
	void testPassAuthenticateUser2() {
		
		UserRegisterDetails inputUser = UserRegisterDetails.builder()
				.firstname("Zunaira")
				.lastname("Malik")
				.email("zm@gmail.com")
				.username("zm")
				.password("4567")
				.build();
		
		ResponseToken inputToken = service.registerUser(inputUser);
		
		UserAuthenticationDetails inputAuth = UserAuthenticationDetails.builder()
				.username(inputUser.getUsername())
				.password(inputUser.getPassword())
				.build();
	
		ResponseToken resultToken = service.authenticateUser(inputAuth);
	
		
		assertTrue(resultToken.getToken().length() > 150, "Token not recieved");
	}
	
	/**
	 * Tests no existent user
	 */
    @Test
    void testFailAuthenticateUser() {
      Optional<User> foundUser = userRepo.findByUsername("nonexistent");

      assertFalse(foundUser.isPresent());
    }
}
