import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Layout } from '../../components/layout/layout';
import { AuthService, AuthenticationResponse } from '../../services/auth.service';
import { HospitalService, Hospital } from '../../services/hospital.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, Layout, RouterModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class Dashboard implements OnInit {
  user: AuthenticationResponse | null = null;
  hospitals: Hospital[] = [];

  constructor(
    private authService: AuthService,
    private hospitalService: HospitalService
  ) {
    this.user = this.authService.currentUserValue;
  }

  ngOnInit() {
    if (this.isSuperAdmin) {
      this.loadHospitals();
    }
  }

  loadHospitals() {
    this.hospitalService.getAllHospitals().subscribe({
      next: (data) => {
        this.hospitals = data;
      },
      error: (err) => console.error('Error loading hospitals', err)
    });
  }

  get isSuperAdmin(): boolean {
    return this.user?.role === 'SUPER_ADMIN';
  }

  get isHospitalAdmin(): boolean {
    return this.user?.role === 'HOSPITAL_ADMIN';
  }

  get isDoctor(): boolean {
    return this.user?.role === 'DOCTOR';
  }

  get isPatient(): boolean {
    return this.user?.role === 'PATIENT';
  }
}
