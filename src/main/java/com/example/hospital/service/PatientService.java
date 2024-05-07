package com.example.hospital.service;

import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.entity.Patient;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientService {

    String bookAppointment(PatientDTO patientDTO);
    String updateAppointment(PatientDTO patientDTO);
    String cancelAppointment(Long id);
    List<PatientDTO> getAllPatients(Long doctorId, LocalDateTime dateTime);
    List<PatientDTO> getAll();

}
