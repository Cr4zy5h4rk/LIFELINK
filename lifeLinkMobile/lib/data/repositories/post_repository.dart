// lib/data/repositories/post_repository.dart

import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/data/services/springboot_backend_service.dart';
import 'package:lifelink/domain/models/post.dart';

/// Repository pour gérer les posts et témoignages
class PostRepository {
  final SpringbootBackendService _apiService;

  ///Constructor
  PostRepository({SpringbootBackendService? apiService}) :
        _apiService = apiService ?? SpringbootBackendService(
          baseUrl: ServicesUrls.springBootUrl,
        );

  /// Récupère tous les posts récents
  Future<List<Post>> fetchRecentPosts() async {
    try {
      final dynamic data = await _apiService.get('/api/recent-posts');

      if (data == null || data is! List) {
        return [];
      }

      return data.map<Post>((item) =>
          Post.fromJson(item as Map<String, dynamic>),).toList();
    } catch (e) {
      throw Exception('Erreur lors de la récupération des posts récents: $e');
    }
  }

  /// Récupère les posts avec pagination
  Future<PaginatedPostsResponse> fetchPaginatedPosts({
    int page = 1, int limit = 10, String? category,
  }) async {
    try {
      // Construire les paramètres de requête
      var queryParams = '?page=$page&limit=$limit';
      if (category != null && category.isNotEmpty) {
        queryParams += '&category=$category';
      }

      final dynamic data = await _apiService.get('/api/posts/paginated$queryParams');

      if (data == null || data is! Map<String, dynamic>) {
        return PaginatedPostsResponse(
          posts: [],
          totalPages: 0,
          currentPage: page,
          totalPosts: 0,
        );
      }

      // Extraction des données de pagination
      final posts = (data['posts'] as List)
          .map<Post>((item) => Post.fromJson(item as Map<String, dynamic>))
          .toList();

      // Création de la réponse paginée
      return PaginatedPostsResponse(
        posts: posts,
        totalPages: data['totalPages'] as int? ?? 0,
        currentPage: data['currentPage'] as int? ?? page,
        totalPosts: data['totalPosts'] as int? ?? 0,
      );
    } catch (e) {
      throw Exception('Erreur lors de la récupération des posts paginés: $e');
    }
  }

  /// Récupère tous les témoignages
  Future<List<Post>> fetchTestimonies() async {
    try {
      final dynamic data = await _apiService.get('/api/testimonies');

      if (data == null || data is! List) {
        return [];
      }

      return data.map<Post>((item) =>
          Post.fromJson(item as Map<String, dynamic>),).toList();
    } catch (e) {
      throw Exception('Erreur lors de la récupération des témoignages: $e');
    }
  }

  /// Récupère un post par son ID
  Future<Post?> fetchPostById(String id) async {
    try {
      final dynamic data = await _apiService.get('/api/posts/$id');

      if (data == null || data is! Map<String, dynamic>) {
        return null;
      }

      return Post.fromJson(data);
    } catch (e) {
      throw Exception('Erreur lors de la récupération du post: $e');
    }
  }

  /// Pour la compatibilité avec le ViewModel du post_detail_screen
  Future<Post> getPostById(String id) async {
    final post = await fetchPostById(id);
    if (post == null) {
      throw Exception('Post non trouvé');
    }
    return post;
  }

  /// Pour la compatibilité avec le ViewModel du post_detail_screen
  Future<List<Post>> getPosts({String? category}) async {
    if (category == 'testimonies') {
      return fetchTestimonies();
    } else {
      return fetchRecentPosts();
    }
  }

  /// Met à jour le statut de favori d'un post
  Future<Post> toggleFavorite(String postId, bool isFavorite) async {
    try {
      // exemple endpoint a contacter
      // final data = await _apiService.put('/api/posts/$postId/favorite',
      //    data: {'isFavorite': isFavorite});

      // Pour le moment, on récupère simplement le post existant
      final post = await getPostById(postId);
      final updatedPost = Post(
        title: post.title,
        content: post.content,
        imageUrl: post.imageUrl,
        publishDate: post.publishDate,
        author: post.author,
        id: post.id,
        audioLanguage: post.audioLanguage,
        hasAudio: post.hasAudio,
        isFavorite: isFavorite,
        authorImageUrl: post.authorImageUrl,
      );

      // On pourrait le mettre à jour ici si l'API le supporte
      // return await updatePost(updatedPost, postId);

      return updatedPost;
    } catch (e) {
      throw Exception('Erreur lors de la mise à jour du statut favori: $e');
    }
  }

}

/// Classe pour représenter une réponse paginée de posts
class PaginatedPostsResponse {
  /// Liste des posts
  final List<Post> posts;

  /// Nombre total de pages
  final int totalPages;

  /// Page actuelle
  final int currentPage;

  /// Nombre total de posts
  final int totalPosts;

  /// Constructeur
  PaginatedPostsResponse({
    required this.posts,
    required this.totalPages,
    required this.currentPage,
    required this.totalPosts,
  });
}
