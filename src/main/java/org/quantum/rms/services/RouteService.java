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
    private final CustomerService customerService;
    private final DriverService driverService;
    private final Logger log = Logger.getLogger(getClass().getSimpleName());
    
    public RouteService(RouteRepository routeRepository, CustomerService customerService, DriverService driverService) {
        this.routeRepository = routeRepository;
        this.customerService = customerService;
        this.driverService = driverService;
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
    
    @Transactional
    public void update(long id, Route route) {
        var routeToUpdate = routeRepository.findById(id);
        if (routeToUpdate.isPresent()) {
            var updRoute = routeToUpdate.get();
            var customer = customerService.findById(route.getCustomer().getId());
            var driver = driverService.findById(route.getDriver().getId());
            updRoute.setCustomer(customer);
            updRoute.setDriver(driver);
            updRoute.getCargo().setGoods(route.getCargo().getGoods());
            updRoute.getCargo().setWeight(route.getCargo().getWeight());
            updRoute.setBillNumber(route.getBillNumber());
            updRoute.setDepartureCity(route.getDepartureCity());
            updRoute.setDestinationCity(route.getDestinationCity());
            updRoute.setPaid(route.isPaid());
            updRoute.setPrice(route.getPrice());
            updRoute.setShipmentDate(route.getShipmentDate());
        }
    }
    
    @Transactional
    public void delete(long id) {
        routeRepository.deleteById(id);
    }
}
