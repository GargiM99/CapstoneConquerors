package ca.ttms.beans.details;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyClientNoteDetails {
	private String noteTitle;
	private String noteBody;
	private LocalDate modifyDate;
	private Integer tripId;
	private Integer clientId;
    
    public boolean verifyNoteDetails() {
    	if (this.noteTitle == null || this.noteTitle.length() < 2) 
    		return false;
    	if (this.noteBody == null || this.noteBody.length() < 2) 
    		return false;
    	if (this.tripId <= 0) 
    		return false;
    	if (this.clientId <= 0)
    		return false;
    	return true;
    }
}
