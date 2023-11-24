package ca.ttms.beans.details;

import java.time.LocalDate;
import java.util.List;

import ca.ttms.beans.ClientNote;
import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserEditDetails {
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
		
		if (this.contact.getEmail() == null || this.contact.getEmail().length() <= MIN_EMAIL_LENGTH)
			return false;
			
		if (this.contact.getPrimaryPhoneNumber() == null || this.contact.getPrimaryPhoneNumber().length() <= MIN_PHONE_LENGTH)
			return false;
		
		if (this.contact.getSecondaryPhoneNumber() != null && !this.contact.getSecondaryPhoneNumber().isBlank()
				&& this.contact.getSecondaryPhoneNumber().length() <= MIN_PHONE_LENGTH)
			return false;
			
		return true;
	}
}
