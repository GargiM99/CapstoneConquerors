package ca.ttms.beans.details;

import java.time.LocalDate;
import java.util.Date;

import ca.ttms.beans.Event;
import ca.ttms.beans.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDetails {
	private Integer id;
	private String eventName; 
	private String eventDescription;
	private LocalDate eventDate;
	private EventStatus status;
	private Integer tripId;
	
	public EventDetails(Event event) {
		this.id = event.getId();
		this.eventName = event.getEventName();
		this.eventDescription = event.getEventDescription();
		this.eventDate = event.getEventDate();
		this.status = event.getStatus();
		this.tripId = event.getTrip().getId();
	}

	public boolean VerifyDetails() {
		LocalDate beforeDate = LocalDate.of(1900, 1, 1);
		if (this.id <= 0)
			return false;
		if (this.eventName == null || this.eventName.equals(""))
			return false;
		if (this.eventDate == null || this.eventDate.isBefore(beforeDate))
			return false;
		if (this.status == null)
			return false;
		if (this.tripId <= 0)
			return false;
		
		return true;
	}
}