import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Post, PostsService } from '../../services/Posts/posts.service';

@Component({
  selector: 'app-post-form',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule],
  templateUrl: './post-form.component.html',
  styleUrls: ['./post-form.component.scss']
})
export class PostFormComponent implements OnInit {
  postForm!: FormGroup;
  isEditing = false;
  loading = false;
  submitting = false;
  error: string | null = null;
  postId: string | null = null;
  
  audioUploadFile: File | null = null;
  imageUploadFile: File | null = null;
  imagePreview: string | null = null;
  authorImagePreview: string | null = null;
  
  audioLanguages = ['Wolof', 'Français', 'Anglais'];
  categories = ['Article', 'Témoignage', 'Actualité', 'Santé', 'Éducation'];
  
  constructor(
    private fb: FormBuilder,
    private postsService: PostsService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initForm();
    
    // Vérifier s'il s'agit d'une modification
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditing = true;
      this.postId = id;
      this.loadPost(id);
    }
  }

  initForm(): void {
    this.postForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(200)]],
      content: ['', [Validators.required, Validators.minLength(10)]],
      imageUrl: [''],
      author: ['', Validators.required],
      authorImageUrl: [''],
      category: ['Article', Validators.required],
      audioLanguage: [''],
      hasAudio: [false],
      publishNow: [true]
    });
  }

  loadPost(id: string): void {
    this.loading = true;
    this.error = null;
    
    this.postsService.getPost(id).subscribe({
      next: (post: Post) => {
        // Pré-remplir le formulaire avec les données existantes
        this.postForm.patchValue({
          title: post.title,
          content: post.content,
          imageUrl: post.imageUrl,
          author: post.author,
          authorImageUrl: post.authorImageUrl,
          audioLanguage: post.audioLanguage,
          hasAudio: post.hasAudio,
          publishNow: true
        });
        
        // Prévisualisations des images
        if (post.imageUrl) {
          this.imagePreview = post.imageUrl;
        }
        if (post.authorImageUrl) {
          this.authorImagePreview = post.authorImageUrl;
        }
        
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement de l\'article', err);
        this.error = 'Impossible de charger les données de l\'article. Veuillez réessayer plus tard.';
        this.loading = false;
      }
    });
  }

  onImageSelected(event: Event, type: 'article' | 'author'): void {
    const inputElement = event.target as HTMLInputElement;
    if (!inputElement.files || !inputElement.files.length) {
      return;
    }
    
    const file = inputElement.files[0];
    if (type === 'article') {
      this.imageUploadFile = file;
      // Prévisualisation de l'image
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result as string;
      };
      reader.readAsDataURL(file);
    } else {
      // Pour l'image de l'auteur
      const reader = new FileReader();
      reader.onload = () => {
        this.authorImagePreview = reader.result as string;
      };
      reader.readAsDataURL(file);
    }
  }

  onAudioSelected(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (!inputElement.files || !inputElement.files.length) {
      return;
    }
    
    this.audioUploadFile = inputElement.files[0];
    // Activer la case à cocher hasAudio
    this.postForm.patchValue({ hasAudio: true });
  }

  onSubmit(): void {
    if (this.postForm.invalid) {
      // Marquer tous les champs comme touchés pour afficher les erreurs
      Object.keys(this.postForm.controls).forEach(key => {
        this.postForm.get(key)?.markAsTouched();
      });
      return;
    }
    
    this.submitting = true;
    this.error = null;
    
    // Dans une application réelle, nous devrions d'abord télécharger les fichiers
    // et récupérer leurs URLs, mais pour l'exemple, nous utiliserons les URLs existantes
    
    const postData: Partial<Post> = {
      ...this.postForm.value,
      publishDate: this.postForm.value.publishNow ? new Date().toISOString() : undefined
    };
    
    if (this.isEditing && this.postId) {
      this.postsService.updatePost(this.postId, postData).subscribe({
        next: () => {
          this.router.navigate(['/dashboard/articles']);
        },
        error: (err) => {
          console.error('Erreur lors de la mise à jour de l\'article', err);
          this.error = 'Impossible de mettre à jour l\'article. Veuillez réessayer plus tard.';
          this.submitting = false;
        }
      });
    } else {
      this.postsService.createPost(postData).subscribe({
        next: () => {
          this.router.navigate(['/dashboard/articles']);
        },
        error: (err) => {
          console.error('Erreur lors de la création de l\'article', err);
          this.error = 'Impossible de créer l\'article. Veuillez réessayer plus tard.';
          this.submitting = false;
        }
      });
    }
  }
  
  // Méthodes pour faciliter l'accès aux contrôles du formulaire
  get title() { return this.postForm.get('title'); }
  get content() { return this.postForm.get('content'); }
  get author() { return this.postForm.get('author'); }
  get category() { return this.postForm.get('category'); }
}