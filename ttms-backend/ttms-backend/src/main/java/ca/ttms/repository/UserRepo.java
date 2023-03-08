package ca.ttms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.ttms.beans.User;

/**
 * @author Hamza & Akshat
 * date: 2023/03/07
 * description: Repository class for User class
 */

public interface UserRepo extends JpaRepository<User, Integer>{
	
	Optional<User> findByUsername(String username);
	
}
