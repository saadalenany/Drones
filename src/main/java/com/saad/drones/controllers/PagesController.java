package com.saad.drones.controllers;

import com.saad.drones.models.Drone;
import com.saad.drones.services.DroneService;
import com.saad.drones.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PagesController {

    @Value("${app.rows-per-page}")
    private int ROW_PER_PAGE;

    @Value("${app.title}")
    private String title;

    @Autowired
    private DroneService droneService;

    @Autowired
    private MedicationService medicationService;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        model.addAttribute("title", title);

        boolean hasPrev = page > 0;
        boolean hasNext = ((page + 1) * ROW_PER_PAGE) < droneService.count();

        List<Drone> all = droneService.getAll(page, ROW_PER_PAGE);
        model.addAttribute("drones", all);
        model.addAttribute("hasPrev", hasPrev);
        model.addAttribute("prev", page - 1);
        model.addAttribute("hasNext", hasNext);
        model.addAttribute("next", page + 1);
        return "home";
    }

}
