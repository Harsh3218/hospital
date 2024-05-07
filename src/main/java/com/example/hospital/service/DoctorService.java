package com.example.hospital.service;

import com.example.hospital.DTO.DoctorDTO;
import com.example.hospital.entity.Doctor;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorService {
    String createDoctor(DoctorDTO doctorDTO);
    Doctor getDoctorById(Long id);
    List<Doctor> getAllDoctors();
    String removeAppointment(Long id, LocalDateTime dateTime);
}
