package com.hospital.management.dto;

import lombok.Data;

@Data
public class CreatePharmacistRequest {
    private String name;
    private String email;
    private String password;
    private String licenseNumber;
    private int experienceYears;
}
