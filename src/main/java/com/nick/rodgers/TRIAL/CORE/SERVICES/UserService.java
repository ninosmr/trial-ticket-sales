package com.nick.rodgers.TRIAL.CORE.SERVICES;

import com.nick.rodgers.TRIAL.API.CONTAINERS.BankCardContainer;
import com.nick.rodgers.TRIAL.API.CONTAINERS.ResponseContainer;
import com.nick.rodgers.TRIAL.API.CONTAINERS.UserAndDeviceContainer;
import com.nick.rodgers.TRIAL.CORE.EVENT.Seat;
import com.nick.rodgers.TRIAL.CORE.MessageConstants;
import com.nick.rodgers.TRIAL.CORE.REPOS.*;
import com.nick.rodgers.TRIAL.CORE.USER.BankCard;
import com.nick.rodgers.TRIAL.CORE.USER.Device;
import com.nick.rodgers.TRIAL.CORE.USER.User;
import com.nick.rodgers.TRIAL.CORE.USER.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private UserRepository userRepository;
    private BankCardRepository bankCardRepository;
    private UserTokenRepository userTokenRepository;
    private DeviceRepository deviceRepository;


    @Autowired
    public UserService(
            UserRepository userRepository,
            BankCardRepository bankCardRepository,
            UserTokenRepository userTokenRepository,
            DeviceRepository deviceRepository
    ) {
        this.userRepository = userRepository;
        this.bankCardRepository = bankCardRepository;
        this.userTokenRepository = userTokenRepository;
        this.deviceRepository = deviceRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public boolean doesDeviceExist(String deviceHash) {
        return deviceRepository.findByDeviceHash(deviceHash) != null;
    }

    public boolean doesUserEmailExist(String userEmail) {
        return userRepository.findByUserEmail(userEmail) != null;
    }

    public void generateNewToken(Device device, User deviceOwner) {
        String tokenInputString = deviceOwner.getUserEmail() + "&" + deviceOwner.getUserID() + "&" + device.getDeviceHash();
        UserToken newToken = new UserToken(Base64.getEncoder().encodeToString(tokenInputString.getBytes()), device, deviceOwner);

        userTokenRepository.save(newToken);

        device.setDeviceUserToken(newToken);
        deviceOwner.addUserTokens(newToken);

        userRepository.save(deviceOwner);
        deviceRepository.save(device);
    }

    public ResponseEntity<ResponseContainer> registerNewUser(UserAndDeviceContainer newUserAndDevice) {
        if (doesUserEmailExist(newUserAndDevice.getNewUserEmail())) {
            logger.warn("User with this email already exists: " + newUserAndDevice.getNewUserEmail());
            return new ResponseEntity<>(MessageConstants.USER_EXISTS, HttpStatus.PRECONDITION_FAILED);
        }
        if (doesDeviceExist(newUserAndDevice.getNewDeviceHash())) {
            logger.warn("Device with this hash already exists: " + newUserAndDevice.getNewDeviceHash());
            return new ResponseEntity<>(MessageConstants.DEVICE_EXISTS, HttpStatus.PRECONDITION_FAILED);
        }

        User newUser = new User(newUserAndDevice.getNewUserName(), newUserAndDevice.getNewUserEmail());
        Device newDevice = new Device(newUserAndDevice.getNewDeviceHash(), newUser);

        userRepository.save(newUser);
        deviceRepository.save(newDevice);

        newUser.addDevice(newDevice);
        userRepository.save(newUser);

        generateNewToken(newDevice, newUser);

        logger.info("New user: " + newUser.getUserID() + ", device: " + newDevice.getUserDeviceID() + "and token created!");
        return new ResponseEntity<>(MessageConstants.OK_RESPONSE_USER, HttpStatus.OK);
    }

    public ResponseEntity<ResponseContainer> removeUser(Long userId) {
        Optional<User> userToRemove = userRepository.findById(userId);
        if (userToRemove.isEmpty()) {
            logger.error("User does not exist: " + userId);
            return new ResponseEntity<>(MessageConstants.USER_DOES_NOT_EXIST, HttpStatus.PRECONDITION_FAILED);
        }

        List<Device> devicesToRemove = userToRemove.get().getDevices();
        if (!devicesToRemove.isEmpty()) {
            deviceRepository.deleteAllInBatch(devicesToRemove);
        }

        List<UserToken> tokensToRemove = userToRemove.get().getUserTokens();
        if (!tokensToRemove.isEmpty()) {
            userTokenRepository.deleteAllInBatch(tokensToRemove);
        }

        List<BankCard> cardsToDelete = userToRemove.get().getBankCards();
        if (!cardsToDelete.isEmpty()) {
            bankCardRepository.deleteAllInBatch(cardsToDelete);
        }

        List<Seat> seatsToClear = userToRemove.get().getUserPurchasedSeats();
        if (!seatsToClear.isEmpty()) {
            for (Seat seat : seatsToClear) {
                seat.setPurchasedByUser(null);
                seat.setReserved(false);
            }
        }

        userRepository.delete(userToRemove.get());

        logger.info("User deleted successfully: " + userId);
        return new ResponseEntity<>(MessageConstants.DELETE_SUCCESS, HttpStatus.OK);
    }

    public ResponseEntity<ResponseContainer> registerNewCard(BankCardContainer newCard) {
        Optional<User> userToRegisterCardTo = userRepository.findById(newCard.getOwnerId());
        BankCard cardToCreate = new BankCard(Long.valueOf(newCard.getCardNumber()), Integer.parseInt(newCard.getCardCvc()), Long.valueOf(newCard.getAmountOnCard()), newCard.getFundsCurrency(), userToRegisterCardTo.get());
        bankCardRepository.save(cardToCreate);

        userToRegisterCardTo.get().addBankCard(cardToCreate);
        userRepository.save(userToRegisterCardTo.get());

        logger.info("New card registered successfully: " + cardToCreate.getUserCardID());
        return new ResponseEntity<>(MessageConstants.OK_RESPONSE_CARD, HttpStatus.OK);
    }
}
