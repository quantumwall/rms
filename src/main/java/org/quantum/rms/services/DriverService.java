package org.quantum.rms.services;

import java.util.List;
import org.quantum.rms.models.Driver;
import org.quantum.rms.repositories.DriverRepository;
import org.springframework.stereotype.Service;

@Service
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
}
