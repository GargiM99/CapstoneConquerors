package ca.ttms.services;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.ttms.beans.Person;
import ca.ttms.beans.Token;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.enums.TokenTypes;
import ca.ttms.beans.response.ResponseToken;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

/**
 * Service for authenticating, and registering users
 * 
 * @author Hamza 
 * date: 2023/03/08 
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepo userRepo;
	private final TokenRepo tokenRepo;
	private final AddressRepo addressRepo;
	private final ContactRepo contactRepo;
	private final PersonRepo personRepo;
	
	private final PasswordEncoder passwordEncoder;
	private final JWTService jwtService;
	private final AuthenticationManager authenticationManager;
	
	private final int DEFAULT_LASTNAME_EXTRACT_AMOUNT = 5;
	private final int DEFAULT_FIRSTNAME_EXTRACT_AMOUNT = 1;
	private final int DEFAULT_MIN_USERNAME_LENGTH = 4;
	private final int DEFAULT_NUMERIC_AMOUNT = 0;
	private final int DEFAULT_USERNAME_SPACER = 0;
	
	private final int DEFAULT_PASSWORD_LENGTH = 12;
	
	private final String ALLOWED_UPPERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String ALLOWED_LOWERS = "abcdefghijklmnopqrstuvwxyz";
	private final String ALLOWED_SPECIALS = "!@#$";
	private final String ALLOWED_NUMBERS = "1234567890";
	
	/**
	 * Registers a user into so the user can login
	 * @param userDetails
	 */
	public UserAuthenticationDetails registerUser(UserRegisterDetails userDetails) {
		if (userDetails == null || !userDetails.verifyDetails())
			return null;
		
		Person newPerson = userDetails.getPerson();
		
		Integer similarUserCount = personRepo.countSimilarPeople(
				newPerson.getFirstname(), newPerson.getLastname(), 
				userDetails.getContact().getEmail(), userDetails.getContact().getPrimaryPhoneNumber());
		
		if (similarUserCount > 0)
			return null;
		
		String tempPassword = this.generateTempPassword();
		User newUser = User
				.builder()
				.password(passwordEncoder.encode(tempPassword))
				.username(this.generateUsername(newPerson.getFirstname() , newPerson.getLastname()))
				.role(Roles.AGENT)
				.build();
		
		Person savedPerson = personRepo.save(userDetails.getPerson());
		
		newUser.setPerson(savedPerson);
		userDetails.getAddress().getPersons().add(savedPerson);
		userDetails.getContact().setPerson(savedPerson);
		
		User savedUser = userRepo.save(newUser);
		addressRepo.save(userDetails.getAddress());
		contactRepo.save(userDetails.getContact());
		
		UserAuthenticationDetails details =  UserAuthenticationDetails
				.builder()
				.password(tempPassword)
				.username(savedUser.getUsername())
				.build();
		
		return details;
	}
	
	/**
	 * Registers a user into so the user can login
	 * @param userDetails
	 */
	public UserAuthenticationDetails registerUser(UserRegisterDetails userDetails, Roles role) {
		if (userDetails == null || !userDetails.verifyDetails())
			return null;
		
		Person newPerson = userDetails.getPerson();
		
		Integer similarUserCount = personRepo.countSimilarPeople(
				newPerson.getFirstname(), newPerson.getLastname(), 
				userDetails.getContact().getEmail(), userDetails.getContact().getPrimaryPhoneNumber());
		
		if (similarUserCount > 0)
			return null;
		
		String tempPassword = this.generateTempPassword();
		User newUser = User
				.builder()
				.password(passwordEncoder.encode(tempPassword))
				.username(this.generateUsername(newPerson.getFirstname() , newPerson.getLastname()))
				.role(role)
				.build();
		
		Person savedPerson = personRepo.save(userDetails.getPerson());
		
		newUser.setPerson(savedPerson);
		userDetails.getAddress().getPersons().add(savedPerson);
		userDetails.getContact().setPerson(savedPerson);
		
		User savedUser = userRepo.save(newUser);
		addressRepo.save(userDetails.getAddress());
		contactRepo.save(userDetails.getContact());
		
		UserAuthenticationDetails details =  UserAuthenticationDetails
				.builder()
				.password(tempPassword)
				.username(savedUser.getUsername())
				.build();
		
		return details;
	}
	
	/**
	 * Generate a temporary password with various character for user
	 * @return: A temp password for login
	 */
	public String generateTempPassword() {
		int length = DEFAULT_PASSWORD_LENGTH;
		
	    String allowedChars = ALLOWED_UPPERS + ALLOWED_LOWERS + ALLOWED_SPECIALS + ALLOWED_NUMBERS;
	    Random random = new Random();
	    
	    char[] password = new char[length];
	   
	    for(int i = 0; i< length ; i++) {
	       password[i] = allowedChars.charAt(random.nextInt(allowedChars.length()));
	    }
	    
	    return String.copyValueOf(password);
	}
	
	/**
	 * Generates a username using a firstname and lastname
	 * @param firstname: Firstname for the user
	 * @param lastname: Lastname for the user
	 * @return: The generated username
	 */
	public String generateUsername (String firstname, String lastname) {
		String username = "";
		String lastnameExtract = "";
		String firstnameExtract = "";
		
		int firstnameExtractAmount = DEFAULT_FIRSTNAME_EXTRACT_AMOUNT;
		int lastnameExtractAmount = DEFAULT_LASTNAME_EXTRACT_AMOUNT;
		int numericAmount = DEFAULT_NUMERIC_AMOUNT;
		int spacerAmount = DEFAULT_USERNAME_SPACER;
		
		//Converting to lowercase
		firstname = firstname.toLowerCase().replaceAll("[^a-zA-Z]", "");
		lastname = lastname.toLowerCase().replaceAll("[^a-zA-Z]", "");
		
		//Determining the amount to extract from lastname and firstname
		if (lastname.length() < lastnameExtractAmount) {
			firstnameExtractAmount = firstnameExtractAmount + (lastnameExtractAmount - lastname.length());
			lastnameExtractAmount = lastname.length();	
		}
		
		//Checks if there needs to be numeric amount added
		if (firstname.length() < firstnameExtractAmount) {
			firstnameExtractAmount = firstname.length();
			numericAmount = DEFAULT_MIN_USERNAME_LENGTH - (firstnameExtractAmount + lastnameExtractAmount) + spacerAmount;
		}
		
		lastnameExtract = lastname.substring(0, lastnameExtractAmount);
		firstnameExtract = firstname.substring(0, firstnameExtractAmount);
		username = lastnameExtract + firstnameExtract;
		
		//Finds similar usernames in the repo
		List<String> similarUsernames = userRepo.findUserWithSimilarUsernames(username);
		
		//Creates a username with numeric at the end if username already exists
		if (similarUsernames != null && similarUsernames.size() != 0) 
			return createUsernameWithNumbers(similarUsernames.get(similarUsernames.size()-1), numericAmount);
		
		if (numericAmount > 0)
			return createUsernameWithNumbers(username, numericAmount);
		
		return username;
	}
	
	/**
	 * Creates a username with numbers added at the end
	 * @param username
	 * @param numericAmount
	 * @return
	 */
	private String createUsernameWithNumbers (String username, int numericAmount) {
		String alphaUsername = username.replaceAll("[^a-zA-Z]", "");
		String numericsUsername = (username.replaceAll("[^0-9]", ""));
		int numerics = 0;
		
		if (!numericsUsername.equals("") && numericsUsername != null)
			numerics = Integer.parseInt(numericsUsername);
		
		if (numericAmount <= 0)
			numericAmount = 1;
			
		return alphaUsername + String.format("%0" + numericAmount + "d" , ++numerics);
	}
	
	/**
	 * Authenticates a user based on credentials
	 * 
	 * @param authDetails
	 * @return ResponseToken
	 */
	public ResponseToken authenticateUser(UserAuthenticationDetails authDetails) {
		
	    authenticationManager.authenticate(
	    	new UsernamePasswordAuthenticationToken(
	    		authDetails.getUsername(),
	            authDetails.getPassword()
	    		)
	        );
	    
	    var user = userRepo.findByUsername(authDetails.getUsername()).orElseThrow();
	    var claimMap = new HashMap<String, Object>();
	    claimMap.put("Role", user.getRole());
	    
	    var jwtoken = jwtService.generateJwtoken(claimMap, user);
	    saveUserToken(user, jwtoken);
	    
	    return new ResponseToken(jwtoken);
	}
	
	/**
	 * Saves token along with the user, and status to the database
	 * 
	 * @param user
	 * @param jwtoken
	 */
	private void saveUserToken(User user, String jwtoken) {
	    var token = Token.builder()
	            .user(user)
	            .token(jwtoken)
	            .tokenType(TokenTypes.BEARER)
	            .expired(false)
	            .revoked(false)
	            .build();
	    
	     tokenRepo.save(token);
	}
	
	/**
	 * Revokes all tokens for a user and sets them to expire
	 * @param user
	 */
	private void revokeUserToken (User user) {
		var validUserJwtokens = tokenRepo.findAllValidTokenByUser(user.getId());
		
		if (validUserJwtokens.isEmpty())
			return;
		
		validUserJwtokens.forEach(jwtoken -> {
			jwtoken.setExpired(true);
			jwtoken.setRevoked(true);
		});
		
		tokenRepo.saveAll(validUserJwtokens);
	}
}
