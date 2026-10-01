package com.hospital.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String bloodGroup;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(nullable = false)
    private String contactNumber;

    @Column(columnDefinition = "TEXT")
    private String medicalHistory;
}
