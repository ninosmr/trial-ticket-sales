package com.nick.rodgers.TRIAL.API.CONTAINERS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeatContainer {
    public boolean reserved;
    public Long ticketPrice;
}
