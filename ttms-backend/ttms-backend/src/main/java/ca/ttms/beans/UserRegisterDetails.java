package ca.ttms.beans;

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
	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private String password;
	
	public boolean isValid () {
		
		if (this.firstname == null || this.firstname == "")
			return false;
		
		else if (this.lastname == null || this.lastname == "")
			return false;
		
		else if (this.email == null || this.email == "")
			return false;
		
		else if (this.username == null || this.username == "")
			return false;
		
		else if (this.password == null || this.password == "")
			return false;
			
		return true;
	}
}
