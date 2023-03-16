package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import ca.ttms.beans.UserAuthenticationDetails;

public class TestJSONService {

	private final JSONService jsonService = new JSONService();
	
	/**
	 * Tests writing a file then reading it and seeing if they're the same
	 * 
	 * @throws IOException
	 */
	@Test
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
	
	/**
	 * Tests writing a file then reading it and seeing if they're different
	 * 
	 * @throws IOException
	 */
	@Test
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

	/**
	 * Tests if the location is null
	 * 
	 * @throws IOException
	 */
	@Test
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
