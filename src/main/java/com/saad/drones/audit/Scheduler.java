package com.saad.drones.audit;

import com.saad.drones.services.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Scheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DroneService service;

    @Scheduled(cron = "${cron.expression}")
    public void logDronesBatteryLevels() {
        logger.info("----------------------Drone Battery Check Job START-------------------------");
        service.getAll(0, (int) service.count()).forEach(e -> logger.info(e.reportDroneBattery()));
        logger.info("-----------------------Drone Battery Check Job END--------------------------");
    }

}
