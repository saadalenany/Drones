package com.saad.drones.services;

import com.saad.drones.models.Drone;
import com.saad.drones.repos.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneService implements ObjectService<Drone> {

    @Autowired
    private DroneRepository repository;

    @Override
    public Drone save(Drone o) {
        return repository.save(o);
    }

    @Override
    public Drone update(Drone o) {
        if (repository.existsById(o.getId())) {
            return repository.save(o);
        }
        throw new RuntimeException(String.format("No Drone found with such ID [%s]", o.getId()));
    }

    @Override
    public Drone delete(String id) {
        Drone drone = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Drone found with such ID [%s]", id)));
        repository.delete(drone);
        return drone;
    }

    @Override
    public Drone get(String id) throws RuntimeException {
        return repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Drone found with such ID [%s]", id)));
    }

    @Override
    public List<Drone> getAll(int page, int rows) {
        return repository.findAll(PageRequest.of(page, rows)).getContent();
    }

    public long count() {
        return repository.count();
    }
}
