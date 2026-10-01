import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HospitalService } from '../../../services/hospital.service';
import { Layout } from '../../../components/layout/layout';

@Component({
    selector: 'app-create-nurse',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, RouterModule, Layout],
    templateUrl: './create-nurse.html',
    styleUrl: './create-nurse.scss'
})
export class CreateNurse {
    nurseForm: FormGroup;
    loading = false;
    error = '';
    success = '';

    constructor(
        private fb: FormBuilder,
        private hospitalService: HospitalService,
        private router: Router
    ) {
        this.nurseForm = this.fb.group({
            name: ['', Validators.required],
            email: ['', [Validators.required, Validators.email]],
            password: ['', [Validators.required, Validators.minLength(6)]],
            departmentId: ['', Validators.required], // Should carry department ID
            licenseNumber: ['', Validators.required],
            experienceYears: ['', [Validators.required, Validators.min(0)]],
            shift: ['', Validators.required]
        });
    }

    onSubmit() {
        if (this.nurseForm.invalid) return;

        this.loading = true;
        this.error = '';
        this.success = '';

        this.hospitalService.createNurse(this.nurseForm.value).subscribe({
            next: () => {
                this.success = 'Nurse account created successfully';
                this.loading = false;
                this.nurseForm.reset();
                setTimeout(() => this.router.navigate(['/dashboard']), 2000);
            },
            error: (err) => {
                this.error = 'Failed to create nurse account';
                this.loading = false;
                console.error(err);
            }
        });
    }
}
