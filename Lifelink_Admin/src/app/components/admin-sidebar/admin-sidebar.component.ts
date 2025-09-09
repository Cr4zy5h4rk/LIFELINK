import { Component, EventEmitter, HostListener, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ShareInfoRequestService } from '../../services/ShareInfo/share-info-request-service.service';
import { DonorDTO } from '../../models/donor';

interface NavItem {
  routeLink: string;
  icon: string;
  label: string;
  badge?: string;
  badgeClass?: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-sidebar.component.html',
  styleUrls: ['./admin-sidebar.component.scss']
})
export class AdminSidebarComponent implements OnInit {
  @Output() onToggleSidebar: EventEmitter<boolean> = new EventEmitter<boolean>();
  collapsed: boolean = true;
  screenWidth: number = 0;
  navData: NavItem[] = [];
  userInfo: DonorDTO | undefined;
  pendingRequestsCount: number = 0;

  constructor(
    private authService: AuthService,
    private shareInfoService: ShareInfoRequestService
  ) {}

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth <= 992) {
      this.collapsed = false;
    } else {
      this.collapsed = true;
    }
  }

  ngOnInit(): void {
    this.screenWidth = window.innerWidth;
    if (this.screenWidth <= 992) {
      this.collapsed = false;
    }
    
    // this.loadPendingRequestsCount();
    this.loadNavData();
    this.getUser();
    
    // Actualiser le compteur toutes les minutes
    // setInterval(() => this.loadPendingRequestsCount(), 60000);
  }

  // loadPendingRequestsCount(): void {
  //   this.shareInfoService.getPendingRequests().subscribe({
  //     next: (requests) => {
  //       this.pendingRequestsCount = requests.length;
  //       this.updateShareInfoBadge();
  //     },
  //     error: (error) => console.error('Erreur lors du chargement des demandes en attente', error)
  //   });
  // }
  
  updateShareInfoBadge(): void {
    // Mettre à jour le badge dans la navigation
    const shareInfoItem = this.navData.find(item => item.routeLink === '/dashboard/share-info');
    if (shareInfoItem) {
      if (this.pendingRequestsCount > 0) {
        shareInfoItem.badge = this.pendingRequestsCount.toString();
        shareInfoItem.badgeClass = 'bg-warning';
      } else {
        shareInfoItem.badge = undefined;
      }
    }
  }

  loadNavData() {
    this.navData = [
      {
        routeLink: '/dashboard/main',
        icon: 'bi bi-speedometer2',
        label: 'Tableau de bord',
        badge: 'Nouveau',
        badgeClass: 'bg-success'
      },
      {
        routeLink: '/dashboard/stock',
        icon: 'bi bi-droplet',
        label: 'Stock de sang'
      },
      {
        routeLink: '/dashboard/urgences',
        icon: 'bi bi-exclamation-triangle',
        label: 'Urgences',
        badge: '3',
        badgeClass: 'bg-danger'
      },
      {
        routeLink: '/dashboard/articles',
        icon: 'bi bi-newspaper',
        label: 'Articles'
      },
      {
        routeLink: '/dashboard/share-info',
        icon: 'bi bi-share',
        label: 'Partage d\'informations',
        // Le badge sera ajouté dynamiquement
      },
      {
        routeLink: '/dashboard/campagnes',
        icon: 'bi bi-calendar-event',
        label: 'Campagnes'
      },
      {
        routeLink: '/dashboard/donors',
        icon: 'bi bi-people',
        label: 'Donneurs'
      },
      {
        routeLink: '/dashboard/roles',
        icon: 'bi bi-person-badge',
        label: 'Gestion des rôles'
      }
    ];
    
    // Appliquer le badge initial s'il est déjà chargé
    this.updateShareInfoBadge();
  }

  toggleCollapse(): void {
    this.collapsed = !this.collapsed;
    this.onToggleSidebar.emit(this.collapsed);
  }

  closeSidenav(): void {
    this.collapsed = false;
    this.onToggleSidebar.emit(this.collapsed);
  }

  logout(): void {
    this.authService.logout();
  }

  getUser(){
    if (localStorage.getItem('userInfo')) {
      this.userInfo = JSON.parse(localStorage.getItem('userInfo')!);
    }
  }
}