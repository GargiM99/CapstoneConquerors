package ca.ttms.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
	@JoinTable(
			  name = "address_person",
			  joinColumns= @JoinColumn(name="person_id", referencedColumnName="id"),
	          inverseJoinColumns= @JoinColumn(name="address_id", referencedColumnName="id"))
	private List<Address> addresses;
	
	public Person() {
		this.addresses = new ArrayList<Address>();
	}
}
