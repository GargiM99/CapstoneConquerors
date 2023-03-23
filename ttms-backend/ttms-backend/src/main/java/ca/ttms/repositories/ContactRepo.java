package ca.ttms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.ttms.beans.Contact;

/**
 * Repository class for Contact class
 * 
 * @author Hamza
 * date: 2023/03/22
 */

public interface ContactRepo extends JpaRepository<Contact, Integer>{

}
