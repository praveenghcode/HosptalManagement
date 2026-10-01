package com.hospital.management.dto;

import lombok.Data;

@Data
public class CreateHospitalAdminRequest {
    private String name;
    private String email;
    private String password;
    private Long hospitalId;
}
