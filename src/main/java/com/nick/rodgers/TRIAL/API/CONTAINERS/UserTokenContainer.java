package com.nick.rodgers.TRIAL.API.CONTAINERS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenContainer {
    Long requesterId;
    Long requesterDeviceId;
    String requesterToken;
}
