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

    public void bookAppointment(LocalDateTime appointment) {
        appointments.add(appointment);
    }

    public void cancelAppointment(LocalDateTime appointment) {
        appointments.remove(appointment);
    }

    public void updateAppointment(LocalDateTime appointment) {
        appointments.set(appointments.indexOf(appointment), appointment);
    }

    public boolean isBusy(LocalDateTime appointment) {
        return appointments.contains(appointment);
    }

}
