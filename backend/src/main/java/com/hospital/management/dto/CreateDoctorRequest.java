package com.hospital.management.dto;

import lombok.Data;

@Data
public class CreateDoctorRequest {
    private String name;
    private String email;
    private String password;
    private Long departmentId;
    private String specialization;
    private String licenseNumber;
    private int experienceYears;
    private Double consultationFee;
}
