import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Layout } from '../../../components/layout/layout';
import { AuthService, AuthenticationResponse } from '../../../services/auth.service';

@Component({
  selector: 'app-patient-dashboard',
  standalone: true,
  imports: [CommonModule, Layout, RouterModule],
  templateUrl: './patient-dashboard.html',
  styleUrl: './patient-dashboard.scss',
})
export class PatientDashboard {
  user: AuthenticationResponse | null = null;

  upcomingAppointments = [
    { id: 1, doctor: 'Dr. Smith', specialty: 'Cardiology', date: 'Tomorrow', time: '10:00 AM', status: 'confirmed' },
    { id: 2, doctor: 'Dr. Johnson', specialty: 'General', date: 'Feb 15', time: '02:30 PM', status: 'pending' },
  ];

  prescriptions = [
    { id: 1, name: 'Amoxicillin 500mg', dosage: '3 times daily', remaining: '5 days', doctor: 'Dr. Smith' },
    { id: 2, name: 'Ibuprofen 200mg', dosage: 'As needed', remaining: '10 days', doctor: 'Dr. Johnson' },
  ];

  constructor(private authService: AuthService) {
    this.user = this.authService.currentUserValue;
  }
}
