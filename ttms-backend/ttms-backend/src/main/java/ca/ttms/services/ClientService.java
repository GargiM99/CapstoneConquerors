package ca.ttms.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.ttms.beans.Person;
import ca.ttms.beans.Trip;
import ca.ttms.beans.User;
import ca.ttms.beans.details.BasicUsersDetails;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.response.BasicClientResponse;
import ca.ttms.beans.response.ClientDetailsResponse;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.TripRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
	
	private final UserRepo userRepo;
	private final AddressRepo addressRepo;
	private final PersonRepo personRepo;
	private final ContactRepo contactRepo;
	private final TripRepo tripRepo;
	 
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
	
	public boolean verfiyClientToAgent (String agentUsername, Integer clientId) {
		long relatedClients = userRepo.isAgentClientRelated(agentUsername, clientId);
		return relatedClients > 0;
	}
	
	public ClientDetailsResponse getClientDetails (Integer clientId) {
		if (clientId < 0)
			return null;
		
		try {
			List<Map<String, Object>> clientDetailsMapDB = userRepo.getUserFullInfoById(clientId);
			List<Trip> clientTripList = tripRepo.findTripByClientId(clientId);
			if (clientTripList == null)
				return null;
			
			if (clientDetailsMapDB.size() < 1)
				return null;
			
			Map<String, Object> clientDetailMap = clientDetailsMapDB.get(0);
			if (!clientDetailMap.get("role").equals("CLIENT"))
				return null;
			
			Trip[] clientTrips = clientTripList.toArray(new Trip[0]);
			ClientDetailsResponse response = new ClientDetailsResponse(clientTrips, clientDetailMap);	
			return response;	
		}catch(Exception e) {
			return null;
		}
	}

	public List<Map<String, Object>> getClients (String username) {
		if (username == null)
			return null;
		
		return userRepo.getClientsForAgents(username);
	}
	
	public List<Map<String, Object>> getClients () {
		return userRepo.getAllClients();
	}
	
//	public List<Map<String, Object>> getClients (String username) {
//		return userRepo.getClientsForAgents(username);
//	}
}
