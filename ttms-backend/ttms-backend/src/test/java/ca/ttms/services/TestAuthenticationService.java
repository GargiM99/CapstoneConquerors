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

//	@Test
//	// Register a user and check that the username is returned
//	void Register_SingleUser_CheckAuthDetailsNotNull() {
//
//		// Arrange
//		LocalDate inputDate = LocalDate.now();
//		Person inputPerson = new Person(null, "Hamza", "Atcha", inputDate, new ArrayList<Address>());
//		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", "", new Person());
//		Address inputAddress = new Address(null, "1 Main St", "L5Q1E3", "City", "Ontario", "Canada",
//				new ArrayList<Person>());
//
//		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson, inputContact, inputAddress);
//		UserAuthenticationDetails resultAuth;
//
//		// Act
//		resultAuth = service.registerUser(inputUser);
//
//		// Assert
//		assertNotNull(resultAuth);
//	}
//
//	@Test
//	// Register a user and check that the username is returned
//	void Register_DuplicateUser_CheckAuthDetailsNull() {
//
//		// Arrange
//		LocalDate inputDate = LocalDate.now();
//		Person inputPerson = new Person(null, "John", "Smith", inputDate, null);
//		Contact inputContact = new Contact(null, "smithjo@gmail.com", "905-555-6666", "", null);
//		Address inputAddress = new Address(null, "1 Main St", "L5Q1E3", "City", "Ontario", "Canada", null);
//
//		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson, inputContact, inputAddress);
//		UserRegisterDetails duplicateUser = new UserRegisterDetails(inputPerson, inputContact, inputAddress);
//		UserAuthenticationDetails resultAuth;
//		UserAuthenticationDetails resultDuplicateAuth;
//
//		// Act
//		resultAuth = service.registerUser(inputUser);
//		resultDuplicateAuth = service.registerUser(duplicateUser);
//
//		// Assert
//		assertNotNull(resultAuth);
//		assertNull(resultDuplicateAuth);
//	}
//
//	@Test
//	// Register a user and check that the username is returned
//	void Register_NullUser_CheckAuthDetailsNull() {
//
//		// Arrange
//		UserRegisterDetails inputUser = null;
//		UserAuthenticationDetails resultAuth;
//
//		// Act
//		resultAuth = service.registerUser(inputUser);
//
//		// Assert
//		assertNull(resultAuth);
//	}

//	@Test
//	//Register a user and checks that there is a token
//	void Register_SingleUser_CheckToken() {
//		
//		//Arrange
//		int expectedTokenLength = 150;
//		int resultTokenLength;
//		
//		UserRegisterDetails inputUser = UserRegisterDetails.builder()
//				.firstname("Akshat")
//				.lastname("Amin")
//				.email("aa@gmail.com")
//				.username("aa")
//				.password("1234")
//				.build();
//		
//		//Act
//		ResponseToken resultToken = service.registerUser(inputUser);
//		resultTokenLength = resultToken.getToken().length();
//		
//		assertTrue(resultTokenLength > expectedTokenLength, "Token should be generated: " + resultToken );
//	}
//	
//	//Register a user and checks that there is a token and user
//	@Test
//	void Register_MultipleUsers_CompareToken() {
//		
//		//Arrange
//		String resultUsername, expectedUsername;
//		Token resultToken;
//		ResponseToken expectedToken;
//		
//		UserRegisterDetails inputUser = UserRegisterDetails.builder()
//				.firstname("Hamza")
//				.lastname("Atcha")
//				.email("ha@gmail.com")
//				.username("ha")
//				.password("2345")
//				.build();
//		
//		expectedUsername = inputUser.getUsername();
//		
//		//Act
//		expectedToken = service.registerUser(inputUser);
//		Optional<User> respnoseUser = userRepo.findByUsername(inputUser.getUsername());
//		User resultUser = respnoseUser.orElse(null);
//		resultUsername = resultUser.getUsername();
//		
//		List<Token> respnoseToken = tokenRepo.findAllValidTokenByUser(resultUser.getId());
//		resultToken = respnoseToken.get(0);
//		
//		//Assert
//		assertEquals(resultUsername, expectedUsername);
//		assertEquals(resultToken.getToken(),expectedToken.getToken());
//	}
//	
//	@Test
//	//Register a null user and checks if it returns null
//	void Register_NullUser_CheckNullPointer() {
//		
//		//Arrange
//		UserRegisterDetails inputUser = null;
//		ResponseToken expectedToken = null;
//		
//		//Act
//		ResponseToken resultToken = service.registerUser(inputUser);
//		
//		//Assert
//		assertEquals(expectedToken, resultToken);
//	}
//
//	@Test
//	//Authenticate a user and checks that there is a token and it's valid
//	void Authenticate_SingleUser_CompareTokenFromReturnToRepo() {
//		
//		//Arrange
//		ResponseToken resultToken, expectedToken;
//		expectedToken = new ResponseToken();
//		
//		UserRegisterDetails inputUser = UserRegisterDetails.builder()
//				.firstname("Gargi")
//				.lastname("Matoo")
//				.email("gm@gmail.com")
//				.username("gm")
//				.password("3456")
//				.build();
//		
//		UserAuthenticationDetails inputAuth = UserAuthenticationDetails.builder()
//				.username(inputUser.getUsername())
//				.password(inputUser.getPassword())
//				.build();
//		
//		//Act
//		service.registerUser(inputUser);
//		resultToken = service.authenticateUser(inputAuth);
//		Optional<User> respnoseUser = userRepo.findByUsername(inputUser.getUsername());
//		User resultUser = respnoseUser.orElse(null);
//		
//		List<Token> respnoseToken = tokenRepo.findAllValidTokenByUser(resultUser.getId());
//		expectedToken.setToken(respnoseToken.get(1).getToken());
//		
//		//Assert
//		assertEquals(resultToken.getToken(),expectedToken.getToken());
//	}
//	
//	@Test
//	//Authenticate a user and checks if token is valid
//	void Authenticate_SingleUser_CheckToken() {
//		//Arrange
//		int expectedTokenLength = 150;
//		int resultLength;
//		UserRegisterDetails inputUser = UserRegisterDetails.builder()
//				.firstname("Zunaira")
//				.lastname("Malik")
//				.email("zm@gmail.com")
//				.username("zm")
//				.password("4567")
//				.build();
//		
//		//Act
//		service.registerUser(inputUser);
//		UserAuthenticationDetails inputAuth = UserAuthenticationDetails.builder()
//				.username(inputUser.getUsername())
//				.password(inputUser.getPassword())
//				.build();
//		
//		ResponseToken resultToken = service.authenticateUser(inputAuth);
//		resultLength = resultToken.getToken().length();
//		
//		//Assert
//		assertTrue(resultLength > expectedTokenLength, "Token not recieved");
//	}

}
