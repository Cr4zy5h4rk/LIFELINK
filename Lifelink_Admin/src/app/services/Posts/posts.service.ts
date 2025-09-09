import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Post {
  id: string;
  title: string;
  content: string;
  imageUrl: string;
  publishDate: string; // Format ISO
  author: string;
  audioLanguage: string;
  hasAudio: boolean;
  authorImageUrl: string;
}

export interface PaginatedPostsResponse {
  posts: Post[];
  total: number;
  page: number;
  limit: number;
  totalPages: number;
}

@Injectable({
  providedIn: 'root'
})
export class PostsService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  /**
   * Récupère tous les posts récents
   */
  getRecentPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/recent-posts`);
  }

  /**
   * Récupère les posts avec pagination
   */
  getPaginatedPosts(page: number = 1, limit: number = 10, category?: string): Observable<PaginatedPostsResponse> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());

    if (category) {
      params = params.set('category', category);
    }

    return this.http.get<PaginatedPostsResponse>(`${this.apiUrl}/posts/paginated`, { params });
  }

  /**
   * Récupère les témoignages
   */
  getTestimonies(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/testimonies`);
  }

  /**
   * Récupère un post par son ID
   */
  getPost(id: string): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/posts/${id}`);
  }

  /**
   * Crée un nouveau post
   */
  createPost(post: Partial<Post>): Observable<Post> {
    return this.http.post<Post>(`${this.apiUrl}/posts`, post);
  }

  /**
   * Met à jour un post existant
   */
  updatePost(id: string, post: Partial<Post>): Observable<Post> {
    return this.http.put<Post>(`${this.apiUrl}/posts/${id}`, post);
  }

  /**
   * Supprime un post
   */
  deletePost(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/posts/${id}`);
  }

  /**
   * Marque/démarque un post comme favori
   */
  toggleFavorite(id: string, isFavorite: boolean): Observable<Post> {
    return this.http.put<Post>(`${this.apiUrl}/posts/${id}/favorite`, { isFavorite });
  }
}