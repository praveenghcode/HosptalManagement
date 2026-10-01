import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { AuthService } from './auth.service';

export interface Doctor {
    id: number;
    name: string;
    specialization: string;
    department: string;
    hospital: string;
    experience: number;
    status: 'Active' | 'On Leave';
}

@Injectable({
    providedIn: 'root'
})
export class DoctorService {
    private apiUrl = 'http://localhost:8080/api/doctors';

    constructor(private http: HttpClient, private authService: AuthService) { }

    private getHeaders(): HttpHeaders {
        const user = this.authService.currentUserValue;
        const token = user ? user.token : '';
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        });
    }

    getDoctors(): Observable<Doctor[]> {
        return this.http.get<any[]>(this.apiUrl, { headers: this.getHeaders() }).pipe(
            map(backendDoctors => backendDoctors.map(doc => ({
                id: doc.id,
                name: doc.user ? doc.user.name : 'Unknown',
                specialization: doc.specialization,
                department: doc.department ? doc.department.name : 'Unknown',
                hospital: doc.hospital ? doc.hospital.name : 'Unknown',
                experience: doc.experienceYears || 0,
                status: 'Active' // Default status as it's missing in backend for now
            })))
        );
    }

    // Add create/delete methods if needed later, but for now getting list is priority
}
