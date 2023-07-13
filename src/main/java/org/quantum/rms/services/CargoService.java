package org.quantum.rms.services;

import java.util.List;
import java.util.Objects;

import org.quantum.rms.models.Cargo;
import org.quantum.rms.repositories.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
	this.cargoRepository = cargoRepository;
    }
    
    public List<Cargo> findAll() {
	return cargoRepository.findAll();
    }
    
    public Cargo findById(Long id) {
	return cargoRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public Cargo save(Cargo cargo) {
	if (Objects.nonNull(cargo)) {
	    cargoRepository.save(cargo);
	}
	return cargo;
    }
    
    @Transactional
    public Cargo delete(Cargo cargo) {
	if (Objects.nonNull(cargo)) {
	    cargoRepository.save(cargo);
	}
	return cargo;
    }
    
}
