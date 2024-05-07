package com.example.hospital.controller;

import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.apis.BookAPI;
import com.example.hospital.apis.CancelAppointmentapi;
import com.example.hospital.apis.PatientListapi;
import com.example.hospital.entity.Patient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController{

    @Autowired
    private BookAPI bookAPI;

    @Autowired
    private CancelAppointmentapi cancelAppointmentapi;

    @Autowired
    private PatientListapi patientListapi;

    @PostMapping("/book-appointment")
    public ResponseEntity<String> bookAppointment(@Valid @RequestBody PatientDTO patientDTO){
        String response = bookAPI.bookAppointment(patientDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> getPatientList(){
        List<PatientDTO> patients = patientListapi.getAll();
        return new ResponseEntity<>(patients , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Long id){
        String response = cancelAppointmentapi.cancelAppointment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}