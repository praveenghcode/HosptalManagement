package com.hospital.management.config;

import com.hospital.management.model.*;
import com.hospital.management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final HospitalAdminRepository hospitalAdminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedUsers();
        seedHospitalsAndDepartments();
        seedDoctors();
        seedPatients();
        seedHospitalAdmin();
    }

    private void seedUsers() {
        // Create Super Admin if no users exist
        if (userRepository.count() == 0) {
            User superAdmin = new User();
            superAdmin.setName("Super Admin");
            superAdmin.setUsername("admin@hospital.com");
            superAdmin.setPassword(passwordEncoder.encode("admin123"));
            superAdmin.setRole(Role.SUPER_ADMIN);
            userRepository.save(superAdmin);
        }
        // Also ensure super admin exists even if users exist but no SUPER_ADMIN
        if (!userRepository.existsByUsername("admin@hospital.com")) {
            User superAdmin = new User();
            superAdmin.setName("Super Admin");
            superAdmin.setUsername("admin@hospital.com");
            superAdmin.setPassword(passwordEncoder.encode("admin123"));
            superAdmin.setRole(Role.SUPER_ADMIN);
            userRepository.save(superAdmin);
        }
    }

    private void seedHospitalsAndDepartments() {
        if (hospitalRepository.count() == 0) {
            // Hospital 1
            Hospital hospital1 = new Hospital();
            hospital1.setName("General City Hospital");
            hospital1.setAddress("123 Main St, Cityville");
            hospital1.setContactNumber("+1 555-0100");
            hospital1.setEmail("info@generalhospital.com");
            hospitalRepository.save(hospital1);

            createDepartment(hospital1, "Cardiology");
            createDepartment(hospital1, "Neurology");
            createDepartment(hospital1, "Pediatrics");
            createDepartment(hospital1, "Orthopedics");

            // Hospital 2
            Hospital hospital2 = new Hospital();
            hospital2.setName("Sunrise Medical Center");
            hospital2.setAddress("456 Health Blvd, Medtown");
            hospital2.setContactNumber("+1 555-0200");
            hospital2.setEmail("info@sunrisemedical.com");
            hospitalRepository.save(hospital2);

            createDepartment(hospital2, "General Medicine");
            createDepartment(hospital2, "Dermatology");
            createDepartment(hospital2, "ENT");
        }
    }

    private void createDepartment(Hospital hospital, String name) {
        Department dept = new Department();
        dept.setName(name);
        dept.setHospital(hospital);
        dept.setDescription(name + " Department");
        departmentRepository.save(dept);
    }

    private void seedDoctors() {
        if (doctorRepository.count() == 0) {
            Hospital hospital = hospitalRepository.findAll().get(0);
            Department cardio = departmentRepository.findAll().stream().filter(d -> d.getName().equals("Cardiology"))
                    .findFirst().orElseThrow();
            Department pediatrics = departmentRepository.findAll().stream()
                    .filter(d -> d.getName().equals("Pediatrics")).findFirst().orElseThrow();

            // Doctor 1
            if (!userRepository.existsByUsername("doctor1@hospital.com")) {
                User docUser1 = new User();
                docUser1.setName("Dr. John Smith");
                docUser1.setUsername("doctor1@hospital.com");
                docUser1.setPassword(passwordEncoder.encode("doc123"));
                docUser1.setRole(Role.DOCTOR);
                userRepository.save(docUser1);

                Doctor doc1 = new Doctor();
                doc1.setUser(docUser1);
                doc1.setHospital(hospital);
                doc1.setDepartment(cardio);
                doc1.setSpecialization("Cardiologist");
                doc1.setLicenseNumber("LIC-001");
                doc1.setExperienceYears(15);
                doc1.setConsultationFee(150.0);
                doctorRepository.save(doc1);
            }

            // Doctor 2
            if (!userRepository.existsByUsername("doctor2@hospital.com")) {
                User docUser2 = new User();
                docUser2.setName("Dr. Emily Brown");
                docUser2.setUsername("doctor2@hospital.com");
                docUser2.setPassword(passwordEncoder.encode("doc123"));
                docUser2.setRole(Role.DOCTOR);
                userRepository.save(docUser2);

                Doctor doc2 = new Doctor();
                doc2.setUser(docUser2);
                doc2.setHospital(hospital);
                doc2.setDepartment(pediatrics);
                doc2.setSpecialization("Pediatrician");
                doc2.setLicenseNumber("LIC-002");
                doc2.setExperienceYears(8);
                doc2.setConsultationFee(100.0);
                doctorRepository.save(doc2);
            }
        }
    }

    private void seedPatients() {
        if (patientRepository.count() == 0 && hospitalRepository.count() > 0) {
            Hospital hospital = hospitalRepository.findAll().get(0);

            // Patient 1
            if (!userRepository.existsByUsername("patient1@hospital.com")) {
                User patUser1 = new User();
                patUser1.setName("Alice Johnson");
                patUser1.setUsername("patient1@hospital.com");
                patUser1.setPassword(passwordEncoder.encode("pat123"));
                patUser1.setRole(Role.PATIENT);
                userRepository.save(patUser1);

                Patient pat1 = new Patient();
                pat1.setUser(patUser1);
                pat1.setHospital(hospital);
                pat1.setDateOfBirth(LocalDate.of(1990, 5, 15));
                pat1.setGender("Female");
                pat1.setBloodGroup("A+");
                pat1.setContactNumber("+1 555-0201");
                pat1.setAddress("456 Oak Ave, Cityville");
                patientRepository.save(pat1);
            }

            // Patient 2
            if (!userRepository.existsByUsername("patient2@hospital.com")) {
                User patUser2 = new User();
                patUser2.setName("Bob Williams");
                patUser2.setUsername("patient2@hospital.com");
                patUser2.setPassword(passwordEncoder.encode("pat123"));
                patUser2.setRole(Role.PATIENT);
                userRepository.save(patUser2);

                Patient pat2 = new Patient();
                pat2.setUser(patUser2);
                pat2.setHospital(hospital);
                pat2.setDateOfBirth(LocalDate.of(1985, 8, 20));
                pat2.setGender("Male");
                pat2.setBloodGroup("O-");
                pat2.setContactNumber("+1 555-0202");
                pat2.setAddress("789 Pine Ln, Cityville");
                patientRepository.save(pat2);
            }
        }
    }

    private void seedHospitalAdmin() {
        if (!userRepository.existsByUsername("manager@hospital.com")) {
            User hAdmin = new User();
            hAdmin.setName("Hospital Manager");
            hAdmin.setUsername("manager@hospital.com");
            hAdmin.setPassword(passwordEncoder.encode("manager123"));
            hAdmin.setRole(Role.HOSPITAL_ADMIN);
            userRepository.save(hAdmin);

            Hospital hospital = hospitalRepository.findAll().get(0);

            HospitalAdmin admin = new HospitalAdmin();
            admin.setUser(hAdmin);
            admin.setHospital(hospital);
            hospitalAdminRepository.save(admin);
        }
    }
}
