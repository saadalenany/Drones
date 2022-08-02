package com.saad.drones.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.saad.drones.models.enums.Model;
import com.saad.drones.models.enums.State;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "drone")
@Getter
@Setter
@ToString
public class Drone {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotBlank
    @Size(max = 100)
    @Column(name="serial_number", unique = true)
    private String serialNumber;

    @NotBlank
    @Column(name="model")
    private Model model;

    @NotBlank
    @Max(500)
    @Min(0)
    @Column(name="weight_limit")
    private Integer weightLimit = 0;

    @NotBlank
    @Max(100)
    @Min(0)
    @Column(name="battery_capacity")
    private Integer batteryCapacity = 0;

    @NotBlank
    @Column(name="state")
    private State State;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medication> medications = new ArrayList<>();

}
