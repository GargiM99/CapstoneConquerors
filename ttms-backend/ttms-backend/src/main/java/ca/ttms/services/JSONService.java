package ca.ttms.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

/**
 * Service to read a write from a JSON file
 * 
 * @author Hamza 
 * date: 2023/03/11 
 */

@Service
@RequiredArgsConstructor
public class JSONService {
	
	/**
	 * Write a JSON object into a JSON file
	 * 
	 * @param object: Object which is going to be written into JSON file
	 * @param filePath: Path to the file of the JSON file
	 * @throws IOException
	 */
	public void writeJsonObject (Object object, String filePath) throws IOException{
	    Gson gson = new Gson();
	    String json = gson.toJson(object);
	    FileWriter writer = new FileWriter(filePath);
	    
	    writer.write(json);
	    writer.close();
	}
	
	/**
	 * Reads from a JSON file 
	 * 
	 * @param filePath: Path to the file of the JSON file
	 * @param objectType: The object type to convert from JSON
	 * @return: Returns an object of the objectType
	 * @throws IOException
	 */
	public Object readJsonFile (String filePath, Class<?> objectType) throws IOException {
	    Gson gson = new Gson();
	    BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    Object object = gson.fromJson(reader, objectType);
	    reader.close();
	    return object;
	}
}
