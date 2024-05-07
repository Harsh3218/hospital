package com.example.hospital.apis;

import com.example.hospital.service.DoctorService;
import com.example.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CancelAppointmentapi {

    @Autowired
    DoctorService doctorService;

    @Autowired
    PatientService patientService;

    public String removeAppointment(Long id, LocalDateTime dateTime) {
        return doctorService.removeAppointment(id, dateTime);
    }

    public String cancelAppointment(Long id) {
        return patientService.cancelAppointment(id);
    }

}
