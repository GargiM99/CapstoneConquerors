package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserEditDetails;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;

/**
 * Tests the profile service which changes user's details
 * date: 2023/04/03
 * @author hamza
 */
class TestProfileService {

	@Mock
	private PersonRepo personRepo;

	@Mock
	private UserRepo userRepo;

	@Mock
	private ContactRepo contactRepo;
	
	@Mock 
	private TokenRepo tokenRepo;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private ProfileService profileService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void Verify_User_CheckResultIsTrue() {
		//Arrange
		int inputId = 1;
		String inputUsername = "atchah";
		
		boolean expectedResult = true;
		boolean actualResult;
		
		User mockUser = new User();
		mockUser.setUsername("atchah");
		Optional<User> mockOutput = Optional.of(mockUser);
		
		//Arrange Mocks
		when(userRepo.findById(Mockito.any())).thenReturn(mockOutput);
		
		//Act
		actualResult = profileService.verifyUser(inputUsername, inputId);
		
		//Assert
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void Verify_NullUser_CheckResultFalse() {
		//Arrange
		int inputId = 1;
		String inputUsername = null;
		
		boolean expectedResult = false;
		boolean actualResult;
		
		User mockUser = new User();
		mockUser.setUsername("smithjo");
		Optional<User> mockOutput = Optional.of(mockUser);
		
		//Arrange Mocks
		when(userRepo.findById(Mockito.any())).thenReturn(mockOutput);
		
		//Act
		actualResult = profileService.verifyUser(inputUsername, inputId);
		
		//Assert
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void Verify_User_InputDifferentThenDB_CheckResultFalse() {
		//Arrange
		int inputId = 1;
		String inputUsername = "atchah";
		
		boolean expectedResult = false;
		boolean actualResult;
		
		User mockUser = new User();
		mockUser.setUsername("smithjo");
		Optional<User> mockOutput = Optional.of(mockUser);
		
		//Arrange Mocks
		when(userRepo.findById(Mockito.anyInt())).thenReturn(mockOutput);
		
		//Act
		actualResult = profileService.verifyUser(inputUsername, inputId);
		
		//Assert
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void Change_Password_CheckResultTrue() {
		//Arrange
		int inputId = 2;
		String inputPassword = "Password123-";
		String mockOutput = "encode password";
		
		boolean expectedResult = true;
		boolean actualResult;
		
		//Arrange Mocks
		when(userRepo.updatePasswordById(Mockito.anyInt(), Mockito.anyString())).thenReturn(0);
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(mockOutput);
		
		//Act 
		actualResult = profileService.updatePassword(inputPassword, inputId);
		
		//Assert
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void Change_Password_WithIncorrectPassword_CheckResultFalse() {
		//Arrange
		int inputId = 2;
		String inputPassword = "Password123";
		String mockOutput = "encode password";
		
		boolean expectedResult = false;
		boolean actualResult;
		
		//Arrange Mocks
		when(userRepo.updatePasswordById(Mockito.anyInt(), Mockito.anyString())).thenReturn(0);
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(mockOutput);
		
		//Act 
		actualResult = profileService.updatePassword(inputPassword, inputId);
		
		//Assert
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void Change_Password_WithSmallPassword_CheckResultFalse() {
		//Arrange
		int inputId = 2;
		String inputPassword = "Ps1-";
		String mockOutput = "encode password";
		
		boolean expectedResult = false;
		boolean actualResult;
		
		//Arrange Mocks
		when(userRepo.updatePasswordById(Mockito.anyInt(), Mockito.anyString())).thenReturn(0);
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn(mockOutput);
		
		//Act 
		actualResult = profileService.updatePassword(inputPassword, inputId);
		
		//Assert
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void Change_ProfileWithCorrectDetails_CheckResultTrue() {
		//Arrange
		String inputUsername = "doesam";
		boolean result;
		
		Person inputPerson = Person.builder().firstname("Samuel").lastname("Doe").build();
		Contact inputContact = Contact.builder().email("john.doe@example.com").primaryPhoneNumber("123-456-7890").build();
		
		UserEditDetails inputDetails = new UserEditDetails(inputPerson, inputContact);
		
		//Arrange Mocks
		when(personRepo.updatePersonByUsername(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		when(contactRepo.updateContactByUsername(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		
		//Act
		result = profileService.updateProfile(inputDetails, inputUsername);
		
		//Assert
		assertTrue(result, "Update profile should return true");
	}
	
	@Test
	void Change_ProfileWithIncorrectDetails_CheckResultFalse() {
		//Arrange
		String inputUsername = "doesam";
		String incorrectEmail = "j";
		boolean result;
		
		Person inputPerson = Person.builder().firstname("Samuel").lastname("Doe").build();
		Contact inputContact = Contact.builder().email(incorrectEmail).primaryPhoneNumber("123-456-7890").build();
		
		UserEditDetails inputDetails = new UserEditDetails(inputPerson, inputContact);
		
		//Arrange Mocks
		when(personRepo.updatePersonByUsername(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		when(contactRepo.updateContactByUsername(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		
		//Act
		result = profileService.updateProfile(inputDetails, inputUsername);
		
		//Assert
		assertFalse(result, "Update profile should return false");
	}
	
	@Test
	void Change_ProfileWithNull_CheckResultFalse() {
		//Arrange
		String inputUsername = "doesam";
		boolean result;	
		UserEditDetails inputDetails = null;
		
		//Act
		result = profileService.updateProfile(inputDetails, inputUsername);
		
		//Assert
		assertFalse(result, "Update profile should return false");
	}
}
