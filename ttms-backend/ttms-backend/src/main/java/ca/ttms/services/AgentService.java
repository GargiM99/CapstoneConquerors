package ca.ttms.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
			Contact updateContact = updateProfile.getContact();
			Person updatePerson = updateProfile.getPerson();
			
			contactRepo.updateContactByUserId(id, updateContact.getEmail(), 
										updateContact.getPrimaryPhoneNumber(), updateContact.getSecondaryPhoneNumber());
			
			personRepo.updatePersonByUserId(id, updatePerson.getFirstname(), updatePerson.getLastname());

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

	    Map<Integer, List<EventDTO>> eventsByTripId = new HashMap<>();
	    tripList.forEach(tripMap -> 
	        eventsByTripId.computeIfAbsent((Integer) tripMap.get("tripId"), key -> new ArrayList<>())
	                     .add(new EventDTO(tripMap)));


	    Map<Integer, List<TripDTO>> tripsByClientId = tripList.stream()
	            .collect(Collectors.groupingBy(
	                    tripMap -> (Integer) tripMap.get("clientId"),
	                    Collectors.collectingAndThen(
	                            Collectors.mapping(
	                                    tripMap -> new TripDTO(tripMap, eventsByTripId.getOrDefault(tripMap.get("tripId"), Collections.emptyList())),
	                                    Collectors.toList()
	                            ),
	                            trips -> new ArrayList<>(new HashSet<>(trips)) 
	                    )
	            ));



	    List<ScheduleDTO> scheduleDto = new ArrayList<>();
	    clientDtos.forEach(clientDTO -> {
	        ScheduleDTO scheduleDTO = new ScheduleDTO(clientDTO, tripsByClientId.getOrDefault(clientDTO.getId(), Collections.emptyList()));

	        scheduleDTO.getTrips().forEach(tripDTO ->
	            tripDTO.setEvents(eventsByTripId.getOrDefault(tripDTO.getTripId(), Collections.emptyList()))
	        );

	        scheduleDto.add(scheduleDTO);
	    });

	    
	    return scheduleDto;
	}


}
