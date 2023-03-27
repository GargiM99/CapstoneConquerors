package ca.ttms.services;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ca.ttms.beans.details.BasicUsersDetails;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TokenRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

/**
 * Service for viewing and updating agents
 * @author Hamza
 * date: 2023-03-25
 */

@Service
@RequiredArgsConstructor
public class AgentService {

	private final UserRepo userRepo;
	
	/**
	 * Gets all the agents and there firstname, lastname, username, id
	 * @return
	 */
	public Map<String, Object>[] getAgents() {
		List<Map<String, Object>> agentList = userRepo.getAllAgents();
		Map<String, Object>[] agentArray = agentList.toArray(new Map[agentList.size()]);
		return agentArray;
	}
}
