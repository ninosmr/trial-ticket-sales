package com.nick.rodgers.TRIAL.API;

import com.nick.rodgers.TRIAL.API.CONTAINERS.EventContainer;
import com.nick.rodgers.TRIAL.API.CONTAINERS.ResponseContainer;
import com.nick.rodgers.TRIAL.API.CONTAINERS.TicketReservationContainer;
import com.nick.rodgers.TRIAL.CORE.EVENT.EventData;
import com.nick.rodgers.TRIAL.CORE.MessageConstants;
import com.nick.rodgers.TRIAL.CORE.SERVICES.EventService;
import com.nick.rodgers.TRIAL.CORE.SERVICES.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class EventController {


    @Autowired
    private EventService eventService;
    @Autowired
    private ValidatorService validatorService;

    @GetMapping("/getEvents")
    ResponseEntity<List<EventData>> allEvents() {
        return new ResponseEntity<>(eventService.findAllEvents(), HttpStatus.OK);
    }

    @GetMapping("/getEventId/{id}")
    ResponseEntity<Optional<EventData>> eventById(@PathVariable String id) {
        return new ResponseEntity<>(eventService.findEventById(Long.valueOf(id)), HttpStatus.OK);
    }

    @GetMapping("/getEventTitle/{eventTitle}")
    ResponseEntity<List<EventData>> eventsByTitle(@PathVariable String eventTitle) {
        return new ResponseEntity<>(eventService.findEventsByTitle(eventTitle), HttpStatus.OK);
    }

    @PostMapping("/buyTicket")
    public ResponseEntity<ResponseContainer> buyTicket(@RequestHeader Map<String, String> token, @RequestBody Map<String, TicketReservationContainer> payload) {
        if (validatorService.validateUserToken(token)) {
            return eventService.reserveTicket(payload.get("Purchase"));
        }
        return new ResponseEntity<>(MessageConstants.INVALID_TOKEN, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/registerNewEvent")
    public ResponseEntity<ResponseContainer> postEvent(@RequestBody Map<String, EventContainer> payload) {
        return eventService.createEvent(payload.get("Event"));
    }

}

