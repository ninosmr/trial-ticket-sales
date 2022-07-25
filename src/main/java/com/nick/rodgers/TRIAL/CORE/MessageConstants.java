package com.nick.rodgers.TRIAL.CORE;

import com.nick.rodgers.TRIAL.API.CONTAINERS.ResponseContainer;

public class MessageConstants {
    public static final ResponseContainer DEVICE_EXISTS = new ResponseContainer("This device already exists in the system. Just give back the stolen phone", "D001");
    public static final ResponseContainer USER_EXISTS = new ResponseContainer("This user's email is already registered. Just add an Xx420 to the end and try again.", "U001");
    public static final ResponseContainer USER_DOES_NOT_EXIST = new ResponseContainer("On this episode of The Twilight Zone, a man attempts to delete himself, without even existing in the first place", "U002");
    public static final ResponseContainer OK_RESPONSE_USER = new ResponseContainer("User and device created successfully!", "U000");
    public static final ResponseContainer DELETE_SUCCESS = new ResponseContainer("User and all associated items deleted successfully. Go tell ticketmaster she can have you.", "U003");
    public static final ResponseContainer OK_RESPONSE_EVENT = new ResponseContainer("Ticket reserved successfully. Enjoy the show!", "E000");
    public static final ResponseContainer NO_CARD = new ResponseContainer("No card registered with this ID. Are you sure this belongs to you?", "C000");
    public static final ResponseContainer NO_FUNDS = new ResponseContainer("Insufficient funds on card. To remedy this issue: www.linkedin.com/jobs", "C001");
    public static final ResponseContainer NO_SEAT = new ResponseContainer("This seat has already been reserved. Please pick another one, preferably next to the one you already picked so that you can annoy your neighbor who took your lovely seat.", "S001");
    public static final ResponseContainer NO_EVENT = new ResponseContainer("The event doesn't exist, are you sure you didn't mean to type TicketMaster in the URL?", "E001");
    public static final ResponseContainer EVENT_STARTED = new ResponseContainer("The event has already started, no further ticket sales are permitted. You can still try to jump the fence though, they skimped on security.", "E002");
    public static final ResponseContainer NO_USER = new ResponseContainer("User not found. Hacker detected.", "U999");
    public static final ResponseContainer NOT_YOUR_CARD = new ResponseContainer("This card doesn't belong to you, put it back in your mom's purse.", "C002");

    public static final ResponseContainer INVALID_TOKEN = new ResponseContainer("Token Invalid, get out of my system.", "T1000");
    public static final ResponseContainer OK_RESPONSE_CARD = new ResponseContainer("New card registered successfully! It'll be empty before you know it ;)", "C003");

    public static final ResponseContainer EVENT_CREATED_OK = new ResponseContainer("New event successfully created! Hope you make a lot of money because we get commission!", "E003");
    public static final ResponseContainer UNKNOWN_ERROR = new ResponseContainer("Unknown Error occurred. Please don't contact the developer, his ego is fragile enough as is.", "U999");

}
