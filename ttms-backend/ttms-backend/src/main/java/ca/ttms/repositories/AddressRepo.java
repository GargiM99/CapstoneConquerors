package ca.ttms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.ttms.beans.Address;

/**
 * Repository class for Address class
 * 
 * @author Hamza
 * date: 2023/03/22
 */

public interface AddressRepo extends JpaRepository<Address, Integer>{

}
