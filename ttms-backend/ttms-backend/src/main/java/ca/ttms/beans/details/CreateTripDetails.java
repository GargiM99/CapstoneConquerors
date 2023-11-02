package ca.ttms.beans.details;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTripDetails {
	private LocalDate tripEndDate;
	private String tripName;
	private String tripType;
	private Integer clientId;
	
	private final int MIN_NAME_LENGTH = 3;
	private final LocalDate CURRENT_DATE = LocalDate.now();
	
	public boolean verifyTripDetails() {
		if (this.getTripEndDate() == null || this.getTripEndDate().compareTo(CURRENT_DATE) < 0 )
			return false;
		if (this.getTripName() == null || this.getTripName().length() <= MIN_NAME_LENGTH)
			return false;
		if (this.getClientId() <= 0)
			return false;
		
		return true;
	}
}
