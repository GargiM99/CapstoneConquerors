package ca.ttms.beans.dto;

import ca.ttms.beans.Trip;
import ca.ttms.beans.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientNoteDTO {
	private Integer id;
	private String noteTitle;
	private String noteBody;
	private Integer clientId;
	private Integer tripId;
    private String tripName;
}
