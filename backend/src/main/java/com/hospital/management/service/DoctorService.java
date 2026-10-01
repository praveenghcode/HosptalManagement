package com.hospital.management.service;

import com.hospital.management.model.Doctor;
import com.hospital.management.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;

    public List<Doctor> getAllDoctors() {
        return repository.findAll();
    }

    public List<Doctor> getDoctorsByHospital(Long hospitalId) {
        return repository.findByHospitalId(hospitalId);
    }

    public List<Doctor> getDoctorsByDepartment(Long departmentId) {
        return repository.findByDepartmentId(departmentId);
    }

    public Doctor getDoctorById(@NonNull Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public Doctor saveDoctor(@NonNull Doctor doctor) {
        return repository.save(doctor);
    }

    public void deleteDoctor(@NonNull Long id) {
        repository.deleteById(id);
    }
}
