package com.nick.rodgers.TRIAL.CORE.REPOS;

import com.nick.rodgers.TRIAL.CORE.EVENT.EventData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventDataRepository extends JpaRepository<EventData, Long> {
    List<EventData> findByEventTitle(String eventName);
}
