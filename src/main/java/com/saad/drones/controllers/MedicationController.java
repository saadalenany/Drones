package com.saad.drones.controllers;

import com.saad.drones.models.Drone;
import com.saad.drones.models.Medication;
import com.saad.drones.services.DroneService;
import com.saad.drones.services.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/medications")
public class MedicationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MedicationService medicationService;

    @Autowired
    DroneService droneService;

    @GetMapping(value = {"/add"})
    public String showAddMedication(Model model, @RequestParam(name = "droneId") String droneId) {
        Medication medication = new Medication();
        model.addAttribute("add", true);
        model.addAttribute("medication", medication);
        model.addAttribute("drone", droneService.get(droneId));

        return "medication-edit";
    }

    @PostMapping(value = {"/add"})
    public String addMedication(Model model,
                                @RequestParam("file") MultipartFile file,
                                @RequestParam("droneId") String droneId,
                                @ModelAttribute("medication") Medication medication) {
        try {
            medication.setImage(file.getBytes());
            medication.setDrone(droneService.get(droneId));
            Drone drone = droneService.get(droneId);

            //Check OverWeight
            checkOverweight(drone, medication, 0);

            medicationService.save(medication);
            return "redirect:/drones/" + String.valueOf(droneId);
        } catch (RuntimeException | IOException ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);

            model.addAttribute("add", true);
            model.addAttribute("medication", medication);
            model.addAttribute("drone", droneService.get(droneId));
            return "medication-edit";
        }
    }


    @GetMapping(value = {"/edit"})
    public String showEditMedication(Model model, @RequestParam("medicationId") String medicationId) {
        Medication medication = null;
        try {
            medication = medicationService.get(medicationId);
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("add", false);
        model.addAttribute("medication", medication);
        assert medication != null;
        model.addAttribute("drone", medication.getDrone());
        return "medication-edit";
    }

    @PostMapping(value = {"/edit"})
    public String updateMedication(Model model,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam("medicationId") String medicationId,
                                   @RequestParam("droneId") String droneId,
                                   @ModelAttribute("medication") Medication medication) {
        Drone drone;
        try {
            Medication updatedMedication = medicationService.get(medicationId);
            drone = droneService.get(droneId);
            //Check OverWeight
            checkOverweight(drone, medication, updatedMedication.getWeight());

            if (!file.isEmpty()) {
                updatedMedication.setImage(file.getBytes());
            }
            updatedMedication.setName(medication.getName());
            updatedMedication.setCode(medication.getCode());
            updatedMedication.setWeight(medication.getWeight());

            medicationService.update(updatedMedication);
            return "redirect:/drones/" + String.valueOf(droneId);
        } catch (RuntimeException | IOException ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            drone = droneService.get(droneId);
            model.addAttribute("drone", drone);
            return "drone";
        }
    }


    @PostMapping(value = {"/delete"})
    public String deleteMedicationById(
            Model model, @RequestParam("medicationId") String medicationId, @RequestParam("droneId") String droneId) {
        try {
            medicationService.delete(medicationId);
            return "redirect:/drones/" + String.valueOf(droneId);
        } catch (RuntimeException ex) {
            String errorMessage = ex.getMessage();
            logger.error(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("drone", droneService.get(droneId));
            return "drone";
        }
    }


    private void checkOverweight(Drone drone, Medication medication, int selfWeight) throws RuntimeException {
        int totalWeight = drone.getTotalMedicationsWeight(selfWeight);
        int newWeight = totalWeight + medication.getWeight();
        if (newWeight > drone.getWeightLimit()) {
            String overweight = String.format("Medication with weight <strong>%s gms</strong> will increase total medications weight to be <strong>%s gms</strong> which is more than the Drone weight limit of <strong>%s gms</strong>",
                    medication.getWeight(), newWeight, drone.getWeightLimit());
            throw new RuntimeException(overweight);
        }
    }

}
