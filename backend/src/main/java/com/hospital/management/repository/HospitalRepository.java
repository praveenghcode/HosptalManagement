package com.hospital.management.repository;

import com.hospital.management.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    boolean existsByHospitalCode(String hospitalCode);

    java.util.Optional<Hospital> findByHospitalCode(String hospitalCode);
}
