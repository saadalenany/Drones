package com.saad.drones.repos;

import com.saad.drones.models.Medication;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends PagingAndSortingRepository<Medication, String>,
        JpaSpecificationExecutor<Medication> {
}
