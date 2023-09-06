package ca.ttms.integration;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import ca.ttms.beans.Address;
import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.Token;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.response.ResponseToken;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;
import ca.ttms.services.AuthenticationService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestIntegartionAuthenticationService {

	@Autowired
	private AuthenticationService service;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private TokenRepo tokenRepo;
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	private Person inputPerson1, inputPerson2, inputPerson3, inputPerson4;
	private Contact inputContact;
	private Address inputAddress;
	
	@BeforeAll
	void Arrange_inputUserDetails() {
		LocalDate inputDate = LocalDate.now();
		inputPerson1 = new Person(null, "Hamza", "Atcha", inputDate, new ArrayList<Address>());
		inputPerson2 = new Person(null, "John", "Smith", inputDate, new ArrayList<Address>());
		inputPerson3 = new Person(null, "Rick", "Rolls", inputDate, new ArrayList<Address>());
		inputPerson4 = new Person(null, "Sam", "Smiles", inputDate, new ArrayList<Address>());
		
		inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", "", new Person());
		inputAddress = new Address(null, "1 Main St", "L5Q1E3", "City", "Ontario", "Canada", new ArrayList<Person>());
	}
	
	@Test
	//Register a user and check that the username is returned
	void Register_SingleUser_CheckAuthDetailsNotNull() {

		//Arrange
		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson1, inputContact, inputAddress);
		UserAuthenticationDetails resultAuth;

		//Act
		resultAuth = service.registerUser(inputUser);

		//Assert
		assertNotNull(resultAuth);
	}

	@Test
	//Register a user and check that the username is returned
	void Register_DuplicateUser_CheckAuthDetailsNull() {

		//Arrange
		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson4, inputContact, inputAddress);
		UserRegisterDetails duplicateUser = inputUser;
		UserAuthenticationDetails resultAuth;
		UserAuthenticationDetails resultDuplicateAuth;

		//Act
		resultAuth = service.registerUser(inputUser);
		resultDuplicateAuth = service.registerUser(duplicateUser);

		//Assert
		assertNotNull(resultAuth);
		assertNull(resultDuplicateAuth);
	}

	@Test
	//Register a user and check that the username is returned
	void Register_NullUser_CheckAuthDetailsNull() {

		//Arrange
		UserRegisterDetails inputUser = null;
		UserAuthenticationDetails resultAuth;

		//Act
		resultAuth = service.registerUser(inputUser);

		//Assert
		assertNull(resultAuth);
	}

	@Test
	//Authenticate a user and checks that there is a token and it's valid
	void Authenticate_SingleUser_CompareTokenFromReturnToRepo() {
		
		//Arrange
		ResponseToken resultToken, expectedToken;
		expectedToken = new ResponseToken();
		
		UserAuthenticationDetails inputAuth;
		UserRegisterDetails inputUserDetails = new UserRegisterDetails(inputPerson2, inputContact, inputAddress);
		
		//Act
		inputAuth = service.registerUser(inputUserDetails);
		
		resultToken = service.authenticateUser(inputAuth);
		Optional<User> respnoseUser = userRepo.findByUsername(inputAuth.getUsername());
		User resultUser = respnoseUser.orElse(null);
		
		List<Token> respnoseToken = tokenRepo.findAllValidTokenByUser(resultUser.getId());
		expectedToken.setToken(respnoseToken.get(0).getToken());
		
		//Assert
		assertEquals(resultToken.getToken(),expectedToken.getToken());
	}
	
	@Test
	//Authenticate a user and checks if token is valid
	void Authenticate_SingleUser_CheckToken() {
		//Arrange
		int expectedTokenLength = 150;
		int resultLength;
		
		UserAuthenticationDetails inputAuth;
		UserRegisterDetails inputUserDetails = new UserRegisterDetails(inputPerson3, inputContact, inputAddress);
		
		//Act
		inputAuth = service.registerUser(inputUserDetails);
		
		ResponseToken resultToken = service.authenticateUser(inputAuth);
		resultLength = resultToken.getToken().length();
		
		//Assert
		assertTrue(resultLength > expectedTokenLength, "Token not recieved");
	}
	
    @AfterAll
    public void cleanup() {
        // Execute SQL cleanup commands
        jdbcTemplate.execute("DELETE FROM token");
        jdbcTemplate.execute("DELETE FROM address_person");
        jdbcTemplate.execute("DELETE FROM _address");
        jdbcTemplate.execute("DELETE FROM contact");
        jdbcTemplate.execute("DELETE FROM _user");
        jdbcTemplate.execute("DELETE FROM person");
    }
}
