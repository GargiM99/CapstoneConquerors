package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.ttms.beans.User;
import ca.ttms.repositories.AddressRepo;
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
	private AddressRepo addressRepo;

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
}
