package com.nick.rodgers.TRIAL.API;

import com.nick.rodgers.TRIAL.API.CONTAINERS.BankCardContainer;
import com.nick.rodgers.TRIAL.API.CONTAINERS.ResponseContainer;
import com.nick.rodgers.TRIAL.API.CONTAINERS.UserAndDeviceContainer;
import com.nick.rodgers.TRIAL.CORE.MessageConstants;
import com.nick.rodgers.TRIAL.CORE.SERVICES.UserService;
import com.nick.rodgers.TRIAL.CORE.SERVICES.ValidatorService;
import com.nick.rodgers.TRIAL.CORE.USER.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ValidatorService validatorService;

    @GetMapping("/getUsers")
    ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/getUserById/{id}")
    ResponseEntity<Optional<User>> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(userService.findUserById(Long.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping("/registerNewUser")
    ResponseEntity<ResponseContainer> registerNewUser(@RequestBody @NotNull Map<String, UserAndDeviceContainer> payload) {
        return userService.registerNewUser(payload.get("New"));
    }

    @PostMapping("/registerNewCard")
    public ResponseEntity<ResponseContainer> registerNewCard(@RequestHeader @NotNull Map<String, String> token, @RequestBody Map<String, BankCardContainer> payload) {

        if (validatorService.validateUserToken(token)) {
            return userService.registerNewCard(payload.get("Card"));
        }
        return new ResponseEntity<>(MessageConstants.INVALID_TOKEN, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/removeUser/{id}")
    ResponseEntity<ResponseContainer> removeUserById(@PathVariable String id) {
        return userService.removeUser(Long.valueOf(id));
    }


}
