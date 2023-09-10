package ca.ttms.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ca.ttms.beans.details.UserFullDetails;
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
}
