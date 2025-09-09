// lib/ui/post/view_model/post_view_model.dart

import 'package:flutter/material.dart';
import 'package:lifelink/data/repositories/post_repository.dart';
import 'package:lifelink/domain/models/post.dart';

/// ViewModel pour les écrans de Post
class PostViewModel extends ChangeNotifier {
  /// Repository pour accéder aux données de post
  final PostRepository _postRepository;

  /// Post actuel
  Post? _currentPost;

  /// État de chargement
  bool _isLoading = false;

  /// Message d'erreur s'il y en a un
  String? _errorMessage;

  /// Liste des posts
  List<Post> _posts = [];

  /// Indique si on a atteint la fin de tous les posts disponibles
  bool _hasReachedEnd = false;

  /// Page actuelle pour la pagination
  int _currentPage = 1;

  /// Nombre total de pages disponibles
  int _totalPages = 1;

  /// Constructeur
  PostViewModel(this._postRepository);

  /// Obtenir le post actuel
  Post? get currentPost => _currentPost;

  /// Vérifier si les données sont en cours de chargement
  bool get isLoading => _isLoading;

  /// Obtenir le message d'erreur
  String? get errorMessage => _errorMessage;

  /// Obtenir la liste des posts
  List<Post> get posts => _posts;

  /// Vérifier si on a atteint la fin des posts
  bool get hasReachedEnd => _hasReachedEnd;

  /// Charger un post par ID
  Future<void> loadPost(String id) async {
    _isLoading = true;
    _errorMessage = null;
    notifyListeners();

    try {
      final post = await _postRepository.fetchPostById(id);
      if (post != null) {
        _currentPost = post;
      } else {
        throw Exception('Post non trouvé');
      }
    } catch (e) {
      _errorMessage = 'Erreur lors du chargement du post: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  /// Charger les posts avec pagination
  Future<void> loadPaginatedPosts({
    int page = 1, int limit = 10, String? category,
  }) async {
    if (page == 1) {
      _isLoading = true;
      _posts = [];
      _hasReachedEnd = false;
      _currentPage = 1;
      notifyListeners();
    }

    try {
      // Utilisation de l'API de pagination
      final response = await _postRepository.fetchPaginatedPosts(
        page: page,
        limit: limit,
        category: category,
      );

      if (page == 1) {
        _posts = response.posts;
      } else {
        _posts.addAll(response.posts);
      }

      _currentPage = response.currentPage;
      _totalPages = response.totalPages;
      _hasReachedEnd = response.currentPage >= response.totalPages;

      _errorMessage = null;
    } catch (e) {
      _errorMessage = 'Erreur lors du chargement des posts: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  /// Charger plus de posts (utilisé pour l'infini scroll)
  Future<void> loadMorePosts({
    int? page, int limit = 10, String? category,
  }) async {
    if (_hasReachedEnd) {
      return;
    }

    // Si la page n'est pas spécifiée, utiliser la page suivante
    final nextPage = page ?? _currentPage + 1;

    await loadPaginatedPosts(
      page: nextPage,
      limit: limit,
      category: category,
    );
  }

  /// Charger tous les posts récents
  Future<void> loadRecentPosts() async {
    _isLoading = true;
    _errorMessage = null;
    _posts = [];
    notifyListeners();

    try {
      _posts = await _postRepository.fetchRecentPosts();
      if (_posts.isEmpty) {
        _errorMessage = 'Aucun post récent trouvé';
      }
    } catch (e) {
      _errorMessage = 'Erreur lors du chargement des posts: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  /// Charger tous les témoignages
  Future<void> loadTestimonies() async {
    _isLoading = true;
    _errorMessage = null;
    _posts = [];
    notifyListeners();

    try {
      _posts = await _postRepository.fetchTestimonies();
      if (_posts.isEmpty) {
        _errorMessage = 'Aucun témoignage trouvé';
      }
    } catch (e) {
      _errorMessage = 'Erreur lors du chargement des témoignages: $e';
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }

  /// Basculer l'état favori d'un post
  Future<void> toggleFavorite() async {
    if (_currentPost == null || _currentPost?.id == null) {
      return;
    }

    final currentPost = _currentPost!;
    final isFavorite = !currentPost.isFavorite;

    try {
      // Mise à jour optimiste
      _currentPost = Post(
        title: currentPost.title,
        content: currentPost.content,
        imageUrl: currentPost.imageUrl,
        publishDate: currentPost.publishDate,
        author: currentPost.author,
        id: currentPost.id,
        audioLanguage: currentPost.audioLanguage,
        hasAudio: currentPost.hasAudio,
        isFavorite: isFavorite,
        authorImageUrl: currentPost.authorImageUrl,
      );
      notifyListeners();

      // Update on database when endpoint accessible
      // await _postRepository.toggleFavorite(currentPost.id!, isFavorite);
    } catch (e) {
      // Rétablir en cas d'erreur
      _currentPost = Post(
        title: currentPost.title,
        content: currentPost.content,
        imageUrl: currentPost.imageUrl,
        publishDate: currentPost.publishDate,
        author: currentPost.author,
        id: currentPost.id,
        audioLanguage: currentPost.audioLanguage,
        hasAudio: currentPost.hasAudio,
        isFavorite: !isFavorite,
        authorImageUrl: currentPost.authorImageUrl,
      );
      _errorMessage = 'Erreur lors de la mise à jour du statut favori';
      notifyListeners();
    }
  }
}
