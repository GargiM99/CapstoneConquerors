package ca.ttms.repositories;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ca.ttms.beans.Trip;
import ca.ttms.beans.User;
import ca.ttms.beans.details.UserFullDetails;
import ca.ttms.beans.dto.ClientDTO;
import ca.ttms.beans.dto.ScheduleDTO;
import ca.ttms.beans.enums.Roles;

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
			WHERE u.role IN ('AGENT','ADMIN') 	
			""")
	List<Map<String, Object>> getAllAgents();	
	
	@Query(value =
			"""
			SELECT p.firstname AS firstname, p.lastname AS lastname,
			u.id AS id, u.username AS username FROM\s
			Person p INNER JOIN User u\s
			ON p.id = u.person.id\s	
			WHERE u.agentUser.id = 
			(SELECT id FROM User WHERE username = :un)
			AND u.role = 'CLIENT'
			""")
	List<Map<String, Object>> getClientsForAgents(@Param("un") String agentUsername);
	
	@Query(value =
		    """
		    SELECT new ca.ttms.beans.dto.ClientDTO(u.id, u.username, p.firstname, p.lastname)
		    FROM Person p
		    INNER JOIN User u ON p.id = u.person.id
		    WHERE u.agentUser.id = :aid AND u.role = 'CLIENT'
		    """)
	List<ClientDTO> getClientForAgentsById(@Param("aid") Integer agentId);

	@Query(value =
			"""
			SELECT p.firstname AS firstname, p.lastname AS lastname,
			u.id AS id, u.username AS username FROM\s
			Person p INNER JOIN User u\s
			ON p.id = u.person.id\s	
			AND u.role = 'CLIENT'
			""")
	List<Map<String, Object>> getAllClients();

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
			SELECT u.id AS id, u.username AS username, u.role AS role,\s
			c.email AS email, c.primary_phone_number AS primaryPhoneNumber, c.secondary_phone_number AS secondaryPhoneNumber,\s
			p.firstname AS firstname, p.lastname AS lastname\s
			FROM _user u JOIN person p ON u.person_id = p.id\s
			JOIN contact c ON c.person_id = p.id\s
			WHERE u.username = :un\s
	        """)
	List<Map<String,Object>> getUserFullInfo(@Param("un") String username);
	
	@Query(nativeQuery = true, value =
			"""
			SELECT u.id AS id, u.username AS username, u.role AS role,\s
			c.email AS email, c.primary_phone_number AS primaryPhoneNumber, c.secondary_phone_number AS secondaryPhoneNumber,\s
			p.firstname AS firstname, p.lastname AS lastname\s
			FROM _user u JOIN person p ON u.person_id = p.id\s
			JOIN contact c ON c.person_id = p.id\s
			WHERE u.id = :id\s
	        """)
	List<Map<String,Object>> getUserFullInfoById(@Param("id") Integer id);

	Optional<User> findByUsername(String username);
	
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.role = :ro WHERE u.id = :id")
    void updateRoleById(@Param("id") Integer userId, @Param("ro") Roles newRole);

    @Query(value = """
    		SELECT COUNT(u)
    		FROM User u JOIN u.agentUser a
    		ON u.agentUser.id = a.id
    		WHERE a.username = :un
    		AND u.id = :cid
    		""")
    long isAgentClientRelated(@Param("un") String agentUsername, @Param("cid") Integer clientId);
    
    @Query(nativeQuery = true, value = """
            SELECT
                c.id as clientId, c.username, p.firstname, p.lastname,
                t.id as tripId, t.trip_name, t.trip_type, t.trip_start_date, t.trip_end_date,
                e.id as eventId, e.event_name, e.event_date, e.event_description
            FROM _user u
            JOIN _user c ON u.id = c.agent_id
            JOIN trip t ON c.id = t.user_id
            LEFT JOIN _event e ON t.id = e.trip_id
            JOIN person p ON c.person_id = p.id
            WHERE u.id = :uid
            """)
    List<Map<String, Object>> getAgentSchedule(@Param("uid") Integer userId);
}
