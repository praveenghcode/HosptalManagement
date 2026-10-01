import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HospitalService } from '../../../services/hospital.service';
import { Layout } from '../../../components/layout/layout';

@Component({
    selector: 'app-create-pharmacist',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, RouterModule, Layout],
    templateUrl: './create-pharmacist.html',
    styleUrl: './create-pharmacist.scss'
})
export class CreatePharmacist {
    pharmacistForm: FormGroup;
    loading = false;
    error = '';
    success = '';

    constructor(
        private fb: FormBuilder,
        private hospitalService: HospitalService,
        private router: Router
    ) {
        this.pharmacistForm = this.fb.group({
            name: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(6)]],
            licenseNumber: ['', Validators.required],
            experienceYears: ['', [Validators.required, Validators.min(0)]]
        });
    }

    onSubmit() {
        if (this.pharmacistForm.invalid) return;

        this.loading = true;
        this.error = '';
        this.success = '';

        this.hospitalService.createPharmacist(this.pharmacistForm.value).subscribe({
            next: () => {
                this.success = 'Pharmacist account created successfully';
                this.loading = false;
                this.pharmacistForm.reset();
                setTimeout(() => this.router.navigate(['/dashboard']), 2000);
            },
            error: (err) => {
                this.error = 'Failed to create pharmacist account';
                this.loading = false;
                console.error(err);
            }
        });
    }
}
