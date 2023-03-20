package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.ttms.beans.ResponseToken;
import ca.ttms.beans.Token;
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
	
	@Autowired
	private AuthenticationService service;

	@Test
	//Register a user and checks that there is a token
	void Register_SingleUser_CheckToken() {
		
		//Arrange
		int expectedTokenLength = 150;
		int resultTokenLength;
		
		UserRegisterDetails inputUser = UserRegisterDetails.builder()
				.firstname("Akshat")
				.lastname("Amin")
				.email("aa@gmail.com")
				.username("aa")
				.password("1234")
				.build();
		
		//Act
		ResponseToken resultToken = service.registerUser(inputUser);
		resultTokenLength = resultToken.getToken().length();
		
		assertTrue(resultTokenLength > expectedTokenLength, "Token should be generated: " + resultToken );
	}
	
	//Register a user and checks that there is a token and user
	@Test
	void Register_MultipleUsers_CompareToken() {
		
		//Arrange
		String resultUsername, expectedUsername;
		Token resultToken;
		ResponseToken expectedToken;
		
		UserRegisterDetails inputUser = UserRegisterDetails.builder()
				.firstname("Hamza")
				.lastname("Atcha")
				.email("ha@gmail.com")
				.username("ha")
				.password("2345")
				.build();
		
		expectedUsername = inputUser.getUsername();
		
		//Act
		expectedToken = service.registerUser(inputUser);
		Optional<User> respnoseUser = userRepo.findByUsername(inputUser.getUsername());
		User resultUser = respnoseUser.orElse(null);
		resultUsername = resultUser.getUsername();
		
		List<Token> respnoseToken = tokenRepo.findAllValidTokenByUser(resultUser.getId());
		resultToken = respnoseToken.get(0);
		
		//Assert
		assertEquals(resultUsername, expectedUsername);
		assertEquals(resultToken.getToken(),expectedToken.getToken());
	}
	
	@Test
	//Register a null user and checks if it returns null
	void Register_NullUser_CheckNullPointer() {
		
		//Arrange
		UserRegisterDetails inputUser = null;
		ResponseToken expectedToken = null;
		
		//Act
		ResponseToken resultToken = service.registerUser(inputUser);
		
		//Assert
		assertEquals(expectedToken, resultToken);
	}

	@Test
	//Authenticate a user and checks that there is a token and it's valid
	void Authenticate_SingleUser_CompareTokenFromReturnToRepo() {
		
		//Arrange
		ResponseToken resultToken, expectedToken;
		expectedToken = new ResponseToken();
		
		UserRegisterDetails inputUser = UserRegisterDetails.builder()
				.firstname("Gargi")
				.lastname("Matoo")
				.email("gm@gmail.com")
				.username("gm")
				.password("3456")
				.build();
		
		UserAuthenticationDetails inputAuth = UserAuthenticationDetails.builder()
				.username(inputUser.getUsername())
				.password(inputUser.getPassword())
				.build();
		
		//Act
		service.registerUser(inputUser);
		resultToken = service.authenticateUser(inputAuth);
		Optional<User> respnoseUser = userRepo.findByUsername(inputUser.getUsername());
		User resultUser = respnoseUser.orElse(null);
		
		List<Token> respnoseToken = tokenRepo.findAllValidTokenByUser(resultUser.getId());
		expectedToken.setToken(respnoseToken.get(1).getToken());
		
		//Assert
		assertEquals(resultToken.getToken(),expectedToken.getToken());
	}
	
	@Test
	//Authenticate a user and checks if token is valid
	void Authenticate_SingleUser_CheckToken() {
		//Arrange
		int expectedTokenLength = 150;
		int resultLength;
		UserRegisterDetails inputUser = UserRegisterDetails.builder()
				.firstname("Zunaira")
				.lastname("Malik")
				.email("zm@gmail.com")
				.username("zm")
				.password("4567")
				.build();
		
		//Act
		service.registerUser(inputUser);
		UserAuthenticationDetails inputAuth = UserAuthenticationDetails.builder()
				.username(inputUser.getUsername())
				.password(inputUser.getPassword())
				.build();
		
		ResponseToken resultToken = service.authenticateUser(inputAuth);
		resultLength = resultToken.getToken().length();
		
		//Assert
		assertTrue(resultLength > expectedTokenLength, "Token not recieved");
	}
	
    @Test
    //Tests no existent user
    void TokenRepo_User_NonexistentUser() {
      Optional<User> foundUser = userRepo.findByUsername("nonexistent");

      assertFalse(foundUser.isPresent());
    }
}
