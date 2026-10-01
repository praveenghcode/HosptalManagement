import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Layout } from '../../../components/layout/layout';
import { AuthService, AuthenticationResponse } from '../../../services/auth.service';

@Component({
  selector: 'app-nurse-dashboard',
  standalone: true,
  imports: [CommonModule, Layout, RouterModule],
  templateUrl: './nurse-dashboard.html',
  styleUrl: './nurse-dashboard.scss',
})
export class NurseDashboard {
  user: AuthenticationResponse | null = null;

  assignedPatients = [
    { id: 1, name: 'John Doe', room: 'Room 201', ward: 'Ward A', vitals: 'Due', priority: 'normal' },
    { id: 2, name: 'Sarah Connor', room: 'Room 205', ward: 'Ward A', vitals: 'Completed', priority: 'normal' },
    { id: 3, name: 'Mike Johnson', room: 'Room 103', ward: 'Ward B', vitals: 'Due', priority: 'high' },
    { id: 4, name: 'Emily Davis', room: 'ICU 02', ward: 'ICU', vitals: 'Due', priority: 'critical' },
  ];

  constructor(private authService: AuthService) {
    this.user = this.authService.currentUserValue;
  }

  getPriorityClass(priority: string): string {
    switch (priority) {
      case 'critical': return 'priority-critical';
      case 'high': return 'priority-high';
      default: return 'priority-normal';
    }
  }
}
