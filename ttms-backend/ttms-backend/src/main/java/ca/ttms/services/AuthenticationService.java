package ca.ttms.services;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.ttms.beans.Token;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.enums.TokenTypes;
import ca.ttms.beans.response.ResponseToken;
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
	private final PasswordEncoder passwordEncoder;
	private final JWTService jwtService;
	private final AuthenticationManager authenticationManager;
	

	/**
	 * Register a user, adds it to the server, and returns a JWT
	 * 
	 * @param userDetails
	 * @return ResponseToken
	 */
	public ResponseToken registerUser(UserRegisterDetails userDetails) {
		if (userDetails == null || !userDetails.isValid())
			return null;
		
		var newUser = User
				.builder()
				.firstname(userDetails.getFirstname())
				.lastname(userDetails.getLastname())
				.email(userDetails.getEmail())
				.username(userDetails.getUsername())
				.password(passwordEncoder.encode(userDetails.getPassword()))
				.role(Roles.ADMIN)
				.build();
		
		var savedUser = userRepo.save(newUser);
		var jwtoken = jwtService.generateJwtoken(newUser);
		
		saveUserToken(savedUser, jwtoken);
		return new ResponseToken(jwtoken);
	}

	/**
	 * Creates user with specified role
	 * 
	 * @param userDetails
	 * @param role
	 * @return ResponseToken
	 */
	public ResponseToken registerUser(UserRegisterDetails userDetails, Roles role) {
		if (userDetails == null || !userDetails.isValid())
			return null;
		
		var newUser = User
				.builder()
				.firstname(userDetails.getFirstname())
				.lastname(userDetails.getLastname())
				.email(userDetails.getEmail())
				.username(userDetails.getUsername())
				.password(passwordEncoder.encode(userDetails.getPassword()))
				.role(role)
				.build();
		
		var savedUser = userRepo.save(newUser);
		var jwtoken = jwtService.generateJwtoken(newUser);
		
		saveUserToken(savedUser, jwtoken);
		return new ResponseToken(jwtoken);
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
	 * 
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
