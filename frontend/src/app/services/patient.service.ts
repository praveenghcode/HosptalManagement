import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { AuthService } from './auth.service';

export interface Patient {
    id: number;
    name: string;
    email: string; // mapped from user.username
    gender: string;
    bloodGroup: string;
    contactNumber: string;
    age: number; // calculated from DOB
    address: string;
}

@Injectable({
    providedIn: 'root'
})
export class PatientService {
    private apiUrl = 'http://localhost:8080/api/patients';

    constructor(private http: HttpClient, private authService: AuthService) { }

    private getHeaders(): HttpHeaders {
        const user = this.authService.currentUserValue;
        const token = user ? user.token : '';
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        });
    }

    getPatients(): Observable<Patient[]> {
        return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() }).pipe(
            map(backendPatients => backendPatients.map(p => ({
                id: p.id,
                name: p.user ? p.user.name : 'Unknown',
                email: p.user ? p.user.username : '',
                gender: p.gender,
                bloodGroup: p.bloodGroup,
                contactNumber: p.contactNumber,
                age: this.calculateAge(p.dateOfBirth),
                address: p.address
            })))
        );
    }

    addPatient(patientData: any): Observable<Patient> {
        // Note: The backend expects a Patient object which includes a User object.
        // This is complex because we need to register a user first, then create a patient.
        // For simplicity, we might need a dedicated endpoint in backend to "Register Patient" 
        // which handles both User creation and Patient details.
        // BUT, for now, let's assume we post to /api/auth/register with role PATIENT, 
        // AND THEN post to /api/patients. 
        // OR, the user is already registered.
        // Let's assume for this "Live" demo, we just want to see the list first.
        return this.http.post<Patient>(this.apiUrl, patientData, { headers: this.getHeaders() });
    }

    private calculateAge(dob: string): number {
        if (!dob) return 0;
        const birthDate = new Date(dob);
        const ageDifMs = Date.now() - birthDate.getTime();
        const ageDate = new Date(ageDifMs);
        return Math.abs(ageDate.getUTCFullYear() - 1970);
    }
}
