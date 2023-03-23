package ca.ttms.beans.details;

import java.time.LocalDate;
import java.util.Date;

import ca.ttms.beans.Address;
import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for registering details of users
 * 
 * @author Hamza  
 * date: 2023/03/08 
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDetails {
	
	private Person person;
	private Contact contact;
	private Address address;	
	
	private final int MIN_NAME_LENGTH = 0;
	private final int MIN_ADDRESS_LENGTH = 3;
	private final LocalDate MIN_DATE = LocalDate.parse("1900-01-01");
	private final int MIN_EMAIL_LENGTH = 3;
	private final int MIN_PHONE_LENGTH = 3;
	
	//TODO: remove else
	public boolean verifyDetails() {
		if (this.person.getFirstname().isBlank() || this.person.getFirstname().replaceAll("[^a-zA-Z]", "").length() <= MIN_NAME_LENGTH)
			return false;
		
		else if (this.person.getLastname().isBlank() || this.person.getFirstname().replaceAll("[^a-zA-Z]", "").length() <= MIN_NAME_LENGTH)
			return false;
		
		else if (this.person.getBirthDate() == null || this.person.getBirthDate().compareTo(MIN_DATE) < 0 )
			return false;
		
		else if (this.address.getAddressLine().isBlank() || this.address.getAddressLine().length() <= MIN_ADDRESS_LENGTH)
			return false;
		
		else if (this.address.getPostalCode().isBlank() || this.address.getPostalCode().length() <= MIN_ADDRESS_LENGTH)
			return false;
		
		else if (this.address.getCity().isBlank() || this.address.getCity().length() <= MIN_ADDRESS_LENGTH)
			return false;
		
		else if (this.address.getCountry().isBlank() || this.address.getCountry().length() <= MIN_ADDRESS_LENGTH)
			return false;
		
		else if (this.address.getProvince().isBlank() || this.address.getProvince().length() <= MIN_ADDRESS_LENGTH)
			return false;
		
		else if (this.contact.getEmail().isBlank() || this.contact.getEmail().length() <= MIN_EMAIL_LENGTH)
			return false;
			
		else if (this.contact.getPrimaryPhoneNumber().isBlank() || this.contact.getPrimaryPhoneNumber().length() <= MIN_PHONE_LENGTH)
			return false;
		
		else if (this.contact.getSecondaryPhoneNumber() != null && this.contact.getSecondaryPhoneNumber().length() <= MIN_PHONE_LENGTH)
			return false;
			
		return true;
	}
}
