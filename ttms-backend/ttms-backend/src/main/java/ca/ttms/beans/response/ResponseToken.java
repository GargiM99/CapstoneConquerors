package ca.ttms.beans.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

/**
 * Class for getting response token
 * 
 * @author Hamza 
 * date: 2023/03/08 
 */

public class ResponseToken {
	private String token;
}
