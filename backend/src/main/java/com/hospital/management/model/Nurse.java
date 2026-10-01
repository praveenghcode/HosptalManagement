package com.hospital.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "nurses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    private String licenseNumber;
    private int experienceYears;
    private String shift; // Morning, Evening, Night
}
