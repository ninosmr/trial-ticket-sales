package com.nick.rodgers.TRIAL.CORE.REPOS;

import com.nick.rodgers.TRIAL.CORE.USER.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
}
