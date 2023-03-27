package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.ttms.beans.Address;
import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.Token;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.response.ResponseToken;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;

class TestAuthenticationService {

	@Mock
	private PersonRepo personRepo;

	@Mock
	private UserRepo userRepo;

	@Mock
	private AddressRepo addressRepo;

	@Mock
	private ContactRepo contactRepo;
	
	@Mock 
	private TokenRepo tokenRepo;

	@Mock
	private PasswordEncoder passwordEncoder;
	
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JWTService jwtService;

	@InjectMocks
	private AuthenticationService userService;
	
	private final int DEFAULT_PASSWORD_LENGTH = 12;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	//Creates duplicate user and checks that return value is null
	public void Register_DuplicateUser_CheckAuthDetailsNull() {
		
		// Arrange
		LocalDate inputDate = LocalDate.now();
		Person inputPerson = new Person(null, "Hamza", "Atcha", inputDate, new ArrayList<Address>());
		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", "", new Person());
		Address inputAddress = new Address(null, "1 Main St", "L5Q1E3", "City", "Ontario", "Canada", new ArrayList<Person>());
		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson, inputContact, inputAddress);

		// Arrange Mock
		when(personRepo.countSimilarPeople(inputPerson.getFirstname(),inputPerson.getLastname(), inputContact.getEmail(),
											inputContact.getPrimaryPhoneNumber())).thenReturn(1);
		
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encoded_password");
		when(userRepo.save(Mockito.any(User.class))).thenReturn(new User());
		when(personRepo.save(Mockito.any(Person.class))).thenReturn(new Person());
		when(addressRepo.save(Mockito.any(Address.class))).thenReturn(new Address());
		when(contactRepo.save(Mockito.any(Contact.class))).thenReturn(new Contact());

		// Act
		UserAuthenticationDetails result = userService.registerUser(inputUser);

