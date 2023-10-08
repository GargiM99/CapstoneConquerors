package ca.ttms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.ttms.beans.Trip;

public interface TripRepo extends JpaRepository<Trip, Integer>{	
	
    @Query(value = """
    		SELECT COUNT(t)
    		FROM Trip t JOIN t.users u
    		ON t.users.id = u.id
    		JOIN u.agentUser a
    		ON u.agentUser.id = a.id
    		WHERE a.username = :un
    		AND t.id = :tid
    		""")
    long isAgentTripRelated(@Param("un") String agentUsername, @Param("tid") Integer tripId);
    
    @Query(value = """
    		SELECT t
    		FROM Trip t JOIN t.users u
    		ON t.users.id = u.id
    		WHERE u.id = :cid
    		""")
    List<Trip> findTripByClientId(@Param("cid") Integer clientId);
}
