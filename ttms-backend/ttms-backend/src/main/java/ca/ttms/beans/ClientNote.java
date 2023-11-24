package ca.ttms.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "client_note")
public class ClientNote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name = "note_title", nullable = false)
	private String noteTitle;
	
	@Column (name = "note_body", nullable = false)
	private String noteBody;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
    private User client;
	
	@ManyToOne
	@JoinColumn(name = "trip_id")
    private Trip trip;
}
