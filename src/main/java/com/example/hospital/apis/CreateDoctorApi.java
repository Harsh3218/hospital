package com.example.hospital.apis;

import com.example.hospital.DTO.DoctorDTO;
import com.example.hospital.entity.Doctor;
import com.example.hospital.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateDoctorApi {
    @Autowired
    DoctorService doctorService;

    public DoctorDTO createDoctor(@Valid DoctorDTO doctorDTO) {
        return doctorService.createDoctor(doctorDTO);
    }

}
