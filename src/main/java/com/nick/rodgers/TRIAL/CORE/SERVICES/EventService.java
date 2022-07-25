package com.nick.rodgers.TRIAL.CORE.SERVICES;

import com.nick.rodgers.TRIAL.API.CONTAINERS.EventContainer;
import com.nick.rodgers.TRIAL.API.CONTAINERS.ResponseContainer;
import com.nick.rodgers.TRIAL.API.CONTAINERS.SeatContainer;
import com.nick.rodgers.TRIAL.API.CONTAINERS.TicketReservationContainer;
import com.nick.rodgers.TRIAL.API.ERRORHANDLING.EventNotFoundException;
import com.nick.rodgers.TRIAL.CORE.EVENT.EventData;
import com.nick.rodgers.TRIAL.CORE.EVENT.Seat;
import com.nick.rodgers.TRIAL.CORE.MessageConstants;
import com.nick.rodgers.TRIAL.CORE.REPOS.BankCardRepository;
import com.nick.rodgers.TRIAL.CORE.REPOS.EventDataRepository;
import com.nick.rodgers.TRIAL.CORE.REPOS.SeatRepository;
import com.nick.rodgers.TRIAL.CORE.REPOS.UserRepository;
import com.nick.rodgers.TRIAL.CORE.USER.BankCard;
import com.nick.rodgers.TRIAL.CORE.USER.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private EventDataRepository eventDataRepository;
    private SeatRepository seatRepository;
    private UserRepository userRepository;
    private BankCardRepository bankCardRepository;

    @Autowired
    public EventService(
            EventDataRepository eventDataRepository,
            SeatRepository seatRepository,
            UserRepository userRepository,
            BankCardRepository bankCardRepository
    ) {
        this.eventDataRepository = eventDataRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.bankCardRepository = bankCardRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public List<EventData> findAllEvents() {
        return eventDataRepository.findAll();
    }

    public Optional<EventData> findEventById(Long id) {
        Optional<EventData> foundEvent = eventDataRepository.findById(id);
        if (foundEvent.isPresent()) {
            return foundEvent;
        } else {
            logger.error("Requested event ID is not found.");
            throw new EventNotFoundException(id);
        }
    }

    public List<EventData> findEventsByTitle(String eventTitle) {
        List<EventData> foundEventTitle = eventDataRepository.findByEventTitle(eventTitle);
        if (!ObjectUtils.isEmpty(foundEventTitle)) {
            return foundEventTitle;
        } else {
            logger.error("Requested event name is not found.");
            throw new EventNotFoundException(eventTitle);
        }
    }

    public ResponseEntity<ResponseContainer> reserveTicket(TicketReservationContainer ticketPurchaseAttempt) {
        Optional<User> ticketPurchaser = userRepository.findById(ticketPurchaseAttempt.getPurchaserId());

        if (ticketPurchaser.isEmpty()) {
            logger.error("No user found with this ID: " + ticketPurchaseAttempt.getEventId());
            return new ResponseEntity<>(MessageConstants.NO_USER, HttpStatus.NOT_FOUND);
        }

        Optional<EventData> eventToPurchaseFrom = eventDataRepository.findById(ticketPurchaseAttempt.getEventId());

        if (eventToPurchaseFrom.isEmpty()) {
            logger.error("No event found with this ID: " + ticketPurchaseAttempt.getEventId());
            return new ResponseEntity<>(MessageConstants.NO_EVENT, HttpStatus.NOT_FOUND);
        }

        if (eventToPurchaseFrom.get().eventStartTimeStamp > getCurrentTimeLong()) {
            logger.error("Event has already started: " + eventToPurchaseFrom.get().getEventId());
            return new ResponseEntity<>(MessageConstants.EVENT_STARTED, HttpStatus.PRECONDITION_FAILED);
        }

        Optional<Seat> seatToBePurchased = seatRepository.findById(ticketPurchaseAttempt.getSeatId());

        if (seatToBePurchased.isEmpty() || seatToBePurchased.get().reserved) {
            logger.error("Seat does not exist or is already reserved");
            return new ResponseEntity<>(MessageConstants.NO_SEAT, HttpStatus.PRECONDITION_FAILED);
        }

        Optional<BankCard> bankCardToUse = bankCardRepository.findById(ticketPurchaseAttempt.getPurchaserCardId());

        if (bankCardToUse.isEmpty()) {
            logger.error("No bank card found with this ID: " + bankCardToUse.get().getUserCardID());
            return new ResponseEntity<>(MessageConstants.NO_CARD, HttpStatus.PRECONDITION_FAILED);
        }

        if (bankCardToUse.get().cardOwner.getUserID() != ticketPurchaser.get().getUserID()) {
            logger.warn("Bank card:" + bankCardToUse.get().getUserCardID() + " does not belong to this user: " + ticketPurchaser.get().getUserID());
            return new ResponseEntity<>(MessageConstants.NOT_YOUR_CARD, HttpStatus.PRECONDITION_FAILED);
        }

        if (bankCardToUse.get().fundsAmount < seatToBePurchased.get().getTicketPrice()) {
            logger.info("Insufficient funds on card: " + bankCardToUse.get().getUserCardID());
            return new ResponseEntity<>(MessageConstants.NO_FUNDS, HttpStatus.PRECONDITION_FAILED);
        }
        // if everything goes well...

        bankCardToUse.get().subtractAmount(seatToBePurchased.get().getTicketPrice());
        seatToBePurchased.get().setReserved(true);
        seatToBePurchased.get().setPurchasedByUser(ticketPurchaser.get());
        ticketPurchaser.get().addSeat(seatToBePurchased.get());

        bankCardRepository.save(bankCardToUse.get());
        seatRepository.save(seatToBePurchased.get());
        userRepository.save(ticketPurchaser.get());

        logger.info("Successfully purchased ticket: " + ticketPurchaser.get().getUserEmail() + " " + eventToPurchaseFrom.get().getEventTitle());
        return new ResponseEntity<>(MessageConstants.OK_RESPONSE_EVENT, HttpStatus.OK);
    }

    public Long getCurrentTimeLong() {
        Calendar currentDate = Calendar.getInstance();
        return currentDate.getTimeInMillis();
    }

    public ResponseEntity<ResponseContainer> createEvent(EventContainer newEvent) {
        EventData eventToCreate = new EventData(newEvent.eventTitle, newEvent.eventLocation, newEvent.eventStartTimeStamp, newEvent.eventEndTimeStamp);
        eventDataRepository.save(eventToCreate);

        List<Seat> seatsList = new ArrayList<>();
        for (SeatContainer seatContainer : newEvent.getSeatsToAdd()) {
            Seat temp = new Seat(eventToCreate, seatContainer.getTicketPrice(), seatContainer.isReserved());
            seatsList.add(temp);
        }
        seatRepository.saveAll(seatsList);
        eventToCreate.addSeats(seatsList);
        eventDataRepository.save(eventToCreate);

        return new ResponseEntity<>(MessageConstants.EVENT_CREATED_OK, HttpStatus.OK);
    }
}
