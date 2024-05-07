package com.example.hospital.controller;

import com.example.hospital.DTO.DoctorDTO;
import com.example.hospital.apis.CreateDoctorApi;
import com.example.hospital.apis.DoctorReadApi;
import com.example.hospital.apis.CancelAppointmentapi;
import com.example.hospital.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    CreateDoctorApi createDoctorApi;

    @Autowired
    DoctorReadApi doctorReadApi;

    @Autowired
    CancelAppointmentapi removeAppointmentapi;

    @PostMapping("/add")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        String savedDoctor = createDoctorApi.createDoctor(doctorDTO);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") Long id) {
        Doctor getDoctor = doctorReadApi.getDoctorById(id);
        return new ResponseEntity<>(getDoctor, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorReadApi.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/appointments")
    public ResponseEntity<String> removeAppointment(@PathVariable("id") Long id,
                                                    @RequestParam("dateTime") LocalDateTime dateTime) {
        String result = removeAppointmentapi.removeAppointment(id, dateTime);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}
