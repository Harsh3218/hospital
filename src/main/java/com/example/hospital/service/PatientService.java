package com.example.hospital.service;

import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.DTO.PatientUpdateDTO;
import com.example.hospital.wrapper.BookingResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface PatientService {

    BookingResponse bookAppointment(PatientDTO patientDTO);
    String cancelAppointment(Long id);
    List<PatientDTO> getAllPatients(Long doctorId, LocalDateTime dateTime);
    List<PatientDTO> getAll();
    BookingResponse updateAppointment(Long id, PatientUpdateDTO patientUpdateDTO);

}
