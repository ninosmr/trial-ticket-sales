package com.nick.rodgers.TRIAL.CORE.USER;

import com.nick.rodgers.TRIAL.CORE.EVENT.Seat;
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
public class User {

    @Id
    @GeneratedValue
    private Long userID;

    public String userName;

    public String userEmail;

    @OneToMany
    private List<BankCard> bankCards = new ArrayList<>();

    @OneToMany
    private List<Device> devices = new ArrayList<>();

    @OneToMany
    private List<Seat> userPurchasedSeats = new ArrayList<>();

    @OneToMany
    private List<UserToken> userTokens = new ArrayList<>();

    public User(String name, String email) {
        userName = name;
        userEmail = email;
    }

    public void addBankCard(BankCard bankCard) {
        this.bankCards.add(bankCard);
    }

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void addUserTokens(UserToken userToken) {
        userTokens.add(userToken);
    }

    public void addSeat(Seat purchasedSeats) {
        this.userPurchasedSeats.add(purchasedSeats);
        purchasedSeats.setPurchasedByUser(this);
    }

}
