package com.example.hospital.apis;

import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.DTO.PatientUpdateDTO;
import com.example.hospital.entity.Patient;
import com.example.hospital.service.PatientService;
import com.example.hospital.wrapper.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateApi {

    @Autowired
    private PatientService patientService;

    public BookingResponse updatePatient(Long id, PatientUpdateDTO patientUpdateDTO) {
        return patientService.updateAppointment(id, patientUpdateDTO);
    }


}
