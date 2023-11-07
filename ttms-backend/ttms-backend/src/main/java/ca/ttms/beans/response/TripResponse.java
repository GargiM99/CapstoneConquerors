package ca.ttms.beans.response;

import java.util.Arrays;

import ca.ttms.beans.Event;
import ca.ttms.beans.Trip;
import ca.ttms.beans.details.EventDetails;
import ca.ttms.beans.details.TripDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {
	private TripDetails tripDetails;
	private EventDetails[] eventDetails;
	
	public TripResponse(Trip trip, Event[] events) {
		this.tripDetails = new TripDetails(trip.getId(), trip.getTripName(), trip.getTripStartDate(),
										trip.getTripEndDate(), trip.getTripType(), trip.getStatus(), trip.getUsers().getId());
		
		this.eventDetails = Arrays.stream(events)
			    .map(event -> EventDetails.builder()
			        .eventName(event.getEventName())
			        .eventDate(event.getEventDate())
			        .eventDescription(event.getEventDescription())
			        .id(event.getId())
			        .status(event.getStatus())
			        .tripId(event.getTrip().getId())
			        .build())
			    .toArray(EventDetails[]::new);
	}
}


