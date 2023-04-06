import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginRequest } from './login-request';
import { LoginResponse, LoginResponseData } from './login-response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
  ) { }

  login(loginRequest: LoginRequest) {
    return this.http.post<LoginResponse>(`${environment.apiUrl}/auth/login`, loginRequest).pipe(
      tap((response: LoginResponse) => {
        this.saveToken(response.data);
      })
    );
  }

  saveToken(data: LoginResponseData) {
    localStorage.setItem(environment.jwtVariable, JSON.stringify(data));
  }

  getToken(): LoginResponseData | null {
    const token = localStorage.getItem(environment.jwtVariable);

    if (!token) {
      return null;
    }

    return JSON.parse(token);
  }

  logout() {
    localStorage.removeItem(environment.jwtVariable);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

}
