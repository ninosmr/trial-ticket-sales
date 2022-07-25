package com.nick.rodgers.TRIAL.CORE.USER;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Device {

    @Id
    @GeneratedValue
    public Long userDeviceID;

    private String deviceHash;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User deviceOwner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "token_id")
    private UserToken deviceUserToken;

    public Device(String deviceHash, User deviceOwner) {
        this.deviceHash = deviceHash;
        this.deviceOwner = deviceOwner;
    }
}
