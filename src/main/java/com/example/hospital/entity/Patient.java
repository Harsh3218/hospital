package com.example.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    private int age;
    private String address;
    private String phone;
    private String email;
    private LocalDateTime appointmentDateTime;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
