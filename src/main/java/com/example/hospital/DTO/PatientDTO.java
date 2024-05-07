package com.example.hospital.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PatientDTO {

    private Long id;

    @NotBlank(message = "Name cannot be Blank")
    private String name;

    @Pattern(regexp = "[MF]", message = "Gender must 'M' or 'F'")
    private String gender;

    @Min(value = 1,message = "Age must be at least 1")
    private int age;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Doctor id cannot be null")
    private Long doctorId;

    @Future(message = "Appointment date and time must be in the future")
    private LocalDateTime appointmentDateTime;

    private String docName;

}
