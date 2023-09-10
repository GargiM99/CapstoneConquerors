package ca.ttms.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.ttms.beans.User;
import ca.ttms.beans.details.UserFullDetails;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
	
	private final UserRepo userRepo;
	private final TokenRepo tokenRepo;
	private final AddressRepo addressRepo;
	private final ContactRepo contactRepo;
	private final PersonRepo personRepo;
	private final PasswordEncoder passwordEncoder;
	
	private final String PASSWORD_POLICY = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%^&*-]).{8,}$";
	
	public boolean verifyUser(String username, int id) {
		if (username == null && id > 0)
			return false;
		
		User dbUser = userRepo.findById(id).orElse(null);
		String dbUsername = dbUser.getUsername();
		
		if (dbUsername.equals(username))
			return true;
		
		return false;
	}
	
	public boolean updatePassword(String password, int id) {
		if (password == null || !password.matches(PASSWORD_POLICY))
			return false;
		
		String encodedPassword = passwordEncoder.encode(password);
		
		userRepo.updatePasswordById(id, encodedPassword);
		return true;
	}
	
//	public User getFullDetails(String username) {
//		if (username == null)
//			return null;
//		
//		Optional<User> user = userRepo.findByUsername(username);
//		return user.orElse(null);
//	}
	
	public UserFullDetails getUserDetails (String username) {
		if (username == null)
			return null;
		
		try {
			List<Map<String, Object>> userDetailsMapDB = userRepo.getUserFullInfo(username);
			
			UserFullDetails userDetails = new UserFullDetails();
			userDetails.mapDetailsFromRepo(userDetailsMapDB.get(0));
			
			return userDetails;
		}catch(Exception e) {
			return null;
		}
	}
}
