package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import ca.ttms.beans.MealPriceDetails;
import ca.ttms.beans.UserAuthenticationDetails;

public class TestJSONService {

	private final JSONService jsonService = new JSONService();
	
	@Test
	//Tests writing a file then reading it and seeing if they're the same
	void testPassWriteJsonObject1() throws IOException {
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
        String extraPath = "\\test\\test.json";
        String filePath = parentDir + extraPath;
        
        UserAuthenticationDetails inputObject = new UserAuthenticationDetails();
        inputObject.setUsername("testUser");
        inputObject.setPassword("testPass");
		
		jsonService.writeJsonObject(inputObject , filePath);
		var resultObject = jsonService.readJsonFile(filePath, inputObject.getClass());
		UserAuthenticationDetails expectedObject = inputObject;
		
		assertEquals(expectedObject, resultObject);
	}
	
	@Test
	//Tests writing a file then reading it and seeing if they're different
	void testPassWriteJsonObject2() throws IOException {
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
        String extraPath = "\\test\\test.json";
        String filePath = parentDir + extraPath;
        
        UserAuthenticationDetails inputObject = new UserAuthenticationDetails();
        inputObject.setUsername("testUser");
        inputObject.setPassword("testPass");
		
		jsonService.writeJsonObject(inputObject , filePath);
		var resultObject = jsonService.readJsonFile(filePath, inputObject.getClass());
		UserAuthenticationDetails expectedObject = inputObject;
		expectedObject.setUsername("incorrectUser");
		
		assertNotEquals(expectedObject, resultObject);
	}

	@Test
	//Tests if the location is null
	void testFailWriteJsonObject() throws IOException {
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
        String extraPath = "\\test\\test.json";
        String filePath = null;
        
        UserAuthenticationDetails inputObject = new UserAuthenticationDetails();
        inputObject.setUsername("testUser");
        inputObject.setPassword("testPass");
		
		assertThrows(NullPointerException.class , () -> jsonService.writeJsonObject(inputObject , filePath));
	}
}
