package com.nick.rodgers.TRIAL.CORE.EVENT;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nick.rodgers.TRIAL.CORE.USER.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seat {
    @Id
    @GeneratedValue
    public Long seatId;

    public boolean reserved;

    public Long ticketPrice;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    public EventData eventData;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User purchasedByUser;

    public Seat(EventData eventData, Long ticketPrice, boolean reserved) {
        this.eventData = eventData;
        this.ticketPrice = ticketPrice;
        this.reserved = reserved;
    }
}
