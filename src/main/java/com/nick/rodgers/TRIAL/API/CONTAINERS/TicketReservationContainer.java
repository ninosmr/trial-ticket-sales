package com.nick.rodgers.TRIAL.API.CONTAINERS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketReservationContainer {
    Long purchaserId;
    Long purchaserCardId;
    Long seatId;
    Long eventId;
}
