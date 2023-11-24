package ca.ttms.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.azure.storage.blob.models.BlobStorageException;
import com.google.gson.reflect.TypeToken;

import ca.ttms.beans.Event;
import ca.ttms.beans.Trip;
import ca.ttms.beans.User;
import ca.ttms.beans.details.CreateTripDetails;
import ca.ttms.beans.details.EditTripDetails;
import ca.ttms.beans.details.EventDetails;
import ca.ttms.beans.details.EventTypeDetails;
import ca.ttms.beans.details.TripDetails;
import ca.ttms.beans.details.TripTypeDetails;
import ca.ttms.beans.enums.EventStatus;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.enums.TripStatus;
import ca.ttms.beans.response.TripResponse;
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
	private ContactRepo contactRepo;
	
	@Mock
	private TripRepo tripRepo;
	
	@Mock
	private EventRepo eventRepo;
	
	@Mock
	private AuthenticationService authService;
	
	@Mock
	private BlobService blobService;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private TripService tripService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	//@Test
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
		assertTrue(resultOutput.getEventDetails().length > expectedEventMinLength);
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

	@Test
	void Edit_Trip_CheckTripResponseIsNotNull() {
		//Arrange
		TripResponse resultOutput;
		
		LocalDate inputDate = LocalDate.now();
		String correctName = "Bob Summer";
		String correctType = "Disney Cruise";
		int inputTripId = 2;
		TripDetails inputTrip = new TripDetails(inputTripId, correctName, inputDate, inputDate.minusDays(60), 
												correctType, TripStatus.INPROGRESS, 1);
		
		int inputEvnetId = 2;
		String correctEName = "Book";
		String correctEDescription = "Book Trip";
		EventDetails inputEvent = new EventDetails(inputEvnetId, correctEName, correctEDescription, 
												   inputDate.minusDays(30), EventStatus.INCOMPLETE, inputTripId);
		
		EventDetails[] inputEventArr = {inputEvent};
		EditTripDetails inputEditDetails = new EditTripDetails(inputTrip, inputEventArr);
		
		//Arrange Mock
		List<Event> mockEvents = new ArrayList<>();
		String mockType = "Disney Cruise";
		
		IntStream.range(0, 4).forEach(i -> mockEvents.add(
				Event.builder().id(2).eventName("Test Event")
				.eventDate(inputDate).status(EventStatus.INCOMPLETE)
				.trip(new Trip()).build()));
		
		
		User mockClient = User.builder().id(2).build();
		Trip mockTrip = Trip.builder()
				.id(2).status(TripStatus.INPROGRESS)
				.tripEndDate(inputTrip.getTripEndDate()).tripName(inputTrip.getTripName())
				.users(mockClient).tripStartDate(inputDate).events(mockEvents).tripType(mockType).build();
		
		
		Optional<Trip> mockTripOpt = Optional.of(mockTrip);

		when(eventRepo.saveAll(Mockito.any())).thenReturn(mockEvents);
		when(tripRepo.save(Mockito.any(Trip.class))).thenReturn(mockTrip);
		when(tripRepo.findById(Mockito.anyInt())).thenReturn(mockTripOpt);
		
		//Act
		resultOutput = tripService.editTrip(inputEditDetails);
		
		//Assert
		assertNotNull(resultOutput);
	}
	
	@Test
	void Edit_IncorrectTrip_CheckTripResponseIsNull() {
		//Arrange
		TripResponse resultOutput;
		
		LocalDate inputDate = LocalDate.now();
		String incorrectName = "";
		String correctType = "Disney Cruise";
		int inputTripId = 2;
		TripDetails inputTrip = new TripDetails(inputTripId, incorrectName, inputDate, inputDate.minusDays(60), 
												correctType, TripStatus.INPROGRESS, 1);
		
		int inputEvnetId = 2;
		String correctEName = "Book";
		String correctEDescription = "Book Trip";
		EventDetails inputEvent = new EventDetails(inputEvnetId, correctEName, correctEDescription, 
												   inputDate.minusDays(30), EventStatus.INCOMPLETE, inputTripId);
		
		EventDetails[] inputEventArr = {inputEvent};
		EditTripDetails inputEditDetails = new EditTripDetails(inputTrip, inputEventArr);
		
		//Act
		resultOutput = tripService.editTrip(inputEditDetails);
		
		//Assert
		assertNull(resultOutput);
	}
	
	@Test
	void Edit_TripNonExitstantTripId_CheckTripResponseIsNull() {
		//Arrange
		TripResponse resultOutput;
		
		LocalDate inputDate = LocalDate.now();
		String correctName = "Bob Summer";
		String correctType = "Disney Cruise";
		int inputTripId = 2;
		TripDetails inputTrip = new TripDetails(inputTripId, correctName, inputDate, inputDate.minusDays(60), 
												correctType, TripStatus.INPROGRESS, 1);
		
		int inputEvnetId = 2;
		String correctEName = "Book";
		String correctEDescription = "Book Trip";
		EventDetails inputEvent = new EventDetails(inputEvnetId, correctEName, correctEDescription, 
												   inputDate.minusDays(30), EventStatus.INCOMPLETE, inputTripId);
		
		EventDetails[] inputEventArr = {inputEvent};
		EditTripDetails inputEditDetails = new EditTripDetails(inputTrip, inputEventArr);
		
		//Arrange Mock
		when(tripRepo.findById(Mockito.anyInt())).thenReturn(null);
		
		//Act
		resultOutput = tripService.editTrip(inputEditDetails);
		
		//Assert
		assertNull(resultOutput);
	}

	@Test
	void Get_TripType_CheckResponseIsNotNull() {
		//Arrange Mock
		List<TripTypeDetails> mockTypeDetails = new ArrayList<TripTypeDetails>();
		when(blobService.downloadJsonBlob(Mockito.anyString(), Mockito.anyString(), Mockito.any(TypeToken.class))).thenReturn(mockTypeDetails);
		
		//Act
		List<TripTypeDetails> response = (List<TripTypeDetails>) tripService.getTripType();
		
		//Assert
		assertNotNull(response);
	}
	
	@Test
	void Get_TripTypeNoResponse_CheckResponseIsNull() {
		//Arrange Mock
		when(blobService.downloadJsonBlob(Mockito.anyString(), Mockito.anyString(), Mockito.any(TypeToken.class))).thenReturn(null);
		
		//Act
		List<TripTypeDetails> response = (List<TripTypeDetails>) tripService.getTripType();
		
		//Assert
		assertNull(response);
	}
	
	@Test
	void Upload_TripType_CheckResponseIsNotNull() {
		//Arrange
		String inputTripName = "Disney Cruise";
		
		EventTypeDetails inputEventType1 = new EventTypeDetails("Book Trip", "Test Desc", 30);
		EventTypeDetails inputEventType2 = new EventTypeDetails("Pay Trip", "Test Desc", 3);
		List<EventTypeDetails> inputEventTypeList = new ArrayList<EventTypeDetails>();
		inputEventTypeList.add(inputEventType1);
		inputEventTypeList.add(inputEventType2);
		
		List<TripTypeDetails> inputTripTypes = new ArrayList<TripTypeDetails>();
		inputTripTypes.add(new TripTypeDetails(inputTripName, inputEventTypeList));
		
		//Arrange Mock
		TripTypeDetails mockTripType = new TripTypeDetails(inputTripName, inputEventTypeList);
		doNothing().when(blobService).uploadJsonBlob(Mockito.anyString(), Mockito.anyString(), Mockito.any(TripTypeDetails.class));

		//Act
		List<TripTypeDetails> response = (List<TripTypeDetails>) tripService.uploadTripType(inputTripTypes);
		
		//Assert
		assertNotNull(response);
	}
	
	@Test
	void Upload_IncorrectTripType_CheckResponseIsNull() {
		//Arrange
		String inputTripName = "Disney Cruise";
		String incorrectEventName = "";
		
		EventTypeDetails inputEventType1 = new EventTypeDetails(incorrectEventName, "Test Desc", 30);
		EventTypeDetails inputEventType2 = new EventTypeDetails("Pay Trip", "Test Desc", 3);
		List<EventTypeDetails> inputEventTypeList = new ArrayList<EventTypeDetails>();
		inputEventTypeList.add(inputEventType1);
		inputEventTypeList.add(inputEventType2);
		
		List<TripTypeDetails> inputTripTypes = new ArrayList<TripTypeDetails>();
		inputTripTypes.add(new TripTypeDetails(inputTripName, inputEventTypeList));

		//Act
		List<TripTypeDetails> response = (List<TripTypeDetails>) tripService.uploadTripType(inputTripTypes);
		
		//Assert
		assertNull(response);
	}
	
	@Test
	void Upload_TripType_ErrorUploadingBlob_CheckResponseIsNull() {
		//Arrange
		String inputTripName = "Disney Cruise";
		
		EventTypeDetails inputEventType1 = new EventTypeDetails("", "Test Desc", 30);
		EventTypeDetails inputEventType2 = new EventTypeDetails("Pay Trip", "Test Desc", 3);
		List<EventTypeDetails> inputEventTypeList = new ArrayList<EventTypeDetails>();
		inputEventTypeList.add(inputEventType1);
		inputEventTypeList.add(inputEventType2);
		
		List<TripTypeDetails> inputTripTypes = new ArrayList<TripTypeDetails>();
		inputTripTypes.add(new TripTypeDetails(inputTripName, inputEventTypeList));
		
		//Arrange Mock
		doThrow(new BlobStorageException("Blob couldn't upload", null, null))
	    .when(blobService).uploadJsonBlob(Mockito.anyString(), Mockito.anyString(), Mockito.any(TripTypeDetails.class));

		//Act
		List<TripTypeDetails> response = (List<TripTypeDetails>) tripService.uploadTripType(inputTripTypes);
		
		//Assert
		assertNull(response);
	}
}
