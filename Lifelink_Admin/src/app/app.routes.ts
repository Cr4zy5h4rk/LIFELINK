import { Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { MainComponent } from './components/dashboard/main/main.component';
import { StockComponent } from './components/dashboard/stock/stock.component';
import { CarnetsComponent } from './components/dashboard/carnets/carnets.component';
import { ArticlesDashboardComponent } from './components/dashboard/articles-dashboard/articles-dashboard.component';
import { BloodDonationDashboardComponent } from './components/dashboard/blood-donation-dashboard/blood-donation-dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { LayoutComponent } from './components/layout/layout.component';
import { LoginComponent } from './components/login/login.component';
import { CentresComponent } from './components/centres/centres.component';
import {DonorListComponent} from "./components/donor-list/donor-list.component";
import {RoleEditComponent} from "./components/role-edit/role-edit.component";
import {DonorEditComponent} from "./components/donor-edit/donor-edit.component";
import {RoleListComponent} from "./components/role-list/role-list.component";
import { CallbackComponent } from './components/callback/callback.component';
import { isAdminGuard, isAuthenticatedGuard } from './utils/auth.guard';
import { PostFormComponent } from './components/post-form/post-form.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { ShareInfoRequestComponent } from './components/share-info-request/share-info-request.component';
import { AlerteComponent } from './components/dashboard/alerte/alerte.component';
import { PublicEmergenciesComponent } from './components/public-emergencies/public-emergencies.component';
import { BloodStockComponent } from './components/blood-stock/blood-stock.component';
import { ContactComponent } from './components/contact/contact.component';
// app.routes.ts
export const routes: Routes = [
    { path: '', component: LayoutComponent, children: [
        { path: '', component: HomeComponent },
        { path: 'centres', component: CentresComponent },
        { path: 'centres/:id', component: CentresComponent },
        { path: 'urgences', component: PublicEmergenciesComponent },
        { path:'contact', component: ContactComponent}
    ]},
    { 
        path: 'dashboard', 
        component: DashboardComponent,
        canActivate: [isAuthenticatedGuard, isAdminGuard],
        children:[
            { path: 'main', component: MainComponent },
            { path: 'stock', component: BloodStockComponent },
            // { path: 'alertes', component: AlerteComponent },
            // { path: 'carnets', component: CarnetsComponent },
            { path: 'urgences', component: AlerteComponent },
            
            // Routes de gestion des articles
            { path: 'articles', component: ArticlesDashboardComponent },
            { path: 'articles/new', component: PostFormComponent },
            { path: 'articles/edit/:id', component: PostFormComponent },
            { path: 'articles/view/:id', component: PostDetailComponent },
            
            // Route pour la gestion des demandes de partage d'informations
            { path: 'share-info', component: ShareInfoRequestComponent },
            
            // Autres routes existantes
            { path: 'campagnes', component: BloodDonationDashboardComponent },
            { path: 'donors', component: DonorListComponent },
            { path: 'donors/new', component: DonorEditComponent },
            { path: 'donors/edit/:id', component: DonorEditComponent },
            { path: 'donors/details/:id', component: CarnetsComponent },
            { path: 'roles', component : RoleListComponent },
            { path: 'roles/new', component: RoleEditComponent },
            { path: 'roles/edit/:id', component: RoleEditComponent },
            { path: 'profile', component: UserProfileComponent },
            
            // Redirection par défaut
            { path: '', redirectTo: 'main', pathMatch: 'full' }
        ] 
    },
    { path: 'login', component: LoginComponent },
    { path: 'callback', component: CallbackComponent },
    { path: '**', redirectTo: '' }
];