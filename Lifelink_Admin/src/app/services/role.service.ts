import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RoleDTO } from '../models/role';

@Injectable({
  providedIn: 'root'
})
export class RoleService {
  private apiUrl = 'http://localhost:8080/api/Roles';
  constructor(private http: HttpClient) {}

  getAllRoles(): Observable<RoleDTO[]> {
    return this.http.get<RoleDTO[]>(this.apiUrl);
  }

  getRoleById(id: number): Observable<RoleDTO> {
    return this.http.get<RoleDTO>(`${this.apiUrl}/${id}`);
  }

  createRole(Role: RoleDTO): Observable<RoleDTO> {
    return this.http.post<RoleDTO>(this.apiUrl, Role);
  }

  updateRole(Role: RoleDTO): Observable<RoleDTO> {
    return this.http.put<RoleDTO>(`${this.apiUrl}/${Role.id}`, Role);
  }

  deleteRole(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
