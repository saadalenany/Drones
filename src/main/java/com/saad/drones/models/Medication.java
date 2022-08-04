package com.saad.drones.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "medication")
@Getter
@Setter
@ToString
public class Medication {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotBlank
    @Pattern(regexp ="([A-Za-z0-9\\-\\_]+)", message = "Only letters, numbers, ‘-‘ and ‘_’ are allowed")
    @Column(name="name")
    private String name;

    @NotBlank
    @Max(500)
    @Min(0)
    @Column(name="weight")
    private Integer weight = 0;

    @NotBlank
    @Pattern(regexp ="([A-Z0-9\\_]+)", message = "Only upper case letters, numbers and ‘_’ are allowed")
    @Column(name="code")
    private String code;

    @Lob
    @Column(name="image")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;

    public String getBase64Image() {
        if (this.image != null) {
            byte[] imgBytesAsBase64 = Base64.encodeBase64(this.image);
            String imgDataAsBase64 = new String(imgBytesAsBase64);
            return "data:image/jpg;base64," + imgDataAsBase64;
        }
        return null;
    }
}
