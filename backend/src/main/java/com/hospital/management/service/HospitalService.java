package com.hospital.management.service;

import com.hospital.management.dto.CreateDoctorRequest;
import com.hospital.management.dto.CreateHospitalAdminRequest;
import com.hospital.management.dto.CreateNurseRequest;
import com.hospital.management.dto.CreatePharmacistRequest;
import com.hospital.management.dto.CreatePatientRequest;
import com.hospital.management.model.*;
import com.hospital.management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository repository;
    private final UserRepository userRepository;
    private final HospitalAdminRepository hospitalAdminRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final PharmacistRepository pharmacistRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Hospital> getAllHospitals() {
        return repository.findAll();
    }

    public Hospital getHospitalById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id: " + id));
    }

    public Hospital createHospital(Hospital hospital) {
        if (hospital.getHospitalCode() == null || hospital.getHospitalCode().isEmpty()) {
            hospital.setHospitalCode(generateHospitalCode(hospital.getName()));
        }
        return repository.save(hospital);
    }

    private String generateHospitalCode(String name) {
        String prefix = name.length() >= 3 ? name.substring(0, 3).toUpperCase() : "HOS";
        String code;
        do {
            int randomNum = (int) (Math.random() * 9000) + 1000;
            code = prefix + "-" + randomNum;
        } while (repository.existsByHospitalCode(code));
        return code;
    }

    public Hospital updateHospital(Long id, Hospital hospitalDetails) {
        Hospital hospital = getHospitalById(id);

        hospital.setName(hospitalDetails.getName());
        hospital.setAddress(hospitalDetails.getAddress());
        hospital.setContactNumber(hospitalDetails.getContactNumber());
        hospital.setEmail(hospitalDetails.getEmail());
        hospital.setWebsite(hospitalDetails.getWebsite());
        hospital.setDescription(hospitalDetails.getDescription());

        return repository.save(hospital);
    }

    public void deleteHospital(Long id) {
        Hospital hospital = getHospitalById(id);
        hospital.setActive(false); // Soft delete
        repository.save(hospital);
    }

    public void createHospitalAdmin(CreateHospitalAdminRequest request) {
        if (userRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Hospital hospital = getHospitalById(request.getHospitalId());

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.HOSPITAL_ADMIN);
        userRepository.save(user);

        HospitalAdmin admin = new HospitalAdmin();
        admin.setUser(user);
        admin.setHospital(hospital);
        hospitalAdminRepository.save(admin);
    }

    public void createDoctor(CreateDoctorRequest request, String adminUsername) {
        User adminUser = userRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        HospitalAdmin hospitalAdmin = hospitalAdminRepository.findByUserId(adminUser.getId())
                .orElseThrow(() -> new RuntimeException("Not a valid Hospital Admin"));

        Hospital hospital = hospitalAdmin.getHospital();

        if (userRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.DOCTOR);
        userRepository.save(user);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        if (!department.getHospital().getId().equals(hospital.getId())) {
            throw new RuntimeException("Department does not belong to this hospital");
        }

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setHospital(hospital);
        doctor.setDepartment(department);
        doctor.setSpecialization(request.getSpecialization());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setConsultationFee(request.getConsultationFee());

        doctorRepository.save(doctor);
    }

    public void createNurse(CreateNurseRequest request, String adminUsername) {
        User adminUser = userRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        HospitalAdmin hospitalAdmin = hospitalAdminRepository.findByUserId(adminUser.getId())
                .orElseThrow(() -> new RuntimeException("Not a valid Hospital Admin"));

        Hospital hospital = hospitalAdmin.getHospital();

        if (userRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.NURSE);
        userRepository.save(user);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        if (!department.getHospital().getId().equals(hospital.getId())) {
            throw new RuntimeException("Department does not belong to this hospital");
        }

        Nurse nurse = new Nurse();
        nurse.setUser(user);
        nurse.setHospital(hospital);
        nurse.setDepartment(department);
        nurse.setLicenseNumber(request.getLicenseNumber());
        nurse.setExperienceYears(request.getExperienceYears());
        nurse.setShift(request.getShift());

        nurseRepository.save(nurse);
    }

    public void createPharmacist(CreatePharmacistRequest request, String adminUsername) {
        User adminUser = userRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        HospitalAdmin hospitalAdmin = hospitalAdminRepository.findByUserId(adminUser.getId())
                .orElseThrow(() -> new RuntimeException("Not a valid Hospital Admin"));

        Hospital hospital = hospitalAdmin.getHospital();

        if (userRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.PHARMACIST);
        userRepository.save(user);

        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setUser(user);
        pharmacist.setHospital(hospital);
        pharmacist.setLicenseNumber(request.getLicenseNumber());
        pharmacist.setExperienceYears(request.getExperienceYears());

        pharmacistRepository.save(pharmacist);
    }

    public void createPatient(CreatePatientRequest request, String adminUsername) {
        User adminUser = userRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        HospitalAdmin hospitalAdmin = hospitalAdminRepository.findByUserId(adminUser.getId())
                .orElseThrow(() -> new RuntimeException("Not a valid Hospital Admin"));

        Hospital hospital = hospitalAdmin.getHospital();

        if (userRepository.existsByUsername(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.PATIENT);
        userRepository.save(user);

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setHospital(hospital);
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setContactNumber(request.getContactNumber());
        patient.setAddress(request.getAddress());
        patient.setMedicalHistory(request.getMedicalHistory());

        patientRepository.save(patient);
    }
}
