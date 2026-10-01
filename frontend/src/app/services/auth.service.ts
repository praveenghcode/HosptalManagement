import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of, delay, tap } from 'rxjs';

export interface AuthenticationResponse {
    token: string;
    role: string;
    id: number;
    name: string;
}

export interface RegisterRequest {
    name: string;
    email: string;
    password: string;
    role?: string;
    // Patient fields
    hospitalCode?: string;
    dateOfBirth?: string;
    gender?: string;
    bloodGroup?: string;
    contactNumber?: string;
    address?: string;
    medicalHistory?: string;
}

export interface AuthenticationRequest {
    email: string;
    password: string;
}

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = 'http://localhost:8080/api/auth';
    private userSubject = new BehaviorSubject<AuthenticationResponse | null>(null);
    public user$ = this.userSubject.asObservable();

    constructor(private http: HttpClient) {
        this.loadUserFromStorage();
    }

    private loadUserFromStorage() {
        const storedUser = localStorage.getItem('user');
        if (storedUser) {
            this.userSubject.next(JSON.parse(storedUser));
        }
    }

    register(request: RegisterRequest): Observable<AuthenticationResponse> {
        return this.http.post<AuthenticationResponse>(`${this.apiUrl}/register`, request).pipe(
            tap(response => this.storeUser(response))
        );
    }

    login(request: AuthenticationRequest): Observable<AuthenticationResponse> {
        return this.http.post<AuthenticationResponse>(`${this.apiUrl}/authenticate`, request).pipe(
            tap(response => this.storeUser(response))
        );
    }

    logout() {
        localStorage.removeItem('user');
        this.userSubject.next(null);
    }

    private storeUser(user: AuthenticationResponse) {
        localStorage.setItem('user', JSON.stringify(user));
        this.userSubject.next(user);
    }

    get currentUserValue(): AuthenticationResponse | null {
        return this.userSubject.value;
    }
}
