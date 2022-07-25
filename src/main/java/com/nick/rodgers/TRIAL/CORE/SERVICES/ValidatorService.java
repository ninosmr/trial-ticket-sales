package com.nick.rodgers.TRIAL.CORE.SERVICES;

import com.nick.rodgers.TRIAL.CORE.REPOS.DeviceRepository;
import com.nick.rodgers.TRIAL.CORE.REPOS.UserRepository;
import com.nick.rodgers.TRIAL.CORE.USER.Device;
import com.nick.rodgers.TRIAL.CORE.USER.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ValidatorService {

    private UserRepository userRepository;
    private DeviceRepository deviceRepository;


    @Autowired
    public ValidatorService(
            UserRepository userRepository,
            DeviceRepository deviceRepository
    ) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }
    private static final Logger logger = LoggerFactory.getLogger(ValidatorService.class);


    public boolean validateUserToken(Map<String, String> userToken) {
        Optional<User> userToValidate = userRepository.findById(Long.valueOf(userToken.get("requesterid")));
        Optional<Device> deviceToValidate = deviceRepository.findById(Long.valueOf(userToken.get("requesterdeviceid")));

        if (!userToValidate.get().getDevices().contains(deviceToValidate.get())) {
            logger.debug("Token validation failed for user: " + userToValidate.get().getUserID());
            return false;
        }

        return deviceToValidate.get().getDeviceUserToken().getUserTokenHash().equals(userToken.get("usertoken"));
    }
}
