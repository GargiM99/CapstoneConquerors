package com.ttms.backend;

import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ttms.backend.beans.Agent;
import com.ttms.repositories.AgentRepo;

@SpringBootApplication
@EnableJpaRepositories("com.ttms.repositories")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(AgentRepo agentRepo) {
		return args -> {
			Stream.of("Hamza", "Akshat", "Gargi", "Zunaira").forEach(name ->{
				Agent agent = new Agent(name, name.toLowerCase() + "@smail.com");
				
				agentRepo.save(agent);
			});
			
			agentRepo.findAll().forEach(System.out::println);
		};
	}

}
