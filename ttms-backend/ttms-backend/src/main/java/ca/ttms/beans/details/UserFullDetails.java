package ca.ttms.beans.details;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ca.ttms.beans.Address;
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
	private Map<String, Object> address;
	private Map<String, Object> person;
	
	public void mapDetailsFromRepo(Map<String,Object> detailsMap) {
		
		user = new HashMap<>();
		contact = new HashMap<>();
		address = new HashMap<>();
		person = new HashMap<>();
		
	    user.put("id", detailsMap.get("id"));
	    user.put("username", detailsMap.get("username"));

	    person.put("firstname", detailsMap.get("firstname"));
	    person.put("lastname", detailsMap.get("lastname"));
	    person.put("birthDate", detailsMap.get("birthDate"));

	    address.put("addressLine", detailsMap.get("addressLine"));
	    address.put("postalCode", detailsMap.get("postalCode"));
	    address.put("city", detailsMap.get("city"));
	    address.put("province", detailsMap.get("province"));
	    address.put("country", detailsMap.get("country"));

	    contact.put("email", detailsMap.get("email"));
	    contact.put("primaryPhoneNumber", detailsMap.get("primaryPhoneNumber"));
	    contact.put("secondaryPhoneNumber", detailsMap.get("secondaryPhoneNumber"));
	}
}
