package ca.ttms.beans.response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.ttms.beans.ClientNote;
import ca.ttms.beans.Trip;
import ca.ttms.beans.details.ClientNoteDetails;
import ca.ttms.beans.details.TripDetails;
import ca.ttms.beans.dto.ClientNoteDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDetailsResponse {
	private TripDetails[] tripDetails;
	private ClientNoteDetails[] clientNotes;
	
	private Map<String, Object> user;
	private Map<String, Object> contact;
	private Map<String, Object> person;
	
	
	public ClientDetailsResponse(Trip[] trips, Map<String,Object> clientDetailsMap, List<ClientNoteDTO> clientNotes) {
		user = new HashMap<>();
		contact = new HashMap<>();
		person = new HashMap<>();
		
	    user.put("id", clientDetailsMap.get("id"));
	    user.put("username", clientDetailsMap.get("username"));
	    user.put("role", clientDetailsMap.get("role"));

	    person.put("firstname", clientDetailsMap.get("firstname"));
	    person.put("lastname", clientDetailsMap.get("lastname"));

	    contact.put("email", clientDetailsMap.get("email"));
	    contact.put("primaryPhoneNumber", clientDetailsMap.get("primaryPhoneNumber"));
	    contact.put("secondaryPhoneNumber", clientDetailsMap.get("secondaryPhoneNumber"));
		
	    if (trips.length > 0)
	    	this.tripDetails = Arrays.stream(trips)
				.map(trip -> TripDetails.builder()
				.tripName(trip.getTripName())
				.tripStartDate(trip.getTripStartDate())
				.tripEndDate(trip.getTripEndDate())
				.id(trip.getId())
				.status(trip.getStatus())
				.clientId(trip.getUsers().getId())
				.build())
				.toArray(TripDetails[]::new);
	    
	    if (clientNotes.size() > 0)
	    	this.clientNotes = clientNotes.stream()
	        		.map((ClientNoteDTO note) -> ClientNoteDetails.builder()
	                .id(note.getId())
	                .noteBody(note.getNoteBody())
	                .noteTitle(note.getNoteTitle())
	                .tripId(note.getTripId())
	                .clientId(note.getClientId())
	                .clientUserName((String)clientDetailsMap.get("username"))
	                .clientFirstName((String)clientDetailsMap.get("firstname"))
	                .clientLastName((String)clientDetailsMap.get("lastname"))
	                .build())
	        		.toArray(ClientNoteDetails[]::new);
	    		
	    		
	}
}
