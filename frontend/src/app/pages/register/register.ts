import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  registerForm: FormGroup;
  error = '';
  loading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      hospitalCode: ['', [Validators.required, Validators.minLength(6)]],
      dateOfBirth: ['', [Validators.required]],
      gender: ['', [Validators.required]],
      bloodGroup: ['', [Validators.required]],
      contactNumber: ['', [Validators.required, Validators.pattern('^[+]?[0-9]{10,13}$')]],
      address: ['', [Validators.required]],
      medicalHistory: ['']
    });
  }

  onSubmit() {
    if (this.registerForm.invalid) return;

    this.loading = true;
    this.error = '';

    // Default role is PATIENT if not specified
    const request = {
      ...this.registerForm.value,
      role: 'PATIENT'
    };

    this.authService.register(request).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.error = 'Registration failed. Please try again.';
        this.loading = false;
        console.error(err);
      }
    });
  }
}
