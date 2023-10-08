package ca.ttms.beans.response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ca.ttms.beans.Trip;
import ca.ttms.beans.details.TripDetails;
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
	
	private Map<String, Object> user;
	private Map<String, Object> contact;
	private Map<String, Object> address;
	private Map<String, Object> person;
	
	public ClientDetailsResponse(Trip[] trips, Map<String,Object> clientDetailsMap) {
		user = new HashMap<>();
		contact = new HashMap<>();
		address = new HashMap<>();
		person = new HashMap<>();
		
	    user.put("id", clientDetailsMap.get("id"));
	    user.put("username", clientDetailsMap.get("username"));
	    user.put("role", clientDetailsMap.get("role"));

	    person.put("firstname", clientDetailsMap.get("firstname"));
	    person.put("lastname", clientDetailsMap.get("lastname"));
	    person.put("birthDate", clientDetailsMap.get("birthDate"));

	    address.put("addressLine", clientDetailsMap.get("addressLine"));
	    address.put("postalCode", clientDetailsMap.get("postalCode"));
	    address.put("city", clientDetailsMap.get("city"));
	    address.put("province", clientDetailsMap.get("province"));
	    address.put("country", clientDetailsMap.get("country"));

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
	}
}
