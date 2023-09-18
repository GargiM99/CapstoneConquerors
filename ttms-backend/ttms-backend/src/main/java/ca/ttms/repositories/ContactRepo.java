package ca.ttms.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ca.ttms.beans.Contact;

/**
 * Repository class for Contact class
 * 
 * @author Hamza
 * date: 2023/03/22
 */

public interface ContactRepo extends JpaRepository<Contact, Integer>{

	@Transactional
	@Modifying
	@Query("""
			UPDATE Contact c
			SET c.email = :em,
			c.primaryPhoneNumber = :ppn,
			c.secondaryPhoneNumber = :spn
			WHERE c.person.id = (SELECT u.person.id FROM User u WHERE u.username = :un)
			""")
	Integer updateContactByUsername(
			@Param("un") String username, @Param("em") String email,
			@Param("ppn") String primaryPhoneNumber, @Param("spn") String secondaryPhoneNumber);
	
	@Transactional
	@Modifying
	@Query("""
			UPDATE Contact c
			SET c.email = :em,
			c.primaryPhoneNumber = :ppn,
			c.secondaryPhoneNumber = :spn
			WHERE c.person.id = (SELECT u.person.id FROM User u WHERE u.id = :id)
			""")
	Integer updateContactByUserId(
			@Param("id") Integer username, @Param("em") String email,
			@Param("ppn") String primaryPhoneNumber, @Param("spn") String secondaryPhoneNumber);
}
