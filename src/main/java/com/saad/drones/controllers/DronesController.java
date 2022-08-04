package com.saad.drones.controllers;

import com.saad.drones.models.Drone;
import com.saad.drones.models.enums.State;
import com.saad.drones.services.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drones")
public class DronesController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DroneService service;

    @GetMapping(value = "/{droneId}")
    public String getDroneById(Model model, @PathVariable String droneId) {
        Drone drone = null;
        try {
            drone = service.get(droneId);
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("drone", drone);
        return "drone";
    }


    @GetMapping(value = {"/add"})
    public String showAddDrone(Model model) {
        Drone drone = new Drone();
        model.addAttribute("add", true);
        model.addAttribute("drone", drone);

        return "drone-edit";
    }

    @PostMapping(value = "/add")
    public String addDrone(Model model,
                             @ModelAttribute("drone") Drone drone) {
        try {
            if (drone.getBatteryCapacity() < 25 && drone.getState() == State.LOADING) {
                throw new RuntimeException("Drone can't be in LOADING state while battery capacity below 25%");
            }
            Drone newDrone = service.save(drone);
            return "redirect:/drones/" + String.valueOf(newDrone.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("drone", drone);
            model.addAttribute("add", true);
            return "drone-edit";
        }
    }


    @GetMapping(value = {"/{droneId}/edit"})
    public String showEditDrone(Model model, @PathVariable String droneId) {
        Drone drone = null;
        try {
            drone = service.get(droneId);
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("drone", drone);
        return "drone-edit";
    }

    @PostMapping(value = {"/{droneId}/edit"})
    public String updateDrone(Model model,
                                @PathVariable String droneId,
                                @ModelAttribute("drone") Drone drone) {
        try {
            drone.setId(droneId);
            if (drone.getBatteryCapacity() < 25 && drone.getState() == State.LOADING) {
                throw new RuntimeException("Drone can't be in LOADING state while battery capacity below 25%");
            }
            service.update(drone);
            return "redirect:/drones/" + String.valueOf(drone.getId());
        } catch (Exception ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("drone", drone);
            model.addAttribute("droneId", drone.getId());
            model.addAttribute("add", false);
            return "drone-edit";
        }
    }


    @GetMapping(value = {"/{droneId}/delete"})
    public String showDeleteDroneById(
            Model model, @PathVariable String droneId) {
        Drone drone = null;
        try {
            drone = service.get(droneId);
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("allowDelete", true);
        model.addAttribute("drone", drone);
        return "drone";
    }

    @PostMapping(value = {"/{droneId}/delete"})
    public String deleteDroneById(
            Model model, @PathVariable String droneId) {
        try {
            service.delete(droneId);
            return "redirect:/drones";
        } catch (RuntimeException ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return "drone";
        }
    }
}
