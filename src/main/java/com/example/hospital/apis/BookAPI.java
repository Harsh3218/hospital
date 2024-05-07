package com.example.hospital.apis;

import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookAPI {
    @Autowired
    private PatientService patientService;

    public String bookAppointment(@Valid PatientDTO patientDTO) {
        return patientService.bookAppointment(patientDTO);
    }
}
