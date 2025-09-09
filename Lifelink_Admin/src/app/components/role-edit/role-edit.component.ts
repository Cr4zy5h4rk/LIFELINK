import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import {RoleService} from "../../services/role.service";
import {RoleDTO} from "../../models/role";

@Component({
  selector: 'app-role-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, HttpClientModule],
  template: `
    <div class="container my-4">
      <h2 class="h2 mb-4">
        {{ isNewRole ? 'Créer un' : 'Modifier' }} Rôle
      </h2>
      <form (ngSubmit)="onSubmit()" class="mw-100" style="max-width: 32rem;">
        <div class="mb-3">
          <label class="form-label">Nom du Rôle</label>
          <input
            type="text"
            [(ngModel)]="role.name"
            name="name"
            class="form-control"
            required
          >
        </div>
        <div class="d-flex gap-3">
          <button
            type="submit"
            class="btn btn-primary"
          >
            {{ isNewRole ? 'Créer' : 'Mettre à jour' }}
          </button>
          <button
            type="button"
            routerLink="/roles"
            class="btn btn-secondary"
          >
            Annuler
          </button>
        </div>
      </form>
    </div>
  `
})
export class RoleEditComponent implements OnInit {
  role: RoleDTO = {
    id: 0,
    name: '',
  };
  isNewRole = true;

  constructor(
    private roleService: RoleService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const roleId = this.route.snapshot.paramMap.get('id');
    if (roleId) {
      this.isNewRole = false;
      this.loadRole(+roleId);
    }
  }

  loadRole(id: number) {
    this.roleService.getRoleById(id).subscribe(
      role => this.role = role,
      error => console.error('Error fetching role', error)
    );
  }

  onSubmit() {
    if (this.isNewRole) {
      this.roleService.createRole(this.role).subscribe(
        () => this.router.navigate(['/roles']),
        error => console.error('Error creating role', error)
      );
    } else {
      this.roleService.updateRole(this.role).subscribe(
        () => this.router.navigate(['/roles']),
        error => console.error('Error updating role', error)
      );
    }
  }
}
