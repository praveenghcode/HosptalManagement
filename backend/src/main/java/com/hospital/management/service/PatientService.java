package com.hospital.management.service;

import com.hospital.management.model.Patient;
import com.hospital.management.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;

    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    public Patient getPatientById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public Patient savePatient(Patient patient) {
        return repository.save(patient);
    }

    public void deletePatient(Long id) {
        repository.deleteById(id);
    }
}
