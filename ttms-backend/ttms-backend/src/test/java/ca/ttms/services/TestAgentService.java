package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.ttms.beans.Address;
import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.details.UserEditDetails;
import ca.ttms.beans.details.UserFullDetails;
import ca.ttms.beans.response.ResetPasswordResponse;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.UserRepo;

class TestAgentService {

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
	private AgentService agentService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void Get_Agent_CheckMapArrayLength() {
		//Arrange
		Map<String, Object>[] resultOutput;
		int expectedLength = 1;
		
		//Arrange Mock
		Map<String, Object> mockOutput = new HashMap<String, Object>();
		mockOutput.put("value1", "Hamza");
		
		List<Map<String, Object>> mockOuputList = new ArrayList<Map<String, Object>>();
		mockOuputList.add(mockOutput);
		
		when(userRepo.getAllAgents()).thenReturn(mockOuputList);
		
		//Act
		resultOutput = agentService.getAgents();
		
		//Assert
		assertEquals(resultOutput.length, expectedLength);
	}

	@Test
	void Get_MutlipleAgent_CheckMapArrayLength() {
		//Arrange
		Map<String, Object>[] resultOutput;
		int expectedLength = 2;
		
		//Arrange Mock
		Map<String, Object> mockOutput1 = new HashMap<String, Object>();
		mockOutput1.put("value1", "Hamza");
		
		Map<String, Object> mockOutput2 = new HashMap<String, Object>();
		mockOutput2.put("value2", "John");
		
		List<Map<String, Object>> mockOutputList = new ArrayList<Map<String, Object>>();
		mockOutputList.add(mockOutput1);
		mockOutputList.add(mockOutput2);
		
		when(userRepo.getAllAgents()).thenReturn(mockOutputList);
		
		//Act
		resultOutput = agentService.getAgents();
		
		//Assert
		assertEquals(resultOutput.length, expectedLength);
	}
	
	@Test
	void Get_AgentFullDetails_CheckAgentDetailsNotNull() {
		
		//Arrange Mock
		Map mockDetailsMap = new HashMap<String, Object>();
		
		List mockDetailsList = new ArrayList<Map<String, Object>>();
		mockDetailsList.add(mockDetailsMap);
		
		when(userRepo.getUserFullInfoById(2)).thenReturn(mockDetailsList);
		
		//Arrange
		UserFullDetails resultOutput;  
		
		//Act
		resultOutput = agentService.getAgentDetails(2);
		
		//Assert
		assertNotNull(resultOutput);
	}
	
	@Test
	void Get_AgentFullDetailsWithIncorrectId_CheckAgentDetailsIsNull() {
		
		//Arrange Mock
		Map mockDetailsMap = new HashMap<String, Object>();
		
		List mockDetailsList = new ArrayList<Map<String, Object>>();
		mockDetailsList.add(mockDetailsMap);
		
		when(userRepo.getUserFullInfoById(0)).thenReturn(mockDetailsList);
		
		//Arrange
		UserFullDetails resultOutput;  
		
		//Act
		resultOutput = agentService.getAgentDetails(-1);
		
		//Assert
		assertNull(resultOutput);
	}
	
	@Test
	void Get_AgentFullDetailsWithNonExistantId_CheckAgentDetailsIsNull() {
		
		//Arrange Mock
		Map mockDetailsMap = new HashMap<String, Object>();
		
		List mockDetailsList = new ArrayList<Map<String, Object>>();
		
		when(userRepo.getUserFullInfoById(2)).thenReturn(mockDetailsList);
		
		//Arrange
		UserFullDetails resultOutput;  
		
		//Act
		resultOutput = agentService.getAgentDetails(0);
		
		//Assert
		assertNull(resultOutput);
	}

