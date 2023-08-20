package org.quantum.rms.repository;

import java.util.List;

import org.quantum.rms.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    List<Driver> findAllByUserId(Long userId);
}
