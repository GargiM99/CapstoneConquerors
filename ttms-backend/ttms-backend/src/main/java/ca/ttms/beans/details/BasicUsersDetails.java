package ca.ttms.beans.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class for getting basic User Details
 * 
 * @author Hamza  
 * date: 2023/03/08 
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicUsersDetails {
	private String firstname;
	private String lastname;
	private String username;
	private Integer id;
}
