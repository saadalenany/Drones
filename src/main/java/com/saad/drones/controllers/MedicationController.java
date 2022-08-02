package com.saad.drones.controllers;

import com.saad.drones.services.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medications")
public class MedicationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MedicationService service;

}
