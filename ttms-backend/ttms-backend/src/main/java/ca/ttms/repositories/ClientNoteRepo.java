package ca.ttms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.ttms.beans.ClientNote;
import ca.ttms.beans.dto.ClientNoteDTO;


public interface ClientNoteRepo extends JpaRepository<ClientNote, Integer>{
	
	@Query(value =
		    """
		    SELECT new ca.ttms.beans.dto.ClientNoteDTO(cn.id, cn.noteTitle, cn.noteBody, u.id, t.id)
		    FROM ClientNote cn
		    INNER JOIN User u ON cn.client.id = u.id
		    LEFT JOIN Trip t ON cn.trip.id = t.id
		    WHERE u.id = :cid AND u.role = 'CLIENT'
		    """)
	List<ClientNoteDTO> getNotesForClientById(@Param("cid") Integer clientId);

	@Modifying
	@Query(nativeQuery = true, value =
	        """
	        INSERT INTO client_note (note_title, note_body, client_id, trip_id)
	        VALUES (:noteTitle, :noteBody, :clientId, :tripId);
	        """)
	void insertClientNote(
	        @Param("noteTitle") String noteTitle,
	        @Param("noteBody") String noteBody,
	        @Param("clientId") Integer clientId,
	        @Param("tripId") Integer tripId);

	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM client_note WHERE client_id = :cid")
	void deleteClientNoteByClientId(@Param("cid") Integer clientId);
}
