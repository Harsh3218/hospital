package com.example.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String speciality;

    @ElementCollection
    @CollectionTable(name = "doctor_appointments", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "appointments")
    private List<LocalDateTime> appointments = new ArrayList<>();

}
