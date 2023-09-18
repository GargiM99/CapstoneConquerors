package ca.ttms.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.ttms.beans.details.UserEditDetails;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserFullDetails;
import ca.ttms.services.JWTService;
import ca.ttms.services.ProfileService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
/**
 * REST controller for handling request for user's profile
 * date: 2023/04/04
 * @author hamza
 */
public class ProfileController {
	
	private final JWTService jwtService;
	private final ProfileService profileService;
	
	@GetMapping()
	public ResponseEntity getDetails(@RequestHeader("Authorization") String authHeader) {
		String jwtoken = authHeader.substring(7);
		String username = jwtService.extractSubject(jwtoken);
		
		if (username == null)
			return ResponseEntity.status(401).body("username null");
		
		UserFullDetails userDetails = profileService.getUserDetails(username);
		
		if (userDetails == null)
			return ResponseEntity.status(500).body(userDetails);
		
		return ResponseEntity.ok(userDetails);
	}
	
	@PutMapping("")
	public ResponseEntity<String> updateProfile(@RequestHeader("Authorization") String authHeader,
										 @RequestBody UserEditDetails profileDetails) {
		
		String jwtoken = authHeader.substring(7);
		String subject = jwtService.extractSubject(jwtoken);
		
		if (subject == null)
			return ResponseEntity.status(401).body("username null");
		
		boolean isUpdated = profileService.updateProfile(profileDetails, subject);
		
		if (!isUpdated)
			return ResponseEntity.status(400).body("invalid information");
		
		return ResponseEntity.ok("profile updated");
	}
	
	@PutMapping("password/{userId}")
	public ResponseEntity<String> updatePassword(@PathVariable Integer userId, 
							             @RequestHeader("Authorization") String authHeader,
							             @RequestBody UserAuthenticationDetails userDetails) {
		
		String jwtoken = authHeader.substring(7);
		userDetails.setUsername(jwtService.extractSubject(jwtoken));
		
		if (userDetails.getUsername() == null)
			return ResponseEntity.status(401).body("username null");
		
		boolean isUserVerified = profileService.verifyUser(userDetails.getUsername(), userId);
		
		if (!isUserVerified)
			return ResponseEntity.status(401).body("username not verified");
		
		boolean isPasswordValid = profileService.updatePassword(userDetails.getPassword(), userId);
		
		if (!isPasswordValid)
			return ResponseEntity.status(400).body("password invalid");
		
		return ResponseEntity.ok("password updated");
	}
	
	
}
