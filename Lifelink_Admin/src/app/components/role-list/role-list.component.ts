import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { RoleService } from '../../services/role.service';
import { RoleDTO } from '../../models/role';

@Component({
  selector: 'app-role-list',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule],
  template: `
    <div class="container my-4">
      <h2 class="h2 mb-4">Gestion des Rôles</h2>
      <div class="mb-4">
        <button
          routerLink="/roles/new"
          class="btn btn-primary"
        >
          Ajouter un Nouveau Rôle
        </button>
      </div>
      <table class="table table-bordered table-hover">
        <thead class="table-light">
        <tr>
          <th>ID</th>
          <th>Nom du Rôle</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr *ngFor="let role of roles">
          <td>{{ role.id }}</td>
          <td>{{ role.name }}</td>
          <td>
            <button
              routerLink="/roles/edit/{{ role.id }}"
              class="btn btn-success me-2"
            >
              Modifier
            </button>
            <button
              (click)="deleteRole(role.id)"
              class="btn btn-danger"
            >
              Supprimer
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  `
})
export class RoleListComponent implements OnInit {
  roles: RoleDTO[] = [];

  constructor(private roleService: RoleService) {}

  ngOnInit() {
    this.loadRoles();
  }

  loadRoles() {
    this.roleService.getAllRoles().subscribe(
      roles => this.roles = roles,
      error => console.error('Error fetching roles', error)
    );
  }

  deleteRole(id: number) {
    if (confirm('Are you sure you want to delete this role?')) {
      this.roleService.deleteRole(id).subscribe(
        () => {
          this.roles = this.roles.filter(role => role.id !== id);
        },
        error => console.error('Error deleting role', error)
      );
    }
  }
}
