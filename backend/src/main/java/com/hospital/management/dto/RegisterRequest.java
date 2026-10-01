package com.hospital.management.dto;

import com.hospital.management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;

    // Patient registration fields
    private String hospitalCode;
    private java.time.LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String contactNumber;
    private String address;
    private String medicalHistory;
}
