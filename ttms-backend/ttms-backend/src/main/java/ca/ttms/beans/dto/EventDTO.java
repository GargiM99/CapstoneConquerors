package ca.ttms.beans.dto;

import java.time.LocalDate;
import java.util.Map;

import ca.ttms.beans.details.CreateTripDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Integer eventId;
    private String eventName;
    private LocalDate eventDate;
    private String eventDescription;
    
    public EventDTO(Map<String, Object> eventMap) {
        this.eventId = (Integer) eventMap.get("eventId");
        this.eventName = (String) eventMap.get("eventName");
        this.eventDescription = (String) eventMap.get("eventDescription");
        
        Object eventDateObject = eventMap.get("tripEndDate");
        this.eventDate = convertToLocalDate(eventDateObject);
    }
    
    private LocalDate convertToLocalDate(Object dateObject) {
        if (dateObject instanceof java.sql.Date) {
            return ((java.sql.Date) dateObject).toLocalDate();
        } else if (dateObject instanceof LocalDate) {
            return (LocalDate) dateObject;
        } else {
            return null; // or handle it based on your requirements
        }
    }
}
