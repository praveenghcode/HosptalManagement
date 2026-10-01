package com.hospital.management.repository;

import com.hospital.management.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUserId(Long userId);

    java.util.List<Patient> findAllByHospital_Id(Long hospitalId);
}
