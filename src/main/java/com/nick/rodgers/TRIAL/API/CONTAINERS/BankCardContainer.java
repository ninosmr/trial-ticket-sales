package com.nick.rodgers.TRIAL.API.CONTAINERS;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankCardContainer {
    Long ownerId;
    String ownerNameOnCard;
    String cardNumber;
    String amountOnCard;
    String fundsCurrency;
    String cardCvc;

}
