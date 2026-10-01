package com.hospital.management.controller;

import com.hospital.management.dto.CreateDoctorRequest;
import com.hospital.management.dto.CreateHospitalAdminRequest;
import com.hospital.management.dto.CreateNurseRequest;
import com.hospital.management.dto.CreatePharmacistRequest;
import com.hospital.management.dto.CreatePatientRequest;
import com.hospital.management.model.Hospital;
import com.hospital.management.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService service;

    @GetMapping
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        return ResponseEntity.ok(service.getAllHospitals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getHospitalById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Hospital> createHospital(@RequestBody Hospital hospital) {
        return ResponseEntity.ok(service.createHospital(hospital));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'HOSPITAL_ADMIN')")
    public ResponseEntity<Hospital> updateHospital(@PathVariable Long id, @RequestBody Hospital hospital) {
        return ResponseEntity.ok(service.updateHospital(id, hospital));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteHospital(@PathVariable Long id) {
        service.deleteHospital(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> createHospitalAdmin(@RequestBody CreateHospitalAdminRequest request) {
        service.createHospitalAdmin(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/doctor")
    @PreAuthorize("hasRole('HOSPITAL_ADMIN')")
    public ResponseEntity<Void> createDoctor(@RequestBody CreateDoctorRequest request, Authentication authentication) {
        service.createDoctor(request, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/nurse")
    @PreAuthorize("hasRole('HOSPITAL_ADMIN')")
    public ResponseEntity<Void> createNurse(@RequestBody CreateNurseRequest request, Authentication authentication) {
        service.createNurse(request, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pharmacist")
    @PreAuthorize("hasRole('HOSPITAL_ADMIN')")
    public ResponseEntity<Void> createPharmacist(@RequestBody CreatePharmacistRequest request,
            Authentication authentication) {
        service.createPharmacist(request, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/patient")
    @PreAuthorize("hasRole('HOSPITAL_ADMIN')")
    public ResponseEntity<Void> createPatient(@RequestBody CreatePatientRequest request,
            Authentication authentication) {
        service.createPatient(request, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
