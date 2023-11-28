package ca.ttms.beans.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientNoteDetails {
	private Integer id;
	private String noteTitle;
	private String noteBody;	
	private Integer tripId;
    
    private Integer clientId;
    private String clientFirstName;
    private String clientLastName;
    private String clientUserName;
}
