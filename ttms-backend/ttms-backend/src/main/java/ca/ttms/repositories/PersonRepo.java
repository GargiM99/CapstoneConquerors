package ca.ttms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.ttms.beans.Person;

/**
 * Repository class for Person class
 * 
 * @author Hamza
 * date: 2023/03/22
 */

public interface PersonRepo extends JpaRepository<Person, Integer>{

	@Query(value =
			""" 
			SELECT COUNT(*)\s
			FROM Contact c JOIN Person p\s 
			ON c.person.id = p.id\s
			WHERE p.firstname = :fn\s
			AND p.lastname = :ln\s
			AND (c.email = :email\s
			OR c.primaryPhoneNumber = :ppn)
			""")
	Integer countSimilarPeople(
			@Param("fn") String firstname, @Param("ln") String lastname,
			@Param("email") String email, @Param("ppn") String primaryPhoneNumber);
	
}
