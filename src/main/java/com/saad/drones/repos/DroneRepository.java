package com.saad.drones.repos;

import com.saad.drones.models.Drone;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends PagingAndSortingRepository<Drone, String>,
        JpaSpecificationExecutor<Drone> {
}
