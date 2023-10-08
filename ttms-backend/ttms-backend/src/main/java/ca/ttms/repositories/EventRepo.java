package ca.ttms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.ttms.beans.Event;

public interface EventRepo extends JpaRepository<Event, Integer>{

}
