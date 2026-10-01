package com.hospital.management.service;

import com.hospital.management.config.JwtService;
import com.hospital.management.dto.AuthenticationRequest;
import com.hospital.management.dto.AuthenticationResponse;
import com.hospital.management.dto.RegisterRequest;
import com.hospital.management.model.Role;
import com.hospital.management.model.User;
import com.hospital.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        private final UserRepository repository;
        private final com.hospital.management.repository.HospitalRepository hospitalRepository;
        private final com.hospital.management.repository.PatientRepository patientRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                if (repository.existsByUsername(request.getEmail())) {
                        throw new RuntimeException("Email already exists");
                }

                Role role = request.getRole() != null ? request.getRole() : Role.PATIENT;

                // For Patient registration, validate hospital code
                com.hospital.management.model.Hospital hospital = null;
                if (role == Role.PATIENT) {
                        if (request.getHospitalCode() == null || request.getHospitalCode().isEmpty()) {
                                throw new RuntimeException("Hospital Code is required for patient registration");
                        }
                        hospital = hospitalRepository.findByHospitalCode(request.getHospitalCode())
                                        .orElseThrow(() -> new RuntimeException("Invalid Hospital Code"));
                }

                var user = new User();
                user.setName(request.getName());
                user.setUsername(request.getEmail());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setRole(role);
                user.setEnabled(true);

                repository.save(user); // Save user first to get ID

                // Create Patient entity if role is PATIENT
                if (role == Role.PATIENT) {
                        var patient = new com.hospital.management.model.Patient();
                        patient.setUser(user);
                        patient.setHospital(hospital);
                        patient.setDateOfBirth(request.getDateOfBirth());
                        patient.setGender(request.getGender());
                        patient.setBloodGroup(request.getBloodGroup());
                        patient.setContactNumber(request.getContactNumber());
                        patient.setAddress(request.getAddress());
                        patient.setMedicalHistory(request.getMedicalHistory());
                        patientRepository.save(patient);
                }

                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .role(user.getRole())
                                .id(user.getId())
                                .name(user.getName())
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = repository.findByUsername(request.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found"));
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .role(user.getRole())
                                .id(user.getId())
                                .name(user.getName())
                                .build();
        }
}
