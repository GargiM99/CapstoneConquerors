package ca.ttms.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional
	@Modifying
	@Query("""
			UPDATE Person p
			SET p.firstname = :fn,
			p.lastname = :ln
			WHERE p.id = (SELECT u.person.id FROM User u WHERE u.username = :un)
			""")
	Integer updatePersonByUsername(
			@Param("un") String username, @Param("fn") String firstname, @Param("ln") String lastname);
	
	@Transactional
	@Modifying
	@Query("""
			UPDATE Person p
			SET p.firstname = :fn,
			p.lastname = :ln
			WHERE p.id = (SELECT u.person.id FROM User u WHERE u.id = :id)
			""")
	Integer updatePersonByUserId(
			@Param("id") Integer id, @Param("fn") String firstname, @Param("ln") String lastname);

}
