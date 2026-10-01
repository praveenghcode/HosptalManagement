package com.hospital.management.repository;

import com.hospital.management.model.HospitalAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalAdminRepository extends JpaRepository<HospitalAdmin, Long> {
    Optional<HospitalAdmin> findByUserId(Long userId);
}
