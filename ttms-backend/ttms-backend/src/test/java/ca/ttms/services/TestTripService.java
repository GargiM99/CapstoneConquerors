package ca.ttms.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.ttms.beans.Address;
import ca.ttms.beans.Contact;
import ca.ttms.beans.Event;
import ca.ttms.beans.Person;
import ca.ttms.beans.Trip;
import ca.ttms.beans.User;
import ca.ttms.beans.details.CreateTripDetails;
import ca.ttms.beans.details.UserRegisterDetails;
import ca.ttms.beans.enums.EventStatus;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.enums.TripStatus;
import ca.ttms.beans.response.BasicClientResponse;
import ca.ttms.beans.response.TripResponse;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.EventRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TripRepo;
import ca.ttms.repositories.UserRepo;

public class TestTripService {
	@Mock
	private UserRepo userRepo;
	
	@Mock
	private PersonRepo personRepo;

	@Mock
	private AddressRepo addressRepo;

	@Mock
	private ContactRepo contactRepo;
	
	@Mock
	private TripRepo tripRepo;
	
	@Mock
	private EventRepo eventRepo;
	
	@Mock
	private AuthenticationService authService;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private TripService tripService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void Create_Trip_CheckTripResponseIsValid() {
		//Arrange
		TripResponse resultOutput;
		
		LocalDate inputDate = LocalDate.now();
		CreateTripDetails inputTrip = new CreateTripDetails(inputDate, "Test Trip", "Basic Trip", 2); 
		
		int expectedEventMinLength = 1;
		
		//Arrange Mock
		User mockClient = User.builder().role(Roles.CLIENT).id(2).build();
		Trip mockTrip = Trip.builder()
				.id(2).status(TripStatus.INPROGRESS)
				.tripEndDate(inputTrip.getTripEndDate()).tripName(inputTrip.getTripName())
				.users(mockClient).tripStartDate(inputDate).build();
		Optional<User> mockUserOpt = Optional.of(mockClient);
		
		List<Event> mockEvents = new ArrayList<>();
		
		IntStream.range(0, 4).forEach(i -> mockEvents.add(Event.builder().trip(mockTrip).build()));
		
		when(userRepo.findById(Mockito.anyInt())).thenReturn(mockUserOpt);
		when(tripRepo.save(Mockito.any(Trip.class))).thenReturn(mockTrip);
		when(eventRepo.saveAll(Mockito.any())).thenReturn(mockEvents);
		
		
		//Act
		resultOutput = tripService.createTrip(inputTrip);
		
		//Assert
		assertTrue("Event length should be longer then 1", resultOutput.getEventDetails().length > expectedEventMinLength);
	}	
	
	@Test
	void Create_TripWithAgentAsUser_CheckTripResponseIsNull() {
		//Arrange
		TripResponse resultOutput;
		
		LocalDate inputDate = LocalDate.now();
		CreateTripDetails inputTrip = new CreateTripDetails(inputDate, "Test Trip", "Basic Trip", 2); 
		Roles incorrectRole = Roles.AGENT;
		
		//Arrange Mock
		User mockClient = User.builder().role(incorrectRole).id(2).build();
		Trip mockTrip = Trip.builder()
				.id(2).status(TripStatus.INPROGRESS)
				.tripEndDate(inputTrip.getTripEndDate()).tripName(inputTrip.getTripName())
				.users(mockClient).tripStartDate(inputDate).build();
		Optional<User> mockUserOpt = Optional.of(mockClient);
		
		List<Event> mockEvents = new ArrayList<>();
		
		IntStream.range(0, 4).forEach(i -> mockEvents.add(Event.builder().trip(mockTrip).build()));
		
		when(userRepo.findById(Mockito.anyInt())).thenReturn(mockUserOpt);
		
		//Act
		resultOutput = tripService.createTrip(inputTrip);
		
		//Assert
		assertNull(resultOutput);
	}
	
	@Test
	void Create_TripWithIncorrectDetails_CheckTripResponseIsNull() {
		//Arrange
		TripResponse resultOutput;
		
		LocalDate inputDate = LocalDate.now();
		String incorrectName = "";
		CreateTripDetails inputTrip = new CreateTripDetails(inputDate, incorrectName, "Basic Trip", 2); 
		
		//Act
		resultOutput = tripService.createTrip(inputTrip);
		
		//Assert
		assertNull(resultOutput);
	}
	
	@Test
	void Get_CorrectId_CheckTripResponseIsNotNull() {
		//Arrange
		TripResponse resultOutput;
		
		LocalDate inputDate = LocalDate.now();
		String incorrectName = "";
		int inputTripId = 2;
		CreateTripDetails inputTrip = new CreateTripDetails(inputDate, incorrectName, "Basic Trip", 2); 
		
		//Arrange Mock
		List<Event> mockEvents = new ArrayList<>();
		IntStream.range(0, 4).forEach(i -> mockEvents.add(
				Event.builder().id(2).eventName("Test Event")
				.eventDate(inputDate).status(EventStatus.INCOMPLETE)
				.trip(new Trip()).build()));
		
		
		User mockClient = User.builder().id(2).build();
		Trip mockTrip = Trip.builder()
				.id(2).status(TripStatus.INPROGRESS)
				.tripEndDate(inputTrip.getTripEndDate()).tripName(inputTrip.getTripName())
				.users(mockClient).tripStartDate(inputDate).events(mockEvents).build();
		
		Optional<Trip> mockTripOpt = Optional.of(mockTrip);
		when(tripRepo.findById(Mockito.anyInt())).thenReturn(mockTripOpt);
		
		//Act
		resultOutput = tripService.getTripDetails(inputTripId);
		
		//Assert
		assertNotNull(resultOutput);
	}
	
	@Test
	void Get_IncorrectIdNotMatchUser_CheckTripResponseIsNull() {
		//Arrange
		TripResponse resultOutput;
		int inputId = 2; 
		
		//Arrange Mock
		Optional<Trip> mockTripOpt = Optional.empty();
		when(tripRepo.findById(Mockito.anyInt())).thenReturn(mockTripOpt);
		
		//Act
		resultOutput = tripService.getTripDetails(inputId);
		
		//Assert
		assertNull(resultOutput);
	}
	
	@Test
	void Get_IncorrectId_CheckTripResponseIsNull() {
		//Arrange
		TripResponse resultOutput;
		int inputId = 0; 
		LocalDate inputDate = LocalDate.now();
		CreateTripDetails inputTrip = new CreateTripDetails(inputDate, "Trip Name", "Basic Trip", 2); 
		
		
		//Arrange Mock
		Optional<Trip> mockTripOpt = Optional.empty();
		when(tripRepo.findById(Mockito.anyInt())).thenReturn(mockTripOpt);
		
		//Act
		resultOutput = tripService.getTripDetails(inputId);
		
		//Assert
		assertNull(resultOutput);
	}
}
