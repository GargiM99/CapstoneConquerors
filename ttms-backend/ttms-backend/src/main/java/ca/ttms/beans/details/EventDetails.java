package ca.ttms.beans.details;

import java.time.LocalDate;
import java.util.Date;

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
	private LocalDate eventDate;
	private EventStatus status;
	private Integer tripId;
}