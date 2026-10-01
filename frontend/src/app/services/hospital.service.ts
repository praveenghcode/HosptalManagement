import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

export interface Hospital {
    id: number;
    name: string;
    hospitalCode: string; // Added code
    address: string;
    contactNumber: string;
    email: string;
    website?: string;
    description?: string;
    active: boolean;
}

export interface CreateHospitalAdminRequest {
    name: string;
    email: string;
    password: string;
    hospitalId: number;
}

export interface CreateNurseRequest {
    name: string;
    email: string;
    password: string;
    departmentId: number;
    licenseNumber: string;
    experienceYears: number;
    shift: string;
}

export interface CreatePharmacistRequest {
    name: string;
    email: string;
    password: string;
    licenseNumber: string;
    experienceYears: number;
}

export interface CreatePatientRequest {
    name: string;
    email: string;
    password: string;
    dateOfBirth: string;
    gender: string;
    bloodGroup: string;
    contactNumber: string;
    address: string;
    medicalHistory?: string;
}

@Injectable({
    providedIn: 'root'
})
export class HospitalService {
    private apiUrl = 'http://localhost:8080/api/hospitals';

    constructor(private http: HttpClient, private authService: AuthService) { }

    private getHeaders(): HttpHeaders {
        const token = this.authService.currentUserValue?.token;
        return new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
    }

    getAllHospitals(): Observable<Hospital[]> {
        return this.http.get<Hospital[]>(this.apiUrl, { headers: this.getHeaders() });
    }

    createHospitalAdmin(request: CreateHospitalAdminRequest): Observable<void> {
        return this.http.post<void>(`${this.apiUrl}/admin`, request, { headers: this.getHeaders() });
    }

    createNurse(request: CreateNurseRequest): Observable<void> {
        return this.http.post<void>(`${this.apiUrl}/nurse`, request, { headers: this.getHeaders() });
    }

    createPharmacist(request: CreatePharmacistRequest): Observable<void> {
        return this.http.post<void>(`${this.apiUrl}/pharmacist`, request, { headers: this.getHeaders() });
    }

    createPatient(request: CreatePatientRequest): Observable<void> {
        return this.http.post<void>(`${this.apiUrl}/patient`, request, { headers: this.getHeaders() });
    }
}
