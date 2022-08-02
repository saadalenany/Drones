package com.saad.drones.services;

import com.saad.drones.models.Medication;
import com.saad.drones.repos.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService implements ObjectService<Medication> {

    @Autowired
    private MedicationRepository repository;

    @Override
    public Medication save(Medication o) {
        return repository.save(o);
    }

    @Override
    public Medication update(Medication o) {
        if (repository.existsById(o.getId())) {
            return repository.save(o);
        }
        throw new RuntimeException(String.format("No Medication found with such ID [%s]", o.getId()));
    }

    @Override
    public Medication delete(String id) {
        Medication medication = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Medication found with such ID [%s]", id)));
        repository.delete(medication);
        return medication;
    }

    @Override
    public Medication get(String id) throws RuntimeException {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Medication found with such ID [%s]", id)));
    }

    @Override
    public List<Medication> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }
}
