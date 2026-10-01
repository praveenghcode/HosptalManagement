package com.hospital.management.controller;

import com.hospital.management.model.Doctor;
import com.hospital.management.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService service;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(service.getAllDoctors());
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<List<Doctor>> getDoctorsByHospital(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(service.getDoctorsByHospital(hospitalId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDoctorById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(service.saveDoctor(doctor));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        service.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
