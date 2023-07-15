package org.quantum.rms.service;

import java.util.List;
import java.util.Objects;

import org.quantum.rms.model.Driver;
import org.quantum.rms.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
	this.driverRepository = driverRepository;
    }

    public List<Driver> findAll() {
	return driverRepository.findAll();
    }

    public Driver findById(long id) {
	return driverRepository.findById(id).orElse(null);
    }

    @Transactional
    public Driver save(Driver driver) {
	if (Objects.nonNull(driver)) {
	    driverRepository.save(driver);
	}
	return driver;
    }

    @Transactional
    public Driver delete(Driver driver) {
	if (Objects.nonNull(driver)) {
	    driverRepository.delete(driver);
	}
	return driver;
    }
}
