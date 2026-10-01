package com.hospital.management.dto;

import lombok.Data;

@Data
public class CreateNurseRequest {
    private String name;
    private String email;
    private String password;
    private Long departmentId;
    private String licenseNumber;
    private int experienceYears;
    private String shift;
}
