package com.ttms.backend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ttms.backend.beans.Agent;
import com.ttms.repositories.AgentRepo;

@RestController
@CrossOrigin(origins = "http://25.68.193.152:4200")
public class AgentController {
	
	public AgentController(AgentRepo agentRepo) {
		this.agentRepo = agentRepo;
	}
	
	private final AgentRepo agentRepo;
	
	@GetMapping("/agent")
	public List<Agent> getAgent(){	    
		return (List<Agent>) agentRepo.findAll();
	}
	
	@PostMapping("/agent")
	void addAgent(@RequestBody Agent agent) {
		agentRepo.save(agent);
	}
}
