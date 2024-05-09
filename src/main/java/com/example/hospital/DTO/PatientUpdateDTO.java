package com.example.hospital.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PatientUpdateDTO {

    private String name;

    private String gender;

    private int age;

    private String address;

    private String phone;

    private String email;

    private Long doctorId;

    private LocalDateTime appointmentDateTime;

    private String docName;

}
