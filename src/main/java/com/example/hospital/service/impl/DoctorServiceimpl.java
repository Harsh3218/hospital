package com.example.hospital.service.impl;


import com.example.hospital.DTO.DoctorDTO;
import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.service.DoctorService;
import com.example.hospital.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DoctorServiceimpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    AppointmentService appointmentService;

    @Override
    public String createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();

        doctor.setName(doctorDTO.getName());
        doctor.setSpeciality(doctorDTO.getSpecialty());
        doctor.setAppointments(doctorDTO.getAppointments());
        doctorRepository.save(doctor);
        return "Doctor Added Successfully";
    }

    @Override
    public Doctor getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return doctor;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public String removeAppointment(Long id, LocalDateTime dateTime) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        appointmentService.cancelAppointment(id,dateTime);
        doctorRepository.save(doctor);
        List<PatientDTO> patients = patientService.getAllPatients(id, dateTime);
        patients.forEach(patient -> patientService.cancelAppointment(patient.getId()));
        return "Appointment Cancelled Successfully";
    }
}
