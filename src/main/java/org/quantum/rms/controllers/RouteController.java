package org.quantum.rms.controllers;

import java.util.logging.Logger;
import org.quantum.rms.models.Cargo;
import org.quantum.rms.models.Route;
import org.quantum.rms.services.CustomerService;
import org.quantum.rms.services.DriverService;
import org.quantum.rms.services.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/routes")
public class RouteController {
    
    private final RouteService routeService;
    private final DriverService driverService;
    private final CustomerService customerService;
    private final Logger log = Logger.getLogger(getClass().getSimpleName());
    
    public RouteController(RouteService routeService, DriverService driverService, CustomerService customerService) {
        this.routeService = routeService;
        this.driverService = driverService;
        this.customerService = customerService;
    }
    
    @GetMapping
    public String index(Model model) {
        model.addAttribute("routes", routeService.findAll());
        return "/routes/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id", required = true) long id, Model model) {
        model.addAttribute("route", routeService.findById(id));
        return "/routes/show";
    }
    
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("route", new Route());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("drivers", driverService.findAll());
        return "/routes/create";
    }
    
    @PostMapping
    public String create(@ModelAttribute("route") Route route,
                         @RequestParam("customer_id") long customerId,
                         @RequestParam("driver_id") long driverId,
                         @RequestParam("cargo_name") String cargoName,
                         @RequestParam("cargo_weight") int cargoWeight) {
        var customer = customerService.findById(customerId);
        var driver = driverService.findById(driverId);
        var cargo = new Cargo();
        log.info(String.format("\n%s\n", customer));
        log.info(String.format("\n%s\n", driver));
        cargo.setGoods(cargoName);
        cargo.setWeight(cargoWeight);
        log.info(String.format("\n%s\n", cargo));
        route.setCustomer(customer);
        route.setDriver(driver);
        route.setCargo(cargo);
        routeService.save(route);
        return "redirect:/routes";
    }
}