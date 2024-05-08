package com.example.hospital.service.impl;

import com.example.hospital.entity.Doctor;
import com.example.hospital.exception.NotFoundException;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void bookAppointment(Long doctorId, LocalDateTime appointmentDateTime) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            List<LocalDateTime> appointments = doctor.getAppointments();
            appointments.add(appointmentDateTime);
        }
    }

    @Override
    public void cancelAppointment(Long doctorId, LocalDateTime appointmentDateTime) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            List<LocalDateTime> appointments = doctor.getAppointments();
            appointments.remove(appointmentDateTime);
        }
    }

    @Override
    public boolean isBusy(Long doctorId, LocalDateTime appointmentDateTime) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            List<LocalDateTime> appointments = doctor.getAppointments();
            return appointments.contains(appointmentDateTime);
        }
        return false;
    }
}
