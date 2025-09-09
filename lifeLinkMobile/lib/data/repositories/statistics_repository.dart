import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/data/services/springboot_backend_service.dart';
import 'package:lifelink/domain/models/statistics.dart';

/// Repository pour gérer les statistiques de don de sang
class StatisticsRepository {
  final SpringbootBackendService _apiService;

  ///Constructor
  StatisticsRepository({SpringbootBackendService? apiService}) :
        _apiService = apiService ?? SpringbootBackendService(
          baseUrl: ServicesUrls.springBootUrl,
        );

  /// Récupère les statistiques globales
  Future<Statistics> fetchStatistics() async {
    try {
      final dynamic data = await _apiService.get('/api/statistics');

      if (data == null || data is! Map<String, dynamic>) {
        return Statistics(donorsCount: 0,
            objectivePercentage: 0,
            livesSaved: 0,
        );
      }

      return Statistics.fromJson(data);
    } catch (e) {
      throw Exception('Erreur lors de la récupération des statistiques: $e');
    }
  }

  /// Met à jour les statistiques
  Future<Statistics> updateStatistics(Statistics statistics) async {
    try {
      final dynamic data = await _apiService.put(
          '/api/statistics',
          data: statistics.toJson(),
      );

      if (data == null || data is! Map<String, dynamic>) {
        throw Exception('Format de réponse API inattendu');
      }

      return Statistics.fromJson(data);
    } catch (e) {
      throw Exception('Erreur lors de la mise à jour des statistiques: $e');
    }
  }
}
