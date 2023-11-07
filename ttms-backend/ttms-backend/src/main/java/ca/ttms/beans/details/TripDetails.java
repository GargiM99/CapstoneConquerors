package ca.ttms.beans.details;

import java.time.LocalDate;
import java.util.Date;

import ca.ttms.beans.Trip;
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
	
	public TripDetails(Trip trip) {
		this.id = trip.getId();
		this.tripName = trip.getTripName();
		this.tripStartDate = trip.getTripStartDate();
		this.tripEndDate = trip.getTripEndDate();
		this.tripType = trip.getTripType();
		this.status = trip.getStatus();
		this.clientId = trip.getUsers().getId();
	}

	public boolean VerifyTripDetails() {
		LocalDate beforeDate = LocalDate.of(1900, 1, 1);
		if (this.id <= 0)
			return false;
		if (this.tripName == null || this.tripName.equals(""))
			return false;
		if (this.tripStartDate == null || this.tripStartDate.isBefore(beforeDate))
			return false;
		if (this.tripEndDate == null || this.tripEndDate.isBefore(tripStartDate))
			return false;
		if (this.status == null)
			return false;
		if (this.clientId <= 0)
			return false;
		
		return true;
	}
}
