import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/data/services/springboot_backend_service.dart';
import 'package:lifelink/domain/models/campaign.dart';
import 'package:lifelink/domain/models/emergency.dart';
import 'package:lifelink/domain/models/home_data.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/domain/models/statistics.dart';

/// Repository pour récupérer les données composites de l'écran d'accueil
class HomeDataRepository {
  final SpringbootBackendService _apiService;

  ///Constructor
  HomeDataRepository({SpringbootBackendService? apiService}) :
        _apiService = apiService ?? SpringbootBackendService(
          baseUrl: ServicesUrls.springBootUrl,
        );

  /// Récupère toutes les données d'accueil en une seule requête
  Future<HomeData> fetchHomeData() async {
    try {
      final dynamic responseData = await _apiService.get('/api/home');

      if (responseData == null || responseData is! Map<String, dynamic>) {
        throw Exception('Format de réponse API inattendu');
      }

      // Convertir les données JSON en objets de domaine
      final emergencies = ((responseData['emergencies'] ?? <dynamic>[]) as List)
          .map((item) => Emergency.fromJson(item as Map<String, dynamic>))
          .toList();

      final campaigns = ((responseData['campaigns'] ?? <dynamic>[]) as List)
          .map((item) => Campaign.fromJson(item as Map<String, dynamic>))
          .toList();

      final testimonies = ((responseData['testimonies'] ?? <dynamic>[]) as List)
          .map((item) => Post.fromJson(item as Map<String, dynamic>))
          .toList();

      final statistics = Statistics.fromJson(
          (responseData['statistics'] ?? <String, dynamic>{})
          as Map<String, dynamic>,);

      final recentPosts = ((responseData['recentPosts'] ?? <dynamic>[]) as List)
          .map((item) => Post.fromJson(item as Map<String, dynamic>))
          .toList();


      return HomeData(
        emergencies: emergencies,
        campaigns: campaigns,
        testimonies: testimonies,
        statistics: statistics,
        recentPosts: recentPosts,
      );
    } catch (e) {
      throw Exception('Erreur lors de la récupération '
          "des données d'accueil: $e");
    }
  }
}
