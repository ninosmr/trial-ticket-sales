package com.nick.rodgers.TRIAL.CORE.REPOS;

import com.nick.rodgers.TRIAL.CORE.USER.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByDeviceHash(String deviceHash);
}
