package ca.ttms.beans;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import ca.ttms.beans.details.TripDetails;
import ca.ttms.beans.enums.TripStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trip")
public class Trip {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name = "trip_name", nullable = false)
	private String tripName; 
	
	@Column (name = "trip_type")
	private String tripType;
	
	@Column (name = "trip_start_date", nullable = false)
	private LocalDate tripStartDate;
	
	@Column (name = "trip_end_date", nullable = false)
	private LocalDate tripEndDate;
	
	@Enumerated(EnumType.STRING)
	private TripStatus status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
    private User users;
	
	@OneToMany(mappedBy = "trip")
	private List<Event> events;

	public Trip (TripDetails tripDetails) {
		this.id = tripDetails.getId();
		this.tripName = tripDetails.getTripName();
		this.tripType = tripDetails.getTripType();
		this.tripStartDate = tripDetails.getTripStartDate();
		this.tripEndDate = tripDetails.getTripEndDate();
		this.status = tripDetails.getStatus();
	}
}
