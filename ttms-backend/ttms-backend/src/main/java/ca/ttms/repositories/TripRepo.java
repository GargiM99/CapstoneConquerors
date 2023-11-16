package ca.ttms.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.ttms.beans.Event;
import ca.ttms.beans.Trip;
import ca.ttms.beans.dto.TripDTO;

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

    @Query(nativeQuery = true, value =
    	    """
    	    SELECT
    	        t.user_id as clientId, t.id as tripId, t.trip_name as tripName, t.trip_type as tripType,
    	        t.trip_start_date as tripStartDate, t.trip_end_date as tripEndDate,
    	        e.id as eventId, e.event_name as eventName, e.event_date as eventDate, e.event_description as eventDescription
    	    FROM trip t
    	    LEFT JOIN _event e ON t.id = e.trip_id
    	    WHERE t.user_id IN (:uids)
    	    """)
    List<Map<String, Object>> getTripsAndEventsForAgents(@Param("uids") List<Integer> agentIds);

}
