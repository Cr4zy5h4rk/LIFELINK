import 'package:flutter/material.dart';

import 'package:lifelink/data/repositories/campaign_repository.dart';
import 'package:lifelink/data/repositories/emergency_repository.dart';
import 'package:lifelink/data/repositories/home_data_repository.dart';
import 'package:lifelink/data/repositories/post_repository.dart';
import 'package:lifelink/data/repositories/statistics_repository.dart';
import 'package:lifelink/domain/models/campaign.dart';
import 'package:lifelink/domain/models/emergency.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/domain/models/statistics.dart';

/// View model for home screen
class HomeViewModel extends ChangeNotifier {
  // Repositories
  final HomeDataRepository _homeDataRepository = HomeDataRepository();
  final EmergencyRepository _emergencyRepository = EmergencyRepository();
  final CampaignRepository _campaignRepository = CampaignRepository();
  final PostRepository _postRepository = PostRepository();
  final StatisticsRepository _statisticsRepository = StatisticsRepository();

  // Data
  List<Emergency> _emergencies = <Emergency>[];
  List<Campaign> _campaigns = <Campaign>[];
  List<Post> _testimonies = <Post>[];
  Statistics? _statistics;
  List<Post> _recentPosts = <Post>[];

  // Status
  bool _isLoading = false;
  String _errorMessage = '';

  /// Emergencies getter
  List<Emergency> get emergencies => _emergencies;
  /// Campaigns getter
  List<Campaign> get campaigns => _campaigns;
  /// Testimonies getter
  List<Post> get testimonies => _testimonies;
  /// Statistics getter
  Statistics? get statistics => _statistics;
  /// recent posts getter
  List<Post> get recentPosts => _recentPosts;

  /// Get loading status
  bool get isLoading => _isLoading;
  /// Get error message
  String get errorMessage => _errorMessage;

  /// Charge les données d'accueil en une seule requête
  Future<void> loadHomeData() async {
    try {
      _isLoading = true;
      notifyListeners();

      // Récupère toutes les données en une seule requête
      final homeData = await _homeDataRepository.fetchHomeData();

      // Assignation directe des propriétés de HomeData
      _emergencies = homeData.emergencies;
      _campaigns = homeData.campaigns;
      _testimonies = homeData.testimonies;
      _statistics = homeData.statistics;
      _recentPosts = homeData.recentPosts;

      _isLoading = false;
      _errorMessage = '';
      notifyListeners();
    } catch (e) {
      _isLoading = false;
      _errorMessage = 'Erreur lors du chargement des données: $e';
      notifyListeners();
    }
  }

  /// Charge chaque type de données séparément
  Future<void> loadHomeDataSeparately() async {
    try {
      _isLoading = true;
      notifyListeners();

      // Récupère chaque type de données séparément
      final emergenciesFuture = _emergencyRepository.fetchEmergencies();
      final campaignsFuture = _campaignRepository.fetchCampaigns();
      final testimoniesFuture = _postRepository.fetchTestimonies();
      final statisticsFuture = _statisticsRepository.fetchStatistics();
      final recentPostsFuture = _postRepository.fetchRecentPosts();

      // Attend que toutes les requêtes soient terminées
      final results = await Future.wait<dynamic>([
        emergenciesFuture,
        campaignsFuture,
        testimoniesFuture,
        statisticsFuture,
        recentPostsFuture,
      ]);

      // Assigne les résultats avec conversion explicite
      _emergencies = results[0] as List<Emergency>;
      _campaigns = results[1] as List<Campaign>;
      _testimonies = results[2] as List<Post>;
      _statistics = results[3] as Statistics;
      _recentPosts = results[4] as List<Post>;

      _isLoading = false;
      _errorMessage = '';
      notifyListeners();
    } catch (e) {
      _isLoading = false;
      _errorMessage = 'Erreur lors du chargement des données: $e';
      notifyListeners();
    }
  }

}
