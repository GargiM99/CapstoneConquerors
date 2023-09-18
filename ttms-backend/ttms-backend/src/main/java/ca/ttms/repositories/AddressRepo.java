package ca.ttms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ca.ttms.beans.Address;

/**
 * Repository class for Address class
 * 
 * @author Hamza
 * date: 2023/03/22
 */

public interface AddressRepo extends JpaRepository<Address, Integer>{

	@Transactional
	@Modifying
	@Query("""
		    UPDATE Address a
		    SET a.addressLine = :al,
		        a.postalCode = :pc,
		        a.city = :ci,
		        a.province = :pr,
		        a.country = :co
		    WHERE :un IN (SELECT u.username FROM User u JOIN a.persons p WHERE u.person = p)
		    """)
	Integer updateAddressByUsername(
			@Param("un") String username, @Param("al") String addressLine,
			@Param("pc") String postalCode, @Param("ci") String city, 
			@Param("pr") String province, @Param("co") String country);
	
	
	@Transactional
	@Modifying
	@Query("""
		    UPDATE Address a
		    SET a.addressLine = :al,
		        a.postalCode = :pc,
		        a.city = :ci,
		        a.province = :pr,
		        a.country = :co
		    WHERE :id IN (SELECT u.id FROM User u JOIN a.persons p WHERE u.person = p)
		    """)
	Integer updateAddressByUserId(
			@Param("id") Integer username, @Param("al") String addressLine,
			@Param("pc") String postalCode, @Param("ci") String city, 
			@Param("pr") String province, @Param("co") String country);
}
