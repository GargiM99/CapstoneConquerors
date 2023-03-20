package ca.ttms.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for holding details for authentication
 * 
 * @author Hamza  
 * date: 2023/03/08 
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthenticationDetails {

	private String username;
	private String password;
}
