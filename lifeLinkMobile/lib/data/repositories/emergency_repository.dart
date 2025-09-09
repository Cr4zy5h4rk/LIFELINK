import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/data/services/springboot_backend_service.dart';
import 'package:lifelink/domain/models/emergency.dart';

/// Repository pour gérer les urgences de don de sang
class EmergencyRepository {
  final SpringbootBackendService _apiService;

  ///Constructor
  EmergencyRepository({SpringbootBackendService? apiService}) :
        _apiService = apiService ?? SpringbootBackendService(
          baseUrl: ServicesUrls.springBootUrl,
        );

  /// Récupère toutes les urgences
  Future<List<Emergency>> fetchEmergencies() async {
    try {
      final dynamic data = await _apiService.get('/api/pending/emergencies');

      if (data == null || data is! List) {
        return [];
      }

      return data.map<Emergency>((item) =>
          Emergency.fromJson(item as Map<String, dynamic>),).toList();
    } catch (e) {
      throw Exception('Erreur lors de la récupération des urgences: $e');
    }
  }

  /// Récupère une urgence par son ID
  Future<Emergency?> fetchEmergencyById(String id) async {
    try {
      final dynamic data = await _apiService.get('/api/emergencies/$id');

      if (data == null || data is! Map<String, dynamic>) {
        return null;
      }

      return Emergency.fromJson(data);
    } catch (e) {
      throw Exception("Erreur lors de la récupération de l'urgence: $e");
    }
  }

}
