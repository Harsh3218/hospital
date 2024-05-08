package com.example.hospital.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DoctorDTO {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "specialty cannot be blank")
    private String Speciality;

    private List<LocalDateTime> appointments;
}
