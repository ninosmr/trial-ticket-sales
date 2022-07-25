package com.nick.rodgers.TRIAL.CORE.USER;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BankCard {
    @Id
    @GeneratedValue
    public Long userCardID;

    public Long cardNumber;

    public int cardCvc;

    public String nameOnCard;

    public Long fundsAmount;

    public String fundsCurrency;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User cardOwner;

    public BankCard(Long cardNumber, int cardCvc, Long fundsAmount, String fundsCurrency, User cardOwner) {
        this.cardNumber = cardNumber;
        this.cardCvc = cardCvc;
        this.nameOnCard = cardOwner.getUserName();
        this.fundsAmount = fundsAmount;
        this.fundsCurrency = fundsCurrency;
        this.cardOwner = cardOwner;
    }

    public void subtractAmount(Long costOfPurchase) {
        setFundsAmount(fundsAmount - costOfPurchase);
    }
}
