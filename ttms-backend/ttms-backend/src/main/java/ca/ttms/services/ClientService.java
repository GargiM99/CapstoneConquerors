package ca.ttms.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.ttms.beans.ClientNote;
import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.Trip;
import ca.ttms.beans.User;
import ca.ttms.beans.details.BasicUsersDetails;
import ca.ttms.beans.details.ClientNoteDetails;
import ca.ttms.beans.details.ModifyClientNoteDetails;
import ca.ttms.beans.details.UserAuthenticationDetails;
import ca.ttms.beans.details.UserEditDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.dto.ClientNoteDTO;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.response.BasicClientResponse;
import ca.ttms.beans.response.ClientDetailsResponse;
import ca.ttms.repositories.ClientNoteRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.TripRepo;
import ca.ttms.repositories.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
	
	private final UserRepo userRepo;
	private final PersonRepo personRepo;
	private final ContactRepo contactRepo;
	private final TripRepo tripRepo;
	private final ClientNoteRepo clientNoteRepo;
	 
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
		
		userDetails.getContact().setPerson(savedPerson);
		
		User savedUser = userRepo.save(newUser);
		contactRepo.save(userDetails.getContact());
		
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
			List<ClientNoteDTO> clientNoteList = clientNoteRepo.getNotesForClientById(clientId);
			
			
			if (clientTripList == null)
				return null;
			
			if (clientDetailsMapDB.size() < 1)
				return null;
			
			Map<String, Object> clientDetailMap = clientDetailsMapDB.get(0);
			if (!clientDetailMap.get("role").equals("CLIENT"))
				return null;
			
			Trip[] clientTrips = clientTripList.toArray(new Trip[0]);
			ClientDetailsResponse response = new ClientDetailsResponse(clientTrips, clientDetailMap, clientNoteList);	
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
	
	public boolean editClientDetails(UserEditDetails editDetails, int id ) {
		
		if(editDetails == null || !editDetails.verifyDetails())
			return false;
		
		if(id <= 0)
			return false;
		
		try{
			Contact updateContact = editDetails.getContact();
			Person updatePerson = editDetails.getPerson();
			
			contactRepo.updateContactByUserId(id, updateContact.getEmail(), 
										updateContact.getPrimaryPhoneNumber(), updateContact.getSecondaryPhoneNumber());
			
			personRepo.updatePersonByUserId(id, updatePerson.getFirstname(), updatePerson.getLastname());
			
													
			return true;
			
		}catch(Exception e) {
			return false;
		}
	}

	@Transactional
    public boolean modifyClientNotes(List<ModifyClientNoteDetails> noteDetails, int clientId) {
        try {
        	clientNoteRepo.deleteClientNoteByClientId(clientId);

            for (ModifyClientNoteDetails noteDetail : noteDetails) {
                if (noteDetail == null || !noteDetail.verifyNoteDetails()) {
                    throw new IllegalArgumentException("Invalid note details");
                }
                clientNoteRepo.insertClientNote(
                        noteDetail.getNoteTitle(),
                        noteDetail.getNoteBody(),
                        clientId,
                        noteDetail.getTripId()
                );
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
