package ca.ttms.beans.details;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ca.ttms.beans.Contact;
import ca.ttms.beans.Person;
import ca.ttms.beans.User;
import ca.ttms.beans.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFullDetails {
	
	private Map<String, Object> user;
	private Map<String, Object> contact;
	private Map<String, Object> person;
	
	public void mapDetailsFromRepo(Map<String,Object> detailsMap) {
		
		user = new HashMap<>();
		contact = new HashMap<>();
		person = new HashMap<>();
		
	    user.put("id", detailsMap.get("id"));
	    user.put("username", detailsMap.get("username"));
	    user.put("role", detailsMap.get("role"));

	    person.put("firstname", detailsMap.get("firstname"));
	    person.put("lastname", detailsMap.get("lastname"));

	    contact.put("email", detailsMap.get("email"));
	    contact.put("primaryPhoneNumber", detailsMap.get("primaryPhoneNumber"));
	    contact.put("secondaryPhoneNumber", detailsMap.get("secondaryPhoneNumber"));
	}
}
