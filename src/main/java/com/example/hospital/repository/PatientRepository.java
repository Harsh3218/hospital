package com.example.hospital.repository;

import com.example.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByDoctorIdAndAppointmentDateTime(Long doctorId, LocalDateTime appointmentDateTime);
    boolean existsByDoctorIdAndNameAndAppointmentDateTime(Long doctorId, String name, LocalDateTime appointmentDateTime);
    boolean existsByDoctorIdAndAppointmentDateTime(Long doctorId, LocalDateTime nextSlot);
}
