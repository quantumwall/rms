package org.quantum.rms.services;

import java.util.List;
import java.util.logging.Logger;
import org.quantum.rms.models.Route;
import org.quantum.rms.repositories.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RouteService {

    private final RouteRepository routeRepository;
    private final Logger log = Logger.getLogger(getClass().getSimpleName());

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public Route findByBillNumber(int billNumber) {
        return routeRepository.findByBillNumber(billNumber).orElse(null);
    }
    
    public Route findById(long id) {
        return routeRepository.findById(id).orElse(null);
    }
    
    @Transactional
    public void save(Route route) {
        routeRepository.save(route);
    }
}
