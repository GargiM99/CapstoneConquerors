package ca.ttms.beans.details;

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
public class EditTripDetails {
	private TripDetails tripDetails;
	private EventDetails[] eventDetails;
	
	public boolean verifyDetails() {
		if (!this.tripDetails.VerifyTripDetails())
			return false;
		
		for (EventDetails eventDetail: eventDetails) {
			if (!eventDetail.VerifyDetails())
				return false;
		}
		return true;
	}
}


