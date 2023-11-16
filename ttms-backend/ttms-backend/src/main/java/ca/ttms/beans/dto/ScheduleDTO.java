package ca.ttms.beans.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

import ca.ttms.beans.details.CreateTripDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Integer clientId;
    private String username;
    private String firstname;
    private String lastname;

    @Builder.Default
    private List<TripDTO> trips = Collections.emptyList();

    public ScheduleDTO(ClientDTO client, List<TripDTO> trips) {
    	this.clientId = client.getId();
    	this.username = client.getUsername();
    	this.firstname = client.getFirstname();
    	this.lastname = client.getLastname();
    	this.trips = trips;
    }
}
