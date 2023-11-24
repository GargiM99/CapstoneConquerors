package ca.ttms.beans.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.ttms.beans.details.CreateTripDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
	private Integer clientId;
    private Integer tripId;
    private String tripName;
    private String tripType;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;

    @Builder.Default
    private List<EventDTO> events = new ArrayList<>();
    
    public TripDTO(Map<String, Object> tripMap, List<EventDTO> events) {
        this.clientId = (Integer) tripMap.get("clientId");
        this.tripId = (Integer) tripMap.get("tripId");
        this.tripName = (String) tripMap.get("tripName");
        this.tripType = (String) tripMap.get("tripType");
        
        Object tripStartDateObject = tripMap.get("tripStartDate");
        this.tripStartDate = convertToLocalDate(tripStartDateObject);

        Object tripEndDateObject = tripMap.get("tripEndDate");
        this.tripEndDate = convertToLocalDate(tripEndDateObject);

//        Object eventsObject = tripMap.get("events");
//        List<EventDTO> newEvents = (eventsObject != null) ? (List<EventDTO>) eventsObject : new ArrayList<EventDTO>();
        this.events = events;
    }
    
    public TripDTO(Map<String, Object> tripMap) {
        this.clientId = (Integer) tripMap.get("clientId");
        this.tripId = (Integer) tripMap.get("tripId");
        this.tripName = (String) tripMap.get("tripName");
        this.tripType = (String) tripMap.get("tripType");
        
        Object tripStartDateObject = tripMap.get("tripStartDate");
        this.tripStartDate = convertToLocalDate(tripStartDateObject);

        Object tripEndDateObject = tripMap.get("tripEndDate");
        this.tripEndDate = convertToLocalDate(tripEndDateObject);

        Object eventsObject = tripMap.get("events");
        List<EventDTO> newEvents = (eventsObject != null) ? (List<EventDTO>) eventsObject : new ArrayList<EventDTO>();
        this.events = events;
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

