package com.hospital.management.controller;

import com.hospital.management.dto.AppointmentRequest;
import com.hospital.management.model.Appointment;
import com.hospital.management.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping("/my-appointments")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<List<Appointment>> getMyAppointments(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (role.equals("ROLE_DOCTOR")) { // Spring Security adds ROLE_ prefix
            return ResponseEntity.ok(service.getAppointmentsForDoctor(authentication.getName()));
        } else {
            return ResponseEntity.ok(service.getAppointmentsForPatient(authentication.getName()));
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('HOSPITAL_ADMIN', 'PATIENT')")
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(service.createAppointment(request));
    }
}
