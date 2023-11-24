package ca.ttms.beans.details;

import java.time.LocalDate;

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
	
	private final int MIN_NAME_LENGTH = 0;
	private final int MIN_EMAIL_LENGTH = 3;
	private final int MIN_PHONE_LENGTH = 3;
	
	public boolean verifyDetails() {
		if (this.person.getFirstname() == null || this.person.getFirstname().replaceAll("[^a-zA-Z]", "").length() <= MIN_NAME_LENGTH)
			return false;
		
		if (this.person.getLastname() == null || this.person.getFirstname().replaceAll("[^a-zA-Z]", "").length() <= MIN_NAME_LENGTH)
			return false;
			
		if (this.contact.getPrimaryPhoneNumber() == null || this.contact.getPrimaryPhoneNumber().length() <= MIN_PHONE_LENGTH)
			return false;
		
		if (this.contact.getSecondaryPhoneNumber() != null && !this.contact.getSecondaryPhoneNumber().isBlank()
				&& this.contact.getSecondaryPhoneNumber().length() <= MIN_PHONE_LENGTH)
			return false;
			
		return true;
	}
}
