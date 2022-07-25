package com.nick.rodgers.TRIAL.CORE.EVENT;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EventData {
    @Id
    @GeneratedValue
    public Long eventId;

    public String eventTitle;

    public String eventLocation;

    public Long eventStartTimeStamp;

    public Long eventEndTimeStamp;

    @OneToMany
    private List<Seat> eventSeats = new ArrayList<>();

    public EventData(String eventTitle, String eventLocation, long eventStartTimeStamp, long eventEndTimeStamp) {
        this.eventTitle = eventTitle;
        this.eventLocation = eventLocation;
        this.eventStartTimeStamp = eventStartTimeStamp;
        this.eventEndTimeStamp = eventEndTimeStamp;
    }

    public void addSeats(List<Seat> seatsList) {
        this.eventSeats.addAll(seatsList);
    }
}
