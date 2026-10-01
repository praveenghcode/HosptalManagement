import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  loginForm: FormGroup;
  error = '';
  loading = false;
  showPassword = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    // If already logged in, redirect
    const user = this.authService.currentUserValue;
    if (user) {
      this.redirectByRole(user.role);
    }

    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    this.loading = true;
    this.error = '';

    this.authService.login(this.loginForm.value).subscribe({
      next: (response) => {
        this.redirectByRole(response.role);
      },
      error: (err) => {
        this.error = 'Invalid email or password. Please try again.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  private redirectByRole(role: string) {
    switch (role) {
      case 'DOCTOR':
        this.router.navigate(['/doctor-dashboard']);
        break;
      case 'PATIENT':
        this.router.navigate(['/patient-dashboard']);
        break;
      case 'NURSE':
        this.router.navigate(['/nurse-dashboard']);
        break;
      case 'HOSPITAL_ADMIN':
      case 'SUPER_ADMIN':
      default:
        this.router.navigate(['/dashboard']);
        break;
    }
  }
}
