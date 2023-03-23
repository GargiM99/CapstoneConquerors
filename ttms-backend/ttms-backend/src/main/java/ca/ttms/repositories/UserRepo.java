package ca.ttms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.ttms.beans.Token;
import ca.ttms.beans.User;

/**
 * Repository class for User class
 * 
 * @author Hamza & Akshat
 * date: 2023/03/07
 */

public interface UserRepo extends JpaRepository<User, Integer>{
	
	@Query(value =
			""" 
			SELECT u.username\s
			FROM User u JOIN Person p\s 
			ON u.person.id = p.id\s
			WHERE p.firstname LIKE :fn%\s
			AND p.lastname LIKE :ln%\s
			""")
			List<String> findUserWithSimilarName(
					@Param("fn") String firstNameExtract, @Param("ln") String lastNameExtract);
	
	@Query(value =
			""" 
			SELECT u.username\s
			FROM User u\s 
			WHERE u.username LIKE :un%\s
			""")
			List<String> findUserWithSimilarNames(@Param("un") String usernameExtract);
	
	Optional<User> findByUsername(String username);
	
}
