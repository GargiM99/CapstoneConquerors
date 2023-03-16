package ca.ttms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.ttms.beans.User;

/**
 * Repository class for User class
 * 
 * @author Hamza & Akshat
 * date: 2023/03/07
 */

public interface UserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findByUsername(String username);
	
}
