package com.example.hospital.wrapper;

import com.example.hospital.entity.Patient;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResponse {
    private String message;
    private Long id;
    private String name;
    private String gender;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int age;
    private String address;
    private String phone;
    private String email;
    private LocalDateTime appointmentDateTime;
    private String doctorName;
    private String doctorSpeciality;

    public BookingResponse(String message) {
        this.message = message;
    }

    public BookingResponse(String message, Long id, String name, String gender, int age, String address, String phone, String email, LocalDateTime appointmentDateTime, String doctorName, String doctorSpeciality) {
        this.message = message;
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.appointmentDateTime = appointmentDateTime;
        this.doctorName = doctorName;
        this.doctorSpeciality = doctorSpeciality;
    }
}