	@Test
	void Change_AgentProfileWithCorrectDetails_CheckResultTrue() {
		//Arrange
		Integer inputId = 1;
		boolean result;
		
		Person inputPerson = Person.builder().firstname("Alex").lastname("Smith").birthDate(LocalDate.of(2003, 10, 28)).build();
		Contact inputContact = Contact.builder().email("alex.smith@example.com").primaryPhoneNumber("123-456-7890").build();
		Address inputAddress = Address.builder().addressLine("1 Main St").city("Oakville").country("Canada")
				               .province("Ontario").postalCode("L2V1Q8").build();
		
		UserEditDetails inputDetails = new UserEditDetails(inputPerson, inputContact, inputAddress);
		
		//Arrange Mocks
		when(personRepo.updatePersonByUserId(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(1);
		when(addressRepo.updateAddressByUserId(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), 
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		when(contactRepo.updateContactByUserId(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		
		//Act
		result = agentService.updateAgentProfile(inputDetails, inputId);
		
		//Assert
		assertTrue(result, "Update profile should return true");
	}
	
	@Test
	void Change_ProfileWithIncorrectDetails_CheckResultFalse() {
		//Arrange
		Integer inputId = 1;
		String incorrectFirstname = null;
		boolean result;
		
		Person inputPerson = Person.builder().firstname(incorrectFirstname).lastname("Doe").birthDate(LocalDate.of(2003, 10, 28)).build();
		Contact inputContact = Contact.builder().email("samuel.doe@gmail.com").primaryPhoneNumber("123-456-7890").build();
		Address inputAddress = Address.builder().addressLine("1 Main St").city("Oakville").country("Canada")
				               .province("Ontario").postalCode("L2V1Q8").build();
		
		UserEditDetails inputDetails = new UserEditDetails(inputPerson, inputContact, inputAddress);
		
		//Arrange Mocks
		when(personRepo.updatePersonByUserId(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(1);
		when(addressRepo.updateAddressByUserId(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), 
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		when(contactRepo.updateContactByUserId(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(1);
		
		//Act
		result = agentService.updateAgentProfile(inputDetails, inputId);
		
		//Assert
		assertFalse(result, "Update profile should return false");
	}
	
	@Test
	void Change_ProfileWithNegativeId_CheckResultFalse() {
		//Arrange
		Integer inputId = -1;
		String incorrectFirstname = null;
		boolean result;
		
		Person inputPerson = Person.builder().firstname(incorrectFirstname).lastname("Doe").birthDate(LocalDate.of(2003, 10, 28)).build();
		Contact inputContact = Contact.builder().email("samuel.doe@gmail.com").primaryPhoneNumber("123-456-7890").build();
		Address inputAddress = Address.builder().addressLine("1 Main St").city("Oakville").country("Canada")
				               .province("Ontario").postalCode("L2V1Q8").build();
		
		UserEditDetails inputDetails = new UserEditDetails(inputPerson, inputContact, inputAddress);
		
		//Act
		result = agentService.updateAgentProfile(inputDetails, inputId);
		
		//Assert
		assertFalse(result, "Update profile should return false");
	}
	
	@Test
	void Reset_AgentPasswordWithCorrectId_CheckPasswordIsValid() {
		//Arrange
		Integer inputId = 1;
		ResetPasswordResponse result;
		int expectedLength = 5;
		
		//Arrange Mocks
		when(userRepo.updatePasswordById(Mockito.anyInt(), Mockito.anyString())).thenReturn(1);
		when(authService.generateTempPassword()).thenReturn("password");
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");
		
		//Act
		result = agentService.resetAgentPassword(inputId);
		
		//Assert
		assertTrue(result.getPassword().length() > expectedLength, "Should return a password");
	}
	
	@Test
	void Reset_AgentPasswordWithIncorrectId_CheckPasswordIsNull() {
		//Arrange
		Integer inputId = 0;
		ResetPasswordResponse result;
		ResetPasswordResponse expectedOutput = null;
		
		//Arrange Mocks
		when(userRepo.updatePasswordById(Mockito.anyInt(), Mockito.anyString())).thenReturn(1);
		when(authService.generateTempPassword()).thenReturn("password");
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");
		
		//Act
		result = agentService.resetAgentPassword(inputId);
		
		//Assert
		assertTrue(result == expectedOutput, "Should return a null");
	}
}
