package ca.ttms.services;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserEditDetails;
import ca.ttms.beans.details.UserFullDetails;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
	
	private final UserRepo userRepo;
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
	
	public boolean updateProfile(UserEditDetails updateProfile, String username) {
		if (updateProfile == null)
			return false;
		
		if (!updateProfile.verifyDetails())
			return false;
		
		try {
			Contact updateContact = updateProfile.getContact();
			Person updatePerson = updateProfile.getPerson();
			
			contactRepo.updateContactByUsername(username, updateContact.getEmail(), 
										updateContact.getPrimaryPhoneNumber(), updateContact.getSecondaryPhoneNumber());
			
			personRepo.updatePersonByUsername(username, updatePerson.getFirstname(), updatePerson.getLastname());

			return true;
		}catch(Exception e) {
			return false;
		}

	}
	
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
