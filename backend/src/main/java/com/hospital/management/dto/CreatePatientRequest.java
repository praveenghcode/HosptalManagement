package com.hospital.management.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreatePatientRequest {
    private String name;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String contactNumber;
    private String address;
    private String medicalHistory;
}
