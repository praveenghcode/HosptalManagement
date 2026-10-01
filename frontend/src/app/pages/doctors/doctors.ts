import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Layout } from '../../components/layout/layout';
import { DoctorService, Doctor } from '../../services/doctor.service';

@Component({
  selector: 'app-doctors',
  standalone: true,
  imports: [CommonModule, Layout],
  templateUrl: './doctors.html',
  styleUrl: './doctors.scss'
})
export class Doctors implements OnInit {
  doctors: Doctor[] = [];
  loading = true;

  constructor(private doctorService: DoctorService) { }

  ngOnInit() {
    this.doctorService.getDoctors().subscribe(data => {
      this.doctors = data;
      this.loading = false;
    });
  }
}
