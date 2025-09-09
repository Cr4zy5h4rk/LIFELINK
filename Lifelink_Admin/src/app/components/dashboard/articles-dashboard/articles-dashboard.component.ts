import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PaginatedPostsResponse, Post, PostsService } from '../../../services/Posts/posts.service';

@Component({
  selector: 'app-articles-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ],
  templateUrl: './articles-dashboard.component.html',
  styleUrls: ['./articles-dashboard.component.scss']
})
export class ArticlesDashboardComponent implements OnInit {
  posts: Post[] = [];
  loading = true;
  error: string | null = null;
  
  // Pagination
  currentPage = 1;
  totalPages = 0;
  totalPosts = 0;
  postsPerPage = 10;
  
  // Filtre
  searchTerm = '';
  categoryFilter = '';
  
  // Options
  categories = ['Article', 'Témoignage', 'Actualité', 'Santé', 'Éducation'];
  
  constructor(private postsService: PostsService) { }

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.loading = true;
    this.error = null;
    
    this.postsService.getPaginatedPosts(this.currentPage, this.postsPerPage, this.categoryFilter)
      .subscribe({
        next: (response: PaginatedPostsResponse) => {
          this.posts = response.posts;
          this.totalPages = response.totalPages;
          this.totalPosts = response.total;
          this.loading = false;
        },
        error: (err) => {
          console.error('Erreur lors du chargement des articles', err);
          this.error = 'Impossible de charger les articles. Veuillez réessayer plus tard.';
          this.loading = false;
        }
      });
  }

  applyFilters(): void {
    this.currentPage = 1; // Revenir à la première page lors du filtrage
    this.loadPosts();
  }

  resetFilters(): void {
    this.searchTerm = '';
    this.categoryFilter = '';
    this.currentPage = 1;
    this.loadPosts();
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.loadPosts();
    }
  }

  deletePost(id: string): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet article ?')) {
      this.postsService.deletePost(id).subscribe({
        next: () => {
          this.posts = this.posts.filter(post => post.id !== id);
          this.loadPosts(); // Recharger pour mettre à jour la pagination
        },
        error: (err) => {
          console.error('Erreur lors de la suppression de l\'article', err);
          alert('Impossible de supprimer l\'article. Veuillez réessayer plus tard.');
        }
      });
    }
  }

  // Méthode pour générer un tableau de numéros de page pour la pagination
  get pagesArray(): number[] {
    const pagesArray: number[] = [];
    const maxPagesToShow = 5;
    
    if (this.totalPages <= maxPagesToShow) {
      // Afficher toutes les pages si leur nombre est inférieur ou égal au max
      for (let i = 1; i <= this.totalPages; i++) {
        pagesArray.push(i);
      }
    } else {
      // Afficher un sous-ensemble de pages avec la page actuelle au milieu si possible
      let startPage = Math.max(1, this.currentPage - Math.floor(maxPagesToShow / 2));
      let endPage = startPage + maxPagesToShow - 1;
      
      if (endPage > this.totalPages) {
        endPage = this.totalPages;
        startPage = Math.max(1, endPage - maxPagesToShow + 1);
      }
      
      for (let i = startPage; i <= endPage; i++) {
        pagesArray.push(i);
      }
    }
    
    return pagesArray;
  }

  formatDate(dateString: string): string {
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', { 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  truncateContent(content: string, maxLength: number = 100): string {
    if (content.length <= maxLength) return content;
    return content.substring(0, maxLength) + '...';
  }
}