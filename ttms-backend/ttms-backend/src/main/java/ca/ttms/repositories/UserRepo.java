package ca.ttms.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ca.ttms.beans.User;
import ca.ttms.beans.details.UserFullDetails;

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
	List<String> findUserWithSimilarUsernames(@Param("un") String usernameExtract);

	@Query(value =
			"""
			SELECT p.firstname AS firstname, p.lastname AS lastname,
			u.id AS id, u.username AS username FROM\s
			Person p INNER JOIN User u\s
			ON p.id = u.person.id\s	
			""")
	List<Map<String, Object>> getAllAgents();	

	@Transactional
	@Modifying
	@Query("""
			UPDATE User\s
			SET password = :pass\s
			WHERE id = :id\s
			""")
	Integer updatePasswordById(@Param("id") Integer id, @Param("pass") String pass);
	
	@Query(nativeQuery = true, value =
			"""
			SELECT u.id AS id, u.username AS username,\s
			c.email AS email, c.primary_phone_number AS primaryPhoneNumber, c.secondary_phone_number AS secondaryPhoneNumber,\s
			p.firstname AS firstname, p.lastname AS lastname, p.birth_date AS birthDate,\s
			a.address_line AS addressLine, a.postal_code AS postalCode, a.city AS city, a.province AS province, a.country AS country\s
			FROM _user u JOIN person p ON u.person_id = p.id\s
			JOIN contact c ON c.person_id = p.id\s
			JOIN address_person ap ON ap.person_id = p.id\s
			JOIN _address a ON ap.address_id = a.id\s
			WHERE u.username = :un\s
	        """)
	List<Map<String,Object>> getUserFullInfo(@Param("un") String username);
	
	Optional<User> findByUsername(String username);
	
}
