package com.nick.rodgers.TRIAL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

//    @Bean
//    CommandLineRunner initDatabase(
//            UserRepository userRepository,
//            BankCardRepository bankCardRepository,
//            DeviceRepository deviceRepository,
//            EventDataRepository eventDataRepository,
//            SeatRepository seatRepository) {
//
//        User testUser = new User("Nick Rodgers", "ninosmr@gmail.com");
//        User testPista = new User("Joska Pista", "nirodgers@iolani.org");
//
//        BankCard testCard = new BankCard(123234123434L, 123, 1000000L, "HUF", testUser);
//        BankCard testPistaCard = new BankCard(643223123434L, 123, 10000L, "HUF", testPista);
//
//        Device testDevice = new Device("ahsdhasdhashdhasdhashd", testUser);
//        Device testDevice2 = new Device("hjkjhslkjhdkljhdfs", testUser);
//        Device testPistaDevice = new Device("yqwioeryoqiweruy", testPista);
//
//        EventData testEvent = new EventData("Test Event", "BalatonKenese", 123876123L, 123876223L);
//        EventData testSziget = new EventData("Sziget 22", "Hajos Sziget", 143876123L, 143876223L);
//
//
//        Seat testSeat1 = new Seat(testEvent, (long) 1000, false);
//        Seat testSeat2 = new Seat(testEvent, (long) 2000, false);
//        Seat testSeat3 = new Seat(testEvent, (long) 2000, false);
//        Seat testSeatSziget = new Seat(testSziget, (long) 2000, false);
//        Seat testSeatSziget1 = new Seat(testSziget, (long) 2000, false);
//        Seat testSeatSziget2 = new Seat(testSziget, (long) 10000, false);
//
//        return args -> {
//            userRepository.saveAll(Arrays.asList(testUser, testPista));
//            bankCardRepository.saveAll(Arrays.asList(testCard, testPistaCard));
//            deviceRepository.saveAll(Arrays.asList(testDevice, testDevice2, testPistaDevice));
//            eventDataRepository.saveAll(Arrays.asList(testEvent, testSziget));
//            seatRepository.saveAll(Arrays.asList(testSeat1, testSeat2, testSeat3, testSeatSziget, testSeatSziget1, testSeatSziget2));
//
//            testUser.addBankCard(testCard);
//            testUser.addDevice(testDevice);
//            testUser.addDevice(testDevice2);
//
//            testPista.addBankCard(testPistaCard);
//            testPista.addDevice(testPistaDevice);
//
//            testEvent.addSeats(Arrays.asList(testSeat1, testSeat2, testSeat3));
//            testSziget.addSeats(Arrays.asList(testSeatSziget, testSeatSziget1, testSeatSziget2));
//
//            userRepository.saveAll(Arrays.asList(testUser, testPista));
//            eventDataRepository.saveAll(Arrays.asList(testEvent, testSziget));
//        };
//    }
}
