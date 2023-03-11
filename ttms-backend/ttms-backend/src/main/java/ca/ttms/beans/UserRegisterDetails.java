package ca.ttms.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hamza  
 * date: 2023/03/08 
 * description: Class for registering details of users
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
}
