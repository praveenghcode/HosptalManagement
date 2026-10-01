import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Layout } from '../../../components/layout/layout';
import { AuthService, AuthenticationResponse } from '../../../services/auth.service';

@Component({
  selector: 'app-doctor-dashboard',
  standalone: true,
  imports: [CommonModule, Layout, RouterModule],
  templateUrl: './doctor-dashboard.html',
  styleUrl: './doctor-dashboard.scss',
})
export class DoctorDashboard {
  user: AuthenticationResponse | null = null;

  todayAppointments = [
    { id: 1, patient: 'John Doe', time: '09:00 AM', type: 'Follow-up', status: 'confirmed' },
    { id: 2, patient: 'Sarah Connor', time: '10:30 AM', type: 'Consultation', status: 'confirmed' },
    { id: 3, patient: 'Mike Johnson', time: '11:00 AM', type: 'Check-up', status: 'pending' },
    { id: 4, patient: 'Emily Davis', time: '02:00 PM', type: 'Emergency', status: 'urgent' },
    { id: 5, patient: 'Robert Wilson', time: '03:30 PM', type: 'Follow-up', status: 'confirmed' },
  ];

  constructor(private authService: AuthService) {
    this.user = this.authService.currentUserValue;
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'confirmed': return 'status-confirmed';
      case 'pending': return 'status-pending';
      case 'urgent': return 'status-urgent';
      default: return '';
    }
  }
}
