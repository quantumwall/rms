package org.quantum.rms.service;

import java.util.Collections;
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
    private final SecurityService securityService;

    public DriverService(DriverRepository driverRepository, SecurityService securityService) {
	this.driverRepository = driverRepository;
	this.securityService = securityService;
    }

    public List<Driver> findAll() {
	return securityService.getAuthenticatedUser().map(u -> driverRepository.findAllByUserId(u.getId()))
		.orElse(Collections.emptyList());
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
