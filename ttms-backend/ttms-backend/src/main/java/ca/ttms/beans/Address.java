package ca.ttms.beans;

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

/**
 * Address entity for people
 * 
 * @author Hamza
 * date: 2023-03-22
 */

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
    private String addressLine;
    private String postalCode;
    private String city;
    private String province;
    private String country;
    
    @ManyToMany(fetch = FetchType.LAZY)
	private List<Person> persons;
    
    public Address () {
    	this.persons = new ArrayList<Person>();
    }
}
