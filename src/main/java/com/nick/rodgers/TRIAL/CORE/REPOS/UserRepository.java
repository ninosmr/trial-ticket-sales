package com.nick.rodgers.TRIAL.CORE.REPOS;

import com.nick.rodgers.TRIAL.CORE.USER.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserEmail(String userEmail);
}
