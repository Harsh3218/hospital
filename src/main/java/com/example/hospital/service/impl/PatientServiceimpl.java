package com.example.hospital.service.impl;

import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.exception.NotFoundException;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceimpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public String bookAppointment(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setName(patientDTO.getName());
        patient.setGender(patientDTO.getGender());
        patient.setAge(patientDTO.getAge());
        patient.setAddress(patientDTO.getAddress());
        patient.setPhone(patientDTO.getPhone());
        patient.setEmail(patientDTO.getEmail());
        patient.setAppointmentDateTime(patientDTO.getAppointmentDateTime());

        boolean appointmentExists = patientRepository.existsByDoctorIdAndNameAndAppointmentDateTime(patientDTO.getDoctorId(), patientDTO.getName(), patientDTO.getAppointmentDateTime());
        if (appointmentExists) {
            return "Appointment already booked.";
        } else {
            Doctor doctor = doctorRepository.findById(patientDTO.getDoctorId())
                    .orElseThrow(() -> new NotFoundException("Doctor not found"));

            if (doctor.isBusy(patientDTO.getAppointmentDateTime())) {
                return "Doctor is busy";
            } else {

                doctor.bookAppointment(patientDTO.getAppointmentDateTime());
                patient.setDoctor(doctor);


                patientRepository.save(patient);
                return "Appointment booked successfully.";
            }
        }
    }

    @Override
    public String updateAppointment(PatientDTO patientDTO) {
        return "";
    }

    @Override
    public String cancelAppointment(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (!patientOptional.isPresent()) {
            throw new NotFoundException("Patient not found");
        }

        Patient patient = patientOptional.get();
        Doctor doctor = patient.getDoctor();
        LocalDateTime appointmentDateTime = patient.getAppointmentDateTime();

        patientRepository.deleteById(id);
        if (doctor != null && appointmentDateTime != null) {
            doctor.cancelAppointment(appointmentDateTime);
            doctorRepository.save(doctor);
        }
        return "Appointment cancelled";
    }

    @Override
    public List<PatientDTO> getAllPatients(Long doctorId, LocalDateTime dateTime) {
        List<Patient> patients = patientRepository.findByDoctorIdAndAppointmentDateTime(doctorId, dateTime);
        List<PatientDTO> patientDTOs = new ArrayList<>();
        for (Patient patient : patients) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(patient.getId());
            patientDTO.setName(patient.getName());
            patientDTO.setAddress(patient.getAddress());
            patientDTO.setPhone(patient.getPhone());
            patientDTO.setEmail(patient.getEmail());
            patientDTOs.add(patientDTO);
        }
        return patientDTOs;
    }

    @Override
    public List<PatientDTO> getAll() {
        List<Patient> patients =  patientRepository.findAll();
        return patients.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PatientDTO convertToDTO(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setGender(patient.getGender());
        patientDTO.setAge(patient.getAge());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setPhone(patient.getPhone());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setAppointmentDateTime(patient.getAppointmentDateTime());

        Doctor doctor = patient.getDoctor();
        if(doctor != null) {
            patientDTO.setDoctorId(doctor.getId());
            patientDTO.setDocName(doctor.getName());
        }
        return patientDTO;

    }
}
