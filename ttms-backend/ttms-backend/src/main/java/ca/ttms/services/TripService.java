package ca.ttms.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
import ca.ttms.repositories.EventRepo;
import ca.ttms.repositories.TripRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripService {
	private final UserRepo userRepo;
	private final TripRepo tripRepo;
	private final EventRepo eventRepo;
	private final BlobService blobService;
	private final JasperService jasperService;
	
	@Value("${spring.cloud.azure.storage.blob.trip-blob-name}")
	private String tripBlobName;
	
	@Value("${spring.cloud.azure.storage.blob.container-name}")
	private String containerName;
	
	private final int MAX_DATEDIFF = 1000;
	
	public TripResponse createTrip(CreateTripDetails createDetails) {
		if (!createDetails.verifyTripDetails())
			return null;
		
		Trip newTrip = Trip
				.builder()
				.status(TripStatus.INPROGRESS)
				.tripStartDate(LocalDate.now())
				.tripEndDate(createDetails.getTripEndDate())
				.tripName(createDetails.getTripName())
				.tripType(createDetails.getTripType())
				.build();
		
		Optional<User> clientUserOpt = userRepo.findById(createDetails.getClientId());
		User clientUser = clientUserOpt.orElse(null);
		
		if (clientUser == null || clientUser.getRole() != Roles.CLIENT)
			return null;
		
		newTrip.setUsers(clientUser);
		newTrip = tripRepo.save(newTrip);
		Event[] newEvents = generateBaseEvents(newTrip);
		
		if (newEvents == null) {
			tripRepo.delete(newTrip);
			return null;
		}
		
		TripResponse tripResponse = new TripResponse(newTrip, newEvents);
		
		return tripResponse;
	}
	
	public Event[] generateBaseEvents(Trip relatedTrip) {
		if (relatedTrip == null)
			return null;
		
		List<TripTypeDetails> tripTypeDetails = getTripType();
		List<EventTypeDetails> baseEventTemplate = tripTypeDetails.stream()
			    .filter(tripTypeDetail -> tripTypeDetail.getTypeName().equals(relatedTrip.getTripType()))
			    .findFirst()
			    .map(TripTypeDetails::getEventTypes)
			    .orElse(Collections.emptyList());
		
		List<Event> newEvents = new ArrayList<Event>();
		
		if (baseEventTemplate.size() <= 0)
			return null;
		
		for (EventTypeDetails eventTemplate : baseEventTemplate) {
			Event newEvent = Event
					.builder()
					.eventDate(relatedTrip.getTripEndDate().minusDays(eventTemplate.getDateDiff()))
					.eventName(eventTemplate.getEventName())
					.eventDescription(eventTemplate.getEventDescription())
					.status(EventStatus.INCOMPLETE)
					.trip(relatedTrip)
					.build();
			newEvents.add(newEvent);
		}
		newEvents = eventRepo.saveAll(newEvents);
		
		return newEvents.toArray(new Event[newEvents.size()]);
	}

	public boolean verifyTripToAgent(String agentUsername, int tripId) {
		long tripCount = tripRepo.isAgentTripRelated(agentUsername, tripId);
		return tripCount > 0;
	}
	
	public TripResponse editTrip(EditTripDetails editDetails) {
		if (!editDetails.verifyDetails())
			return null;
		
		try {
			Optional<Trip> currTripOpt = tripRepo.findById(editDetails.getTripDetails().getId());
			Trip currTrip = currTripOpt.orElse(null);
			
			if (currTrip == null)
				return null;
			
			List<Event> newEvents = Arrays.asList(editDetails.getEventDetails()).stream()
				    .map(detail -> new Event(detail, currTrip))
				    .collect(Collectors.toList());
			Trip newTrip = new Trip(editDetails.getTripDetails());
			newTrip.setUsers(currTrip.getUsers());
			newTrip.setEvents(newEvents);
			
			newEvents = eventRepo.saveAll(newEvents);
			newTrip = tripRepo.save(newTrip);

			Event[] tripEvents = newEvents.toArray(new Event[newEvents.size()]);
			TripResponse response = new TripResponse(newTrip, tripEvents);
			return response;
		}catch(Exception e) {
			return null;
		}
	}
	
	public TripResponse getTripDetails(int tripId) {
		if (tripId <= 0)
			return null;
		
		Trip trip = tripRepo.findById(tripId).orElse(null);
		if (trip == null)
			return null;
		
		Event[] eventArray = trip.getEvents().toArray(new Event[0]);
		TripResponse response = new TripResponse(trip, eventArray);
		return response;
	}
	
	public byte[] generateTripReport(int tripId) {
		try {
			if (tripId <= 0)
				return null;
			
			Trip trip = tripRepo.findById(tripId).orElse(null);
			if (trip == null)
				return null;
			
			EventDetails[] eventDetailArray = trip.getEvents().stream()
				    .map(event -> new EventDetails(event))
				    .toArray(EventDetails[]::new);
			TripDetails tripDetails = new TripDetails(trip);
			
	        Map<String, Object> reportParam = new HashMap<>();
	        reportParam.put("tripDetails", tripDetails);
	        reportParam.put("eventDetails", eventDetailArray);
	        
	        byte[] reportByte = jasperService.generateReport(reportParam, "ttms_schedule.jasper");
			return reportByte;
		}catch(Exception e) {
			return null;
		}

	}

	public List<TripTypeDetails> uploadTripType (List<TripTypeDetails> typeDetails) {
		try {
			for (TripTypeDetails typeDetail : typeDetails) {
				if (!verifyTripType(typeDetail))
					return null;
			}
			blobService.uploadJsonBlob(containerName, tripBlobName, typeDetails);
		}catch(Exception ex) {
			return null;
		}
		
		return typeDetails;
	}
	
	public List<TripTypeDetails> getTripType () {
		try {
			TypeToken<List<TripTypeDetails>> typeToken = new TypeToken<List<TripTypeDetails>>() {};
			List<TripTypeDetails> typeDetails = (List<TripTypeDetails>) blobService.downloadJsonBlob(containerName, tripBlobName, typeToken);
			return typeDetails;
		}catch(Exception ex) {
			return null;
		}
	}
	
	public boolean verifyTripType (TripTypeDetails typeDetails) {
		if (typeDetails.getTypeName() == null || typeDetails.getTypeName() == "")
			return false;
		
		if (typeDetails.getEventTypes() == null)
			return false;
			
		for (EventTypeDetails eventDetails : typeDetails.getEventTypes()) {
			if (eventDetails.getEventName() == null || eventDetails.getEventName() == "")
				return false;
			if (eventDetails.getDateDiff() <= 0 || eventDetails.getDateDiff() > MAX_DATEDIFF)
				return false;
		}
		return true;
	}
}
