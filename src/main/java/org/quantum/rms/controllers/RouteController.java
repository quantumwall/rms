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
import org.springframework.web.bind.annotation.PatchMapping;
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

    // TODO create cargo, driver, customer, inject they in new route and pass route to model
    // this is for implementation of new create method
    // TODO is it possible to use edit page for create new route?
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("route", new Route());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("drivers", driverService.findAll());
        return "/routes/create";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("route", routeService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("drivers", driverService.findAll());
        return "/routes/edit";
    }

    // TODO catch only route object from view
    @PostMapping
    public String create(@ModelAttribute("route") Route route,
                         @RequestParam("customer_id") long customerId,
                         @RequestParam("driver_id") long driverId,
                         @RequestParam("cargo_name") String cargoName,
                         @RequestParam("cargo_weight") int cargoWeight) {
        var customer = customerService.findById(customerId);
        var driver = driverService.findById(driverId);
        var cargo = new Cargo();
        cargo.setGoods(cargoName);
        cargo.setWeight(cargoWeight);
        route.setCustomer(customer);
        route.setDriver(driver);
        route.setCargo(cargo);
        routeService.save(route);
        return "redirect:/routes";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("route") Route route) {
        routeService.update(id, route);
        return "redirect:/routes";
    }
}
