package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.ttms.beans.Address;
import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.response.BasicClientResponse;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.UserRepo;

public class TestClientService {
	
	@Mock
	private UserRepo userRepo;
	
	@Mock
	private PersonRepo personRepo;

	@Mock
	private AddressRepo addressRepo;

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
		
		LocalDate inputDate = LocalDate.now();
		Person inputPerson = new Person(2, "Hamza", "Atcha", inputDate, new ArrayList<Address>());
		Contact inputContact = new Contact(null, "atchah@gmail.com", "905-333-4444", null, new Person());
		Address inputAddress = new Address(null, "1 Main St", "L5Q1E3", "City", "Ontario", "Canada", new ArrayList<Person>());
		UserRegisterDetails inputUser = new UserRegisterDetails(inputPerson, inputContact, inputAddress);
		
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
		when(addressRepo.save(Mockito.any(Address.class))).thenReturn(inputAddress);
		when(contactRepo.save(Mockito.any(Contact.class))).thenReturn(inputContact);
		
		//Act
		resultOutput = clientService.registerClient(inputUser, inputUsername);
		
		//Assert
		assertEquals(resultOutput.getFirstname(), inputPerson.getFirstname());
		assertEquals(resultOutput.getLastname(), inputPerson.getLastname());
		assertEquals(resultOutput.getUsername(), expectedUsername);
	}
}
