package com.hospital.management.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentTime;
    private String reason;
}
