package com.example.hospital.apis;

import com.example.hospital.DTO.DoctorDTO;
import com.example.hospital.entity.Doctor;
import com.example.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorReadApi {
    @Autowired
    DoctorService doctorService;

    public DoctorDTO getDoctorById(Long id) {
        return doctorService.getDoctorById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }
}
