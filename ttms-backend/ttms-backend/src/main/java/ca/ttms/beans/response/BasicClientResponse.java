package ca.ttms.beans.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicClientResponse {
	private String firstname;
	private String lastname;
	private String username;
	private Integer id;
}
