package com.example.hospital.service;

import com.example.hospital.DTO.DoctorDTO;
import com.example.hospital.entity.Doctor;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorService {
    DoctorDTO createDoctor(DoctorDTO doctorDTO);
    DoctorDTO getDoctorById(Long id);
    List<Doctor> getAllDoctors();
    String removeAppointment(Long id, LocalDateTime dateTime);
}
