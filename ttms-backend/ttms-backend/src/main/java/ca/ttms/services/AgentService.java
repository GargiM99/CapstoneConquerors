package ca.ttms.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.qpid.proton.engine.Collector;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.ttms.beans.Address;
import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserEditDetails;
import ca.ttms.beans.details.UserFullDetails;
import ca.ttms.beans.details.UserPromoteDetails;
import ca.ttms.beans.dto.ClientDTO;
import ca.ttms.beans.dto.EventDTO;
import ca.ttms.beans.dto.ScheduleDTO;
import ca.ttms.beans.dto.TripDTO;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.response.ResetPasswordResponse;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TripRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

/**
 * Service for viewing and updating agents
 * 
 * @author Hamza
 * date: 2023-03-25
 */

@Service
@RequiredArgsConstructor
public class AgentService {

	private final UserRepo userRepo;
	private final AddressRepo addressRepo;
	private final PersonRepo personRepo;
	private final ContactRepo contactRepo;
	private final TripRepo tripRepo;
	 
	private final AuthenticationService authService;
	private final PasswordEncoder passwordEncoder;
	
	/**
	 * Gets all the agents and there firstname, lastname, username, id
	 * 
	 * @return
	 */
	public Map<String, Object>[] getAgents() {
		List<Map<String, Object>> agentList = userRepo.getAllAgents();
		Map<String, Object>[] agentArray = agentList.toArray(new Map[agentList.size()]);
		return agentArray;
	}
	
	public UserFullDetails getAgentDetails (int id) {
		if (id < 0)
			return null;
		
		try {
			List<Map<String, Object>> userDetailsMapDB = userRepo.getUserFullInfoById(id);
			
			if (userDetailsMapDB.size() < 1)
				return null;
			
			UserFullDetails userDetails = new UserFullDetails();
			userDetails.mapDetailsFromRepo(userDetailsMapDB.get(0));
			
			return userDetails;
		}catch(Exception e) {
			return null;
		}
	}

	public boolean updateAgentProfile(UserEditDetails updateProfile, int id) {
		if (id <= 0)
			return false;
		
		if (!updateProfile.verifyDetails())
			return false;
		
		try {
			Address updateAddress = updateProfile.getAddress();
			Contact updateContact = updateProfile.getContact();
			Person updatePerson = updateProfile.getPerson();
			
			addressRepo.updateAddressByUserId(id, updateAddress.getAddressLine(), updateAddress.getPostalCode(),
										updateAddress.getCity(), updateAddress.getProvince(), updateAddress.getCountry());
			
			contactRepo.updateContactByUserId(id, updateContact.getEmail(), 
										updateContact.getPrimaryPhoneNumber(), updateContact.getSecondaryPhoneNumber());
			
			personRepo.updatePersonByUserId(id, updatePerson.getFirstname(), updatePerson.getLastname(), updatePerson.getBirthDate());

			return true;
		}catch(Exception e) {
			return false;
		}

	}

	public ResetPasswordResponse resetAgentPassword(int id) {
		if (id <= 0)
			return null;
		
		String tempPassword = authService.generateTempPassword();
		String encodedPassword = passwordEncoder.encode(tempPassword);
		
		try {
			userRepo.updatePasswordById(id, encodedPassword);
			return new ResetPasswordResponse(tempPassword);
		} catch(Exception e) {
			return null;
		}
		
	}

	public boolean promoteAgent (UserPromoteDetails details, int id) {
		if (id <= 0)
			return false;
		
		if (!(details.getRole() == Roles.ADMIN || details.getRole() == Roles.AGENT))
			return false;
		
		try {
			userRepo.updateRoleById(id, details.getRole());
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean verifyAgentToUsername (String username, int id) {
		Optional<User> checkAgentOpt = userRepo.findById(id);
		
		if (checkAgentOpt.isEmpty())
			return false;
		
		return checkAgentOpt.get().getUsername().equals(username);
	}

	public List<ScheduleDTO> getAgentSchedule(Integer agentId) {
	    List<ClientDTO> clientDtos = userRepo.getClientForAgentsById(agentId);
	    List<Integer> clientIds = clientDtos.stream().map(ClientDTO::getId).collect(Collectors.toList());
	    List<Map<String, Object>> tripList = tripRepo.getTripsAndEventsForAgents(clientIds);

	    // Group events by tripId
	    Map<Integer, List<EventDTO>> eventsByTripId = tripList.stream()
	            .collect(Collectors.groupingBy(tripMap -> (Integer) tripMap.get("tripId"),
	                    Collectors.mapping(eventMap -> new EventDTO(eventMap), Collectors.toList())));

	    // Group trips by clientId
	    Map<Integer, List<TripDTO>> tripsByClientId = tripList.stream()
	            .collect(Collectors.groupingBy(tripMap -> (Integer) tripMap.get("clientId"),
	                    Collectors.mapping(tripMap -> new TripDTO(tripMap), Collectors.toList())));

	    // Create ScheduleDTO objects with grouped trips and events
	    return clientDtos.stream()
	            .map(clientDTO -> {
	                ScheduleDTO scheduleDTO = new ScheduleDTO(clientDTO, tripsByClientId.getOrDefault(clientDTO.getId(), Collections.emptyList()));

	                if (scheduleDTO.getTrips() != null) {
	                    scheduleDTO.getTrips().forEach(tripDTO -> {
	                        List<EventDTO> events = eventsByTripId.getOrDefault(tripDTO.getTripId(), Collections.emptyList());
	                        tripDTO.setEvents(events);
	                    });
	                }

	                return scheduleDTO;
	            })
	            .collect(Collectors.toList());
	}


}
