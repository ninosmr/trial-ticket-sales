package com.nick.rodgers.TRIAL.API.CONTAINERS;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventContainer {
    public String eventTitle;
    public String eventLocation;
    public Long eventStartTimeStamp;
    public Long eventEndTimeStamp;
    public List<SeatContainer> seatsToAdd;
}
