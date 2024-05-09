package com.example.hospital.service.impl;

import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.exception.NotFoundException;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.wrapper.BookingResponse;
import com.example.hospital.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceimpl implements PatientService {

    private final ModelMapper modelMapper;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    public PatientServiceimpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookingResponse bookAppointment(PatientDTO patientDTO) {
        
        Patient patient = modelMapper.map(patientDTO, Patient.class);

        boolean appointmentExists = patientRepository.existsByDoctorIdAndNameAndAppointmentDateTime(patientDTO.getDoctorId(), patientDTO.getName(), patientDTO.getAppointmentDateTime());
        if (appointmentExists) {
            return new BookingResponse("Appointment Already Booked");
        }

        Doctor doctor = doctorRepository.findById(patientDTO.getDoctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found"));

        if (appointmentService.isBusy(patientDTO.getDoctorId(),patientDTO.getAppointmentDateTime())) {
            return new BookingResponse("Doctor is busy");
        }

        appointmentService.bookAppointment(patientDTO.getDoctorId(),patientDTO.getAppointmentDateTime());
        patient.setDoctor(doctor);
        patientRepository.save(patient);

        return new BookingResponse(
                "Appointment Booked",
                patient.getId(),
                patient.getName(),
                patient.getGender(),
                patient.getAge(),
                patient.getAddress(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getAppointmentDateTime(),
                doctor.getName(),
                doctor.getSpeciality()
        );
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
            appointmentService.cancelAppointment(doctor.getId(),appointmentDateTime);
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
