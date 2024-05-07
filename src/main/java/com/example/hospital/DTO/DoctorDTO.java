package com.example.hospital.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DoctorDTO {
    private Long id;
    private String name;
    private String specialty;
    private List<LocalDateTime> appointments;
}
