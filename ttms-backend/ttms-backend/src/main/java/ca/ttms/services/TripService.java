package ca.ttms.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ca.ttms.beans.Event;
import ca.ttms.beans.Trip;
import ca.ttms.beans.User;
import ca.ttms.beans.details.CreateTripDetails;
import ca.ttms.beans.details.TripDetails;
import ca.ttms.beans.enums.EventStatus;
import ca.ttms.beans.enums.Roles;
import ca.ttms.beans.enums.TripStatus;
import ca.ttms.beans.response.EventTemplate;
import ca.ttms.beans.response.TripResponse;
import ca.ttms.repositories.AddressRepo;
import ca.ttms.repositories.ContactRepo;
import ca.ttms.repositories.EventRepo;
import ca.ttms.repositories.PersonRepo;
import ca.ttms.repositories.TripRepo;
import ca.ttms.repositories.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripService {
	private final UserRepo userRepo;
	private final TripRepo tripRepo;
	private final EventRepo eventRepo;
	
	
	public TripResponse createTrip(CreateTripDetails createDetails) {
		if (!createDetails.verifyTripDetails())
			return null;
		
		Trip newTrip = Trip
				.builder()
				.status(TripStatus.INPROGRESS)
				.tripStartDate(LocalDate.now())
				.tripEndDate(createDetails.getTripEndDate())
				.tripName(createDetails.getTripName())
				.build();
		
		Optional<User> clientUserOpt = userRepo.findById(createDetails.getClientId());
		User clientUser = clientUserOpt.orElse(null);
		
		if (clientUser == null || clientUser.getRole() != Roles.CLIENT)
			return null;
		
		newTrip.setUsers(clientUser);
		newTrip = tripRepo.save(newTrip);
		Event[] newEvents = generateBaseEvents(newTrip);
		TripResponse tripResponse = new TripResponse(newTrip, newEvents);
		
		return tripResponse;
	}
	
	public Event[] generateBaseEvents(Trip relatedTrip) {
		if (relatedTrip == null)
			return null;
		
		EventTemplate[] baseEventTemplate = generateBaseEventTemplate();
		List<Event> newEvents = new ArrayList<Event>();
		
		if (baseEventTemplate.length <= 0)
			return null;
		
		for (EventTemplate eventTemplate : baseEventTemplate) {
			Event newEvent = Event
					.builder()
					.eventDate(relatedTrip.getTripEndDate().minusDays(eventTemplate.getDateOffset()))
					.eventName(eventTemplate.getEventName())
					.status(EventStatus.INCOMPLETE)
					.trip(relatedTrip)
					.build();
			newEvents.add(newEvent);
		}
		newEvents = eventRepo.saveAll(newEvents);
		
		return newEvents.toArray(new Event[newEvents.size()]);
	}
	
	public EventTemplate[] generateBaseEventTemplate() {
		EventTemplate[] template = {new EventTemplate("Renew Passport", 120),
		                            new EventTemplate("Pay Intial Fees", 90),
		                            new EventTemplate("Reserve Resturants", 20),
		                            new EventTemplate("Pay Final Fees", 5)};
		
		return template;
	}

	public boolean verifyTripToAgent(String agentUsername, int tripId) {
		long tripCount = tripRepo.isAgentTripRelated(agentUsername, tripId);
		return tripCount > 0;
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
}
