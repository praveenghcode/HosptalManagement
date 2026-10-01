package com.hospital.management.repository;

import com.hospital.management.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
    Optional<Nurse> findByUserId(Long userId);

    List<Nurse> findAllByHospital_Id(Long hospitalId);

    List<Nurse> findAllByDepartment_Id(Long departmentId);
}
