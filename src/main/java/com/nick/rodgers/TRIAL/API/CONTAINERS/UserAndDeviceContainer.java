package com.nick.rodgers.TRIAL.API.CONTAINERS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAndDeviceContainer {
    String newUserName;
    String newUserEmail;
    String newDeviceHash;
}
