package org.quantum.rms.service;

import java.util.List;
import java.util.Objects;

import org.quantum.rms.model.Route;
import org.quantum.rms.repository.RouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RouteService {

    private final RouteRepository routeRepository;
    private final CustomerService customerService;
    private final DriverService driverService;
    private final SecurityService securityService;

    public RouteService(RouteRepository routeRepository, CustomerService customerService, DriverService driverService, SecurityService securityService) {
	this.routeRepository = routeRepository;
	this.customerService = customerService;
	this.driverService = driverService;
	this.securityService = securityService;
    }

    public List<Route> findAll(String filter) {
	Long userId = securityService.getAuthenticatedUser().map(u -> u.getId()).orElse(null);
	if (Objects.isNull(filter) || filter.isBlank()) {
	    return routeRepository.findByUser_Id(userId);
	}
	return routeRepository.search(filter, userId);
    }

    public Route findById(long id) {
	return routeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Route route) {
//        var customer = customerService.findById(route.getCustomer().getId());
//        var driver = driverService.findById(route.getDriver().getId());
//        route.setCustomer(customer);
//        route.setDriver(driver);
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
	    updRoute.getCargo().setName(route.getCargo().getName());
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
