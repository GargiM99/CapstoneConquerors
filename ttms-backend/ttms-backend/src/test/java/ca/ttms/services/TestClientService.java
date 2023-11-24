package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserEditDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.response.BasicClientResponse;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.UserRepo;

public class TestClientService {
	
	@Mock
	private UserRepo userRepo;
	
	@Mock
	private PersonRepo personRepo;

	@Mock
	private ContactRepo contactRepo;
	
	@Mock
	private AuthenticationService authService;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private ClientService clientService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void Register_Client_CheckClientBasicResponseIsValid() {
		//Arrange
		BasicClientResponse resultOutput;
		
		Person inputPerson = new Person(2, "Hamza", "Atcha");
		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", null, new Person());
		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson, inputContact);
		
		String inputUsername = "doesam";
		String expectedUsername = "atchah";
		
		//Arrange Mock
		String mockUsernameOutput = "atchah";
		User mockUser = User.builder().username(inputUsername).build();
		User mockUserOutput = User.builder().username(mockUsernameOutput).id(2).role(Roles.CLIENT).build();
		Optional<User> mockUserOpt = Optional.of(mockUser);
		
		when(authService.generateUsername(Mockito.anyString(), Mockito.anyString())).thenReturn(mockUsernameOutput);
		when(userRepo.findByUsername(Mockito.anyString())).thenReturn(mockUserOpt);
		when(personRepo.save(Mockito.any(Person.class))).thenReturn(inputPerson);
		when(userRepo.save(Mockito.any(User.class))).thenReturn(mockUserOutput);
		when(contactRepo.save(Mockito.any(Contact.class))).thenReturn(inputContact);
		
		//Act
		resultOutput = clientService.registerClient(inputUser, inputUsername);
		
		//Assert
		assertEquals(resultOutput.getFirstname(), inputPerson.getFirstname());
		assertEquals(resultOutput.getLastname(), inputPerson.getLastname());
		assertEquals(resultOutput.getUsername(), expectedUsername);
	}			
	
	@Test
	void Register_IncorrectClient_CheckClientBasicResponseIsNull() {
		//Arrange
		BasicClientResponse resultOutput;
		
		String incorrectFirstname = null;
		Person inputPerson = new Person(2, incorrectFirstname, "Atcha");
		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", null, new Person());
		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson, inputContact);
		
		String inputUsername = "doesam";
		
		//Act
		resultOutput = clientService.registerClient(inputUser, inputUsername);
		
		//Assert
		assertNull(resultOutput);
	}
	
	@Test
	void Register_ClientWithInvalidAgent_CheckClientBasicResponseIsNull() {
		//Arrange
		BasicClientResponse resultOutput;
		
		Person inputPerson = new Person(2, "Hamza", "Atcha");
		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", null, new Person());
		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson, inputContact);
		
		String inputUsername = "doesam";
		
		//Arrange Mock
		String mockUsernameOutput = "atchah";
		Optional<User> mockUserOpt = Optional.empty();
		
		when(authService.generateUsername(Mockito.anyString(), Mockito.anyString())).thenReturn(mockUsernameOutput);
		when(userRepo.findByUsername(Mockito.anyString())).thenReturn(mockUserOpt);
		
		//Act
		resultOutput = clientService.registerClient(inputUser, inputUsername);
		
		//Assert
		assertNull(resultOutput);
	}

	@Test
	void Edit_Client_CheckClientBasicResponseIsValid() {
		//Arrange
		boolean resultOutput;
		
		Person inputPerson = new Person(2, "Hamza", "Atcha");
		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", null, new Person());
		UserEditDetails inputUser = new UserEditDetails(inputPerson, inputContact);
		int inputId = 2;
		
		//Arrange Mock
		when(personRepo.updatePersonByUserId(
				Mockito.any(Integer.class), Mockito.anyString(), Mockito.anyString())).thenReturn(1);	
		when(contactRepo.updateContactByUserId(
				Mockito.any(Integer.class), Mockito.anyString(), 
				Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		
		//Act
		resultOutput = clientService.editClientDetails(inputUser, inputId);
		
		//Assert
		assertTrue(resultOutput);
	}	

	@Test
	void Edit_NullClient_CheckClientBasicResponseIsNull() {
		//Arrange
		boolean resultOutput;
		
		UserEditDetails inputUser = null;
		int inputId = 2;
		
		//Act
		resultOutput = clientService.editClientDetails(inputUser, inputId);
		
		//Assert
		assertFalse(resultOutput);
	}

	@Test
	void Edit_ClientWithError_CheckClientBasicResponseIsValidNull() {
		//Arrange
		boolean resultOutput;
		
		Person inputPerson = new Person(2, "Hamza", "Atcha");
		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", null, new Person());
		UserEditDetails inputUser = new UserEditDetails(inputPerson, inputContact);
		int inputId = 2;
		
		//Arrange Mock
		when(personRepo.updatePersonByUserId(
				Mockito.any(Integer.class), Mockito.anyString(), 
				Mockito.anyString())).thenThrow(new DataIntegrityViolationException("Data integrity violation occurred"));	
		when(contactRepo.updateContactByUserId(
				Mockito.any(Integer.class), Mockito.anyString(), 
				Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		
		//Act
		resultOutput = clientService.editClientDetails(inputUser, inputId);
		
		//Assert
		assertFalse(resultOutput);
	}
}
