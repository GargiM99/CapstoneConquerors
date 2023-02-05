package com.ttms.repositories;


import org.springframework.data.repository.CrudRepository;

import com.ttms.backend.beans.Agent;

public interface AgentRepo extends CrudRepository<Agent, Long> {}
