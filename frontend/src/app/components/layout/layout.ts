import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AuthService, AuthenticationResponse } from '../../services/auth.service';

interface NavItem {
  label: string;
  icon: string;
  route: string;
  roles: string[];
}

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './layout.html',
  styleUrl: './layout.scss'
})
export class Layout {
  private authService = inject(AuthService);
  private router = inject(Router);

  user$ = this.authService.user$;
  sidebarCollapsed = false;

  allNavItems: NavItem[] = [
    { label: 'Dashboard', icon: '📊', route: '/dashboard', roles: ['SUPER_ADMIN', 'HOSPITAL_ADMIN'] },
    { label: 'Dashboard', icon: '📊', route: '/doctor-dashboard', roles: ['DOCTOR'] },
    { label: 'Dashboard', icon: '📊', route: '/patient-dashboard', roles: ['PATIENT'] },
    { label: 'Dashboard', icon: '📊', route: '/nurse-dashboard', roles: ['NURSE'] },
    { label: 'Doctors', icon: '🩺', route: '/doctors', roles: ['SUPER_ADMIN', 'HOSPITAL_ADMIN'] },
    { label: 'Patients', icon: '👥', route: '/patients', roles: ['SUPER_ADMIN', 'HOSPITAL_ADMIN', 'DOCTOR', 'NURSE'] },
    { label: 'Create Admin', icon: '🔑', route: '/create-hospital-admin', roles: ['SUPER_ADMIN'] },
  ];

  getNavItems(role: string): NavItem[] {
    return this.allNavItems.filter(item => item.roles.includes(role));
  }

  getRoleBadgeClass(role: string): string {
    switch (role) {
      case 'SUPER_ADMIN': return 'badge-super-admin';
      case 'HOSPITAL_ADMIN': return 'badge-admin';
      case 'DOCTOR': return 'badge-doctor';
      case 'NURSE': return 'badge-nurse';
      case 'PATIENT': return 'badge-patient';
      default: return 'badge-default';
    }
  }

  formatRole(role: string): string {
    return role.replace(/_/g, ' ');
  }

  toggleSidebar() {
    this.sidebarCollapsed = !this.sidebarCollapsed;
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
