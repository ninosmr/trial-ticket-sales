package com.nick.rodgers.TRIAL.CORE.USER;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserToken {

    @Id
    @GeneratedValue
    private int tokenId;

    private String userTokenHash;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserToken(String userTokenHash, Device device, User user) {
        this.userTokenHash = userTokenHash;
        this.device = device;
        this.user = user;
    }


}
