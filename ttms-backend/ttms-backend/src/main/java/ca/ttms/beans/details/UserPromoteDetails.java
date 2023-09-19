package ca.ttms.beans.details;

import ca.ttms.beans.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPromoteDetails {
	private Roles role; 
}
