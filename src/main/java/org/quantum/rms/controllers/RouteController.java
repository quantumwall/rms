package org.quantum.rms.controllers;

import java.util.logging.Logger;
import org.quantum.rms.services.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final Logger log = Logger.getLogger(getClass().getSimpleName());

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("routes", routeService.findAll());
        return "index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id", required = true) long id, Model model) {
        model.addAttribute("route", routeService.findById(id));
        return "show";
    }
}
