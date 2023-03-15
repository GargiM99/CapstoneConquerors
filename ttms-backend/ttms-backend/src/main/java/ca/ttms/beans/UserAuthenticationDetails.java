package ca.ttms.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hamza  
 * date: 2023/03/08 
 * description: Class for holding details for authentication
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationDetails {

	private String username;
	private String password;
}
