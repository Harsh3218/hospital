package com.example.hospital.apis;

import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.entity.Patient;
import com.example.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientListapi {

    @Autowired
    PatientService patientService;

    public List<PatientDTO> getAll() {
        return patientService.getAll();
    }
}
