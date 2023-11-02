package ca.ttms.beans.details;

import java.time.LocalDate;
import java.util.Date;

import ca.ttms.beans.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDetails {
	private Integer id;
	private String tripName; 
	private LocalDate tripStartDate;
	private LocalDate tripEndDate;
	private String tripType;
	private TripStatus status;
	private Integer clientId;
}
