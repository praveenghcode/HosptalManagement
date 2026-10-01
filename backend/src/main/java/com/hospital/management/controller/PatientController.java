package com.hospital.management.controller;

import com.hospital.management.model.Patient;
import com.hospital.management.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(service.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPatientById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN', 'DOCTOR', 'NURSE', 'RECEPTIONIST')")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return ResponseEntity.ok(service.savePatient(patient));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        service.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
