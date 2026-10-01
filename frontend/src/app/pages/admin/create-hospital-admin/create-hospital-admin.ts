import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HospitalService, Hospital, CreateHospitalAdminRequest } from '../../../services/hospital.service';
import { Layout } from '../../../components/layout/layout';

@Component({
    selector: 'app-create-hospital-admin',
    standalone: true,
    imports: [CommonModule, FormsModule, Layout, RouterModule],
    templateUrl: './create-hospital-admin.html',
    styleUrl: './create-hospital-admin.scss'
})
export class CreateHospitalAdmin implements OnInit {
    hospitals: Hospital[] = [];
    formData: CreateHospitalAdminRequest = {
        name: '',
        email: '',
        password: '',
        hospitalId: 0
    };
    errorMessage: string = '';
    successMessage: string = '';
    loading: boolean = false;

    constructor(
        private hospitalService: HospitalService,
        private router: Router
    ) { }

    ngOnInit() {
        this.loadHospitals();
    }

    loadHospitals() {
        this.hospitalService.getAllHospitals().subscribe({
            next: (data) => {
                this.hospitals = data;
                if (this.hospitals.length > 0) {
                    this.formData.hospitalId = this.hospitals[0].id;
                }
            },
            error: (err) => {
                console.error('Error loading hospitals', err);
                this.errorMessage = 'Failed to load hospitals. Please try again.';
            }
        });
    }

    onSubmit() {
        this.loading = true;
        this.errorMessage = '';
        this.successMessage = '';

        this.hospitalService.createHospitalAdmin(this.formData).subscribe({
            next: () => {
                this.successMessage = `Hospital Admin "${this.formData.name}" created successfully!`;
                this.errorMessage = '';
                this.loading = false;
                // Reset form
                this.formData = { name: '', email: '', password: '', hospitalId: this.formData.hospitalId };
                setTimeout(() => this.router.navigate(['/dashboard']), 2500);
            },
            error: (err) => {
                console.error('Error creating admin', err);
                this.errorMessage = err.error?.message || 'Failed to create Hospital Admin. Email might already exist.';
                this.successMessage = '';
                this.loading = false;
            }
        });
    }
}
