package com.example.hospital.service.impl;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.example.hospital.DTO.PatientDTO;
import com.example.hospital.DTO.PatientUpdateDTO;
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

import javax.swing.text.html.Option;
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
        LocalDateTime appointmentDateTime = patientDTO.getAppointmentDateTime();

        boolean appointmentExists = patientRepository.existsByDoctorIdAndNameAndAppointmentDateTime(patientDTO.getDoctorId(), patientDTO.getName(), patientDTO.getAppointmentDateTime());
        if (appointmentExists) {
            return new BookingResponse("Appointment Already Booked");
        }

        if (appointmentService.isBusy(patientDTO.getDoctorId(), appointmentDateTime)) {
            return new BookingResponse("Doctor is busy");
        }

        Optional<Patient> lastAppointmentOpt = patientRepository.findTopByDoctorIdOrderByAppointmentDateTimeDesc(patientDTO.getDoctorId());
        LocalDateTime nextAvailableSlot;
        if (lastAppointmentOpt.isPresent()) {
            Patient lastAppointment = lastAppointmentOpt.get();
            LocalDateTime lastAppointmentEnd = lastAppointment.getAppointmentDateTime().plusMinutes(30);
            if (appointmentDateTime.isBefore(lastAppointmentEnd)) {

                nextAvailableSlot = lastAppointmentEnd;

                return new BookingResponse("Next available slot: " + nextAvailableSlot);
            } else {

                nextAvailableSlot = appointmentDateTime;
            }
        } else {

            nextAvailableSlot = appointmentDateTime;
        }


        Doctor doctor = doctorRepository.findById(patientDTO.getDoctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found"));

        appointmentService.bookAppointment(patientDTO.getDoctorId(), appointmentDateTime);

        Patient patient = modelMapper.map(patientDTO, Patient.class);
        patient.setDoctor(doctor);
        patient.setAppointmentDateTime(appointmentDateTime);
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




    private LocalDateTime findNextAvailableSlot(Long doctorId, LocalDateTime currentAppointmentDateTime) {
        LocalDateTime nextSlot = currentAppointmentDateTime.plusMinutes(30);
        while (patientRepository.existsByDoctorIdAndAppointmentDateTime(doctorId, nextSlot)) {
            nextSlot = nextSlot.plusMinutes(30);
        }
        return nextSlot;
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
            patientDTOs.add(modelMapper.map(patient, PatientDTO.class));
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

    @Override
    public BookingResponse updateAppointment(Long id, PatientUpdateDTO patientUpdateDTO) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        Patient existingPatient = optionalPatient.orElseThrow(() -> new NotFoundException("Patient not found"));

        if (patientUpdateDTO.getName() != null) {
            existingPatient.setName(patientUpdateDTO.getName());
        }
        if (patientUpdateDTO.getGender() != null) {
            existingPatient.setGender(patientUpdateDTO.getGender());
        }
        if (patientUpdateDTO.getAge() >= 1) {
            existingPatient.setAge(patientUpdateDTO.getAge());
        }
        if (patientUpdateDTO.getAddress() != null) {
            existingPatient.setAddress(patientUpdateDTO.getAddress());
        }
        if (patientUpdateDTO.getPhone() != null) {
            existingPatient.setPhone(patientUpdateDTO.getPhone());
        }
        if (patientUpdateDTO.getEmail() != null) {
            existingPatient.setEmail(patientUpdateDTO.getEmail());
        }
        if (patientUpdateDTO.getAppointmentDateTime() != null) {
            existingPatient.setAppointmentDateTime(patientUpdateDTO.getAppointmentDateTime());
        }

        Patient updatedPatient = patientRepository.save(existingPatient);

        Doctor doctor = updatedPatient.getDoctor();

        return new BookingResponse(
                "Appointment updated",
                updatedPatient.getId(),
                updatedPatient.getName(),
                updatedPatient.getGender(),
                updatedPatient.getAge(),
                updatedPatient.getAddress(),
                updatedPatient.getPhone(),
                updatedPatient.getEmail(),
                updatedPatient.getAppointmentDateTime(),
                doctor != null ? doctor.getName() : null,
                doctor != null ? doctor.getSpeciality() : null
        );
    }


    private PatientDTO convertToDTO(Patient patient) {
        PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);

        Doctor doctor = patient.getDoctor();
        if(doctor != null) {
            patientDTO.setDocName(doctor.getName());
        }
        return patientDTO;

    }
}
