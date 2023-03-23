package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import ca.ttms.beans.details.UserAuthenticationDetails;

public class TestJSONService {

	private final JSONService jsonService = new JSONService();
	
	@Test
	//Tests writing a file then reading it and seeing if they're the same
	void Write_SingleObject_CompareObjectsFromReadToInput() throws IOException {
		
		//Arrange
		UserAuthenticationDetails resultObject;
		UserAuthenticationDetails inputObject = new UserAuthenticationDetails("testUser", "testPass");
		UserAuthenticationDetails expectedObject = inputObject;
		
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
        String extraPath = "\\test\\test.json";
        String filePath = parentDir + extraPath;
		
        //Act
		jsonService.writeJsonObject(inputObject , filePath);
		resultObject = (UserAuthenticationDetails)jsonService.readJsonFile(filePath, inputObject.getClass());
		
		//Assert
		assertEquals(expectedObject, resultObject);
	}
	
	@Test
	//Tests writing a file then reading it and seeing if they're different
	void WriteMultipleTimes_SingleObject_CompareObjectsFromReadToInput() throws IOException {
		
		//Arrange
		UserAuthenticationDetails resultObject;
		UserAuthenticationDetails inputObject = new UserAuthenticationDetails("testUser", "testPass");
		UserAuthenticationDetails expectedObject;
		
		String parentDir = new File (System.getProperty("user.dir")).getParentFile().getParent();
        String extraPath = "\\test\\test.json";
        String filePath = parentDir + extraPath;
		
        //Act
		jsonService.writeJsonObject(inputObject , filePath);
		inputObject.setUsername("newPassword");
		
		jsonService.writeJsonObject(inputObject , filePath);
		expectedObject = inputObject;
		
		resultObject = (UserAuthenticationDetails)jsonService.readJsonFile(filePath, inputObject.getClass());
		
		//Assert
		assertEquals(expectedObject, resultObject);
	}

	@Test
	//Tests if the location is null
	void Write_NullPath_CheckNullPointer() throws IOException {
		
		//Arrange
        String filePath = null;
        UserAuthenticationDetails inputObject = new UserAuthenticationDetails("testUser", "testPass");
		
        //Act & Assert
		assertThrows(NullPointerException.class , () -> jsonService.writeJsonObject(inputObject , filePath));
	}
}
