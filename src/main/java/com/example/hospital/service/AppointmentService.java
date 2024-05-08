package com.example.hospital.service;

import java.time.LocalDateTime;

public interface AppointmentService {
    void bookAppointment(Long doctorId, LocalDateTime appointmentDateTime);
    void cancelAppointment(Long doctorId, LocalDateTime appointmentDateTime);
    boolean isBusy(Long doctorId, LocalDateTime appointmentDateTime);

}
