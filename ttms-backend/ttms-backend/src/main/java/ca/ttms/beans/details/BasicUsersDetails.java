package ca.ttms.beans.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
