package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.ttms.beans.details.UserFullDetails;
import ca.ttms.repositories.UserRepo;

class TestAgentService {

	@Mock
	private UserRepo userRepo;
	
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
}
