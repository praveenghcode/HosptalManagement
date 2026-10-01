import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Layout } from '../../components/layout/layout';
import { Patient, PatientService } from '../../services/patient.service';

@Component({
  selector: 'app-patients',
  standalone: true,
  imports: [CommonModule, Layout],
  templateUrl: './patients.html',
  styleUrl: './patients.scss'
})
export class Patients implements OnInit {
  patients: Patient[] = [];
  isLoading = true;

  constructor(private patientService: PatientService) { }

  ngOnInit() {
    this.patientService.getPatients().subscribe({
      next: (data) => {
        this.patients = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching patients', err);
        this.isLoading = false;
      }
    });
  }
}
