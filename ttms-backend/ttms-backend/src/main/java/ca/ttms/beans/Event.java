package ca.ttms.beans;

import java.time.LocalDate;
import java.util.Date;

import ca.ttms.beans.details.EventDetails;
import ca.ttms.beans.enums.EventStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_event")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name = "event_name", nullable = false)
	private String eventName; 
	
	@Column (name = "event_date", nullable = false)
	private LocalDate eventDate;
	
	@Column (name = "event_description")
	private String eventDescription;
	
	@Enumerated(EnumType.STRING)
	private EventStatus status;
	
	@ManyToOne
	@JoinColumn(name = "trip_id")
    private Trip trip;

	public Event(EventDetails eventDetails) {
		this.id = eventDetails.getId();
		this.eventName = eventDetails.getEventName();
		this.eventDate = eventDetails.getEventDate();
		this.status = eventDetails.getStatus();
	}
	
	public Event(EventDetails eventDetails, Trip eventTrip) {
		this.id = eventDetails.getId();
		this.eventName = eventDetails.getEventName();
		this.eventDate = eventDetails.getEventDate();
		this.eventDescription = eventDetails.getEventDescription();
		this.status = eventDetails.getStatus();
		this.trip = eventTrip;
	}
}
