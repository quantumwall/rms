package org.quantum.rms.controllers;

import jakarta.validation.Valid;
import org.quantum.rms.models.Cargo;
import org.quantum.rms.models.Customer;
import org.quantum.rms.models.Driver;
import org.quantum.rms.models.Route;
import org.quantum.rms.services.CustomerService;
import org.quantum.rms.services.DriverService;
import org.quantum.rms.services.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/routes"})
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

    @GetMapping("/add")
    public String add(Model model) {
        var route = new Route();
        route.setCustomer(new Customer());
        route.setDriver(new Driver());
        route.setCargo(new Cargo());
        model.addAttribute("route", route);
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("drivers", driverService.findAll());
        return "/routes/edit";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("route", routeService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("drivers", driverService.findAll());
        return "/routes/edit";
    }

    @PostMapping
    public String create(@ModelAttribute("route") @Valid Route route,
                         BindingResult bindResult,
                         Model model) {
        if (bindResult.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("drivers", driverService.findAll());
            return "/routes/edit";
        }
        routeService.save(route);
        return "redirect:/routes";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") long id,
                         @ModelAttribute("route") @Valid Route route,
                         BindingResult bindResult,
                         Model model) {
        if (bindResult.hasErrors()) {
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("drivers", driverService.findAll());
            return "/routes/edit";
        }
        routeService.update(id, route);
        return "redirect:/routes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        routeService.delete(id);
        return "redirect:/routes";
    }
}
