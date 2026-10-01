import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { Dashboard } from './pages/dashboard/dashboard';
import { Doctors } from './pages/doctors/doctors';
import { Patients } from './pages/patients/patients';
import { CreateHospitalAdmin } from './pages/admin/create-hospital-admin/create-hospital-admin';
import { DoctorDashboard } from './pages/dashboard/doctor-dashboard/doctor-dashboard';
import { PatientDashboard } from './pages/dashboard/patient-dashboard/patient-dashboard';
import { NurseDashboard } from './pages/dashboard/nurse-dashboard/nurse-dashboard';

export const routes: Routes = [
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'login', component: Login },
    { path: 'register', component: Register },
    { path: 'dashboard', component: Dashboard },
    { path: 'doctor-dashboard', component: DoctorDashboard },
    { path: 'patient-dashboard', component: PatientDashboard },
    { path: 'nurse-dashboard', component: NurseDashboard },
    { path: 'doctors', component: Doctors },
    { path: 'patients', component: Patients },
    { path: 'create-hospital-admin', component: CreateHospitalAdmin },
    { path: 'create-nurse', loadComponent: () => import('./pages/admin/create-nurse/create-nurse').then(m => m.CreateNurse) },
    { path: 'create-pharmacist', loadComponent: () => import('./pages/admin/create-pharmacist/create-pharmacist').then(m => m.CreatePharmacist) }
];
