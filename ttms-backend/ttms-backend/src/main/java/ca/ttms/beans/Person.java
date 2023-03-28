package ca.ttms.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Person Entity
 * 
 * @author Hamza
 * date: 2023-03-22
 */

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String firstname;
	private String lastname;
	private LocalDate birthDate;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Address> addresses;
	
	public Person() {
		this.addresses = new ArrayList<Address>();
	}
}
