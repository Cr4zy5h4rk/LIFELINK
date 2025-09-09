import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { DonorDTO } from '../models/donor';
import { environment } from '../Environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;
  private readonly url = environment.apiUrl


  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<any>(
      JSON.parse(localStorage.getItem('currentUser') || 'null')
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  login(code: string) {
    return this.http.get<any>(`http://localhost:8080/api/delegate/fetchUserInfo?code=${code}`)
      .pipe(map(response => {
        // Stocker les informations utilisateur et le token JWT
        localStorage.setItem('currentUser', JSON.stringify(response.user));
        localStorage.setItem('token', response.token);
        this.currentUserSubject.next(response.user);
        return response.user;
      }));
  }

  logout() {
    // Supprimer l'utilisateur du stockage local
    localStorage.removeItem('currentUser');
    localStorage.removeItem('token');
    this.currentUserSubject.next(null);
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  saveUser(user: DonorDTO): Observable<DonorDTO> {
    console.log("user", user)
    return this.http.post<DonorDTO>(`${this.url}/donor/save`, user)
  }
}