		// Assert
		assertNull(result);
	}
	
	@Test
	public void Register_InvalidUser_CheckAuthDetailsNull() {
		
		// Arrange
		Person inputPerson = new Person();
		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", "", new Person());
		Address inputAddress = new Address(null, "1 Main St", "L5Q1E3", "City", "Ontario", "Canada", new ArrayList<Person>());
		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson, inputContact, inputAddress);

		// Arrange Mock
		when(personRepo.countSimilarPeople(inputPerson.getFirstname(),inputPerson.getLastname(), inputContact.getEmail(),
											inputContact.getPrimaryPhoneNumber())).thenReturn(0);
		
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encoded_password");
		when(userRepo.save(Mockito.any(User.class))).thenReturn(new User());
		when(personRepo.save(Mockito.any(Person.class))).thenReturn(new Person());
		when(addressRepo.save(Mockito.any(Address.class))).thenReturn(new Address());
		when(contactRepo.save(Mockito.any(Contact.class))).thenReturn(new Contact());

		// Act
		UserAuthenticationDetails result = userService.registerUser(inputUser);

		// Assert
		assertNull(result);
	}
	
	@Test
	public void Register_SingleUser_CheckAuthDetailsNotNull() {
		
		// Arrange
		LocalDate inputDate = LocalDate.now();
		Person inputPerson = new Person(null, "Hamza", "Atcha", inputDate, new ArrayList<Address>());
		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", null, new Person());
		Address inputAddress = new Address(null, "1 Main St", "L5Q1E3", "City", "Ontario", "Canada", new ArrayList<Person>());
		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson, inputContact, inputAddress);

		// Arrange Mock
		when(personRepo.countSimilarPeople(inputPerson.getFirstname(),inputPerson.getLastname(), inputContact.getEmail(),
											inputContact.getPrimaryPhoneNumber())).thenReturn(0);
		
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encoded_password");
		when(userRepo.save(Mockito.any(User.class))).thenReturn(new User());
		when(personRepo.save(Mockito.any(Person.class))).thenReturn(new Person());
		when(addressRepo.save(Mockito.any(Address.class))).thenReturn(new Address());
		when(contactRepo.save(Mockito.any(Contact.class))).thenReturn(new Contact());

		// Act
		UserAuthenticationDetails result = userService.registerUser(inputUser);

		// Assert
		assertNotNull(result);
		assertEquals(result.getPassword().length(), DEFAULT_PASSWORD_LENGTH);
	}
	
	@Test
	public void Generate_Password_CheckPasswordLength() {
		
		// Act
		String resultPassword = userService.generateTempPassword();

		// Assert
		assertNotNull(resultPassword);
		assertEquals(resultPassword.length(), DEFAULT_PASSWORD_LENGTH);
	}
	
	@Test
	public void Generate_Password_CheckUnique() {
		
		// Act
		String resultPassword = userService.generateTempPassword();
		String resultPassword2 = userService.generateTempPassword();

		// Assert
		assertNotEquals(resultPassword, resultPassword2);
	}
	
	@Test
	public void Generate_Username_CompareResultToExpected() {
		
		//Arrange
		String expectedUsername = "atchah";
		String resultUsername = "";
		String inputFirstname = "Hamza";
		String inputLastname = "Atcha";
		
		//Arrange Mock
		when(userRepo.findUserWithSimilarUsernames(Mockito.any(String.class))).thenReturn(null);
		
		//Act
		resultUsername = userService.generateUsername(inputFirstname, inputLastname);
		
		//Assert
		assertEquals(resultUsername, expectedUsername);
	}
	
	@Test
	public void Generate_UsernameWhichIsExisting_CheckNumberOnUsername() {
		
		//Arrange
		String expectedUsername = "atchah1";
		String resultUsername = "";
		String expectedNumbers = "1";
		
		String mockUsername = "atchah";
		ArrayList<String> mockResult = new ArrayList<String>();
		mockResult.add(mockUsername);
		
		String inputFirstname = "Hamza";
		String inputLastname = "Atcha";
		
		//Arrange Mock
		when(userRepo.findUserWithSimilarUsernames(Mockito.any(String.class))).thenReturn(mockResult);
		
		//Act
		resultUsername = userService.generateUsername(inputFirstname, inputLastname);
		
		//Assert
		assertTrue(resultUsername.contains(expectedNumbers));
		assertEquals(resultUsername, expectedUsername);
	}
	
	@Test
	public void Generate_UsernameWithSmallName_CompareResultAndExpected() {
		
		//Arrange
		String expectedUsername = "wexi";
		String resultUsername = "";
		
		String inputFirstname = "Xi";
		String inputLastname = "We";
		
		//Arrange Mock
		when(userRepo.findUserWithSimilarUsernames(Mockito.any(String.class))).thenReturn(null);
		
		//Act
		resultUsername = userService.generateUsername(inputFirstname, inputLastname);
		
		//Assert
		assertEquals(resultUsername, expectedUsername);
	}
	
	@Test
	public void Generate_UsernameWithUsername_CheckSymbolIsRemoved() {
		
		//Arrange
		String expectedUsername = "foxola";
		String resultUsername = "";
		String expectedSymbol = "'";
		
		String inputFirstname = "O'Larry";
		String inputLastname = "Fox";
		
		//Arrange Mock
		when(userRepo.findUserWithSimilarUsernames(Mockito.any(String.class))).thenReturn(null);
		
		//Act
		resultUsername = userService.generateUsername(inputFirstname, inputLastname);
		
		//Assert
		assertFalse(resultUsername.contains(expectedSymbol));
		assertEquals(resultUsername, expectedUsername, resultUsername);
	}
	
	@Test
	public void Authenticate_User_CheckJWToken() {
		
		//Arrange
		String inputUsername = "atchah";
		String inputPassword = "password123";
		ResponseToken resultToken;
		int jwtLength = 150;
		
		//JWTService jwtService = new JWTService();
		
		UserAuthenticationDetails inputAuth = new UserAuthenticationDetails(inputUsername, inputPassword);
		Authentication authResponse = new UsernamePasswordAuthenticationToken(inputUsername, inputPassword);
		
		User mockOutputUser = new User();
		mockOutputUser.setUsername(inputUsername);
		mockOutputUser.setPassword(inputPassword);
		Optional<User> mockOutputOptional = Optional.ofNullable(mockOutputUser);
		
		String mockOuputToken = "a token";
		
		
		//Arrange Mock
		when(authenticationManager.authenticate(Mockito.any(Authentication.class))).thenReturn(authResponse);
		when(userRepo.findByUsername(Mockito.any(String.class))).thenReturn(mockOutputOptional);
		when(tokenRepo.save(Mockito.any())).thenReturn(null);
		when(jwtService.generateJwtoken(Mockito.any(),Mockito.any())).thenReturn(mockOuputToken);
		
		//Act
		resultToken = userService.authenticateUser(inputAuth);
		
		//Assert
		assertNotNull(resultToken.getToken());
	}
	
	@Test
	public void Authenticate_User_CheckJWTokenLength_IntegrateJWTService() {
		
		//Arrange
		String inputUsername = "atchah";
		String inputPassword = "password123";
		ResponseToken resultToken;
		int jwtLength = 150;
		
		UserAuthenticationDetails inputAuth = new UserAuthenticationDetails(inputUsername, inputPassword);
		Authentication authResponse = new UsernamePasswordAuthenticationToken(inputUsername, inputPassword);
		
		User mockOutputUser = new User();
		mockOutputUser.setUsername(inputUsername);
		mockOutputUser.setPassword(inputPassword);
		Optional<User> mockOutputOptional = Optional.ofNullable(mockOutputUser);
		
		String mockOuputToken = new JWTService().generateJwtoken(mockOutputUser);
		int expectedMinLength = 150;
		
		
		//Arrange Mock
		when(authenticationManager.authenticate(Mockito.any(Authentication.class))).thenReturn(authResponse);
		when(userRepo.findByUsername(Mockito.any(String.class))).thenReturn(mockOutputOptional);
		when(tokenRepo.save(Mockito.any())).thenReturn(null);
		when(jwtService.generateJwtoken(Mockito.any(),Mockito.any())).thenReturn(mockOuputToken);
		
		//Act
		resultToken = userService.authenticateUser(inputAuth);
		
		//Assert
		assertNotNull(resultToken.getToken().length() > expectedMinLength);
	}


}
