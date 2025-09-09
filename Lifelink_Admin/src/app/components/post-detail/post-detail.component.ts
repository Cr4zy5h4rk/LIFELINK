import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Post, PostsService } from '../../services/Posts/posts.service';

@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent implements OnInit {
  post: Post | null = null;
  loading = true;
  error: string | null = null;
  
  constructor(
    private postsService: PostsService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadPost(id);
    } else {
      this.error = 'Aucun identifiant d\'article fourni';
      this.loading = false;
    }
  }

  loadPost(id: string): void {
    this.loading = true;
    this.error = null;
    
    this.postsService.getPost(id).subscribe({
      next: (post: Post) => {
        this.post = post;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement de l\'article', err);
        this.error = 'Impossible de charger les données de l\'article. Veuillez réessayer plus tard.';
        this.loading = false;
      }
    });
  }


  deletePost(): void {
    if (!this.post) return;
    
    if (confirm('Êtes-vous sûr de vouloir supprimer cet article ? Cette action est irréversible.')) {
      this.postsService.deletePost(this.post.id).subscribe({
        next: () => {
          this.router.navigate(['/dashboard/articles']);
        },
        error: (err) => {
          console.error('Erreur lors de la suppression de l\'article', err);
          alert('Impossible de supprimer l\'article. Veuillez réessayer plus tard.');
        }
      });
    }
  }

  formatDate(dateString: string | undefined): string {
    if (!dateString) return '';
    
    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', { 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}