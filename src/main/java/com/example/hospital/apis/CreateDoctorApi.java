package com.example.hospital.apis;

import com.example.hospital.DTO.DoctorDTO;
import com.example.hospital.entity.Doctor;
import com.example.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateDoctorApi {
    @Autowired
    DoctorService doctorService;

    public String createDoctor(DoctorDTO doctorDTO) {
        doctorService.createDoctor(doctorDTO);
        return "Doctor Added Successfully";
    }

}
