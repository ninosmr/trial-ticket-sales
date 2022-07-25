package com.nick.rodgers.TRIAL.API.ERRORHANDLING;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(Long id) {
        super("Could not find event with ID: " + id);
    }

    public EventNotFoundException(String name) {
        super("Could not find event with name: " + name);
    }
}
