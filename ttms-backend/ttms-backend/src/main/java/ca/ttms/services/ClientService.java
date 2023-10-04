package ca.ttms.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.ttms.beans.Person;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.response.BasicClientResponse;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
	
	private final UserRepo userRepo;
	private final AddressRepo addressRepo;
	private final PersonRepo personRepo;
	private final ContactRepo contactRepo;
	 
	private final AuthenticationService authService;

	public BasicClientResponse registerClient(UserRegisterDetails userDetails, String agentUsername) {
		if (userDetails == null || !userDetails.verifyDetails())
			return null;
		
		if (agentUsername == null || agentUsername.equals(""))
			return null;
		
		String username = authService.generateUsername(userDetails.getPerson().getFirstname(), userDetails.getPerson().getLastname());
		Optional<User> agentUserOpt = userRepo.findByUsername(agentUsername);
		
		if (agentUserOpt.isEmpty())
			return null;
		
		User agentUser = agentUserOpt.get();
		
		Person savedPerson = personRepo.save(userDetails.getPerson());
		User newUser = User
				.builder()
				.username(username)
				.role(Roles.CLIENT)
				.agentUser(agentUser)
				.person(savedPerson)
				.build();
		
		userDetails.getAddress().getPersons().add(savedPerson);
		userDetails.getContact().setPerson(savedPerson);
		
		User savedUser = userRepo.save(newUser);
		contactRepo.save(userDetails.getContact());
		addressRepo.save(userDetails.getAddress());
		
		return BasicClientResponse
				.builder()
				.id(savedUser.getId())
				.firstname(savedPerson.getFirstname())
				.lastname(savedPerson.getLastname())
				.username(savedUser.getUsername())
				.build();
	}
	
}
