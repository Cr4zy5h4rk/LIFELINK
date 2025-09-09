import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/data/services/springboot_backend_service.dart';
import 'package:lifelink/domain/models/campaign.dart';

/// Repository pour gérer les campagnes de don de sang
class CampaignRepository {
  final SpringbootBackendService _apiService;

  ///constructor
  CampaignRepository({SpringbootBackendService? apiService}) :
        _apiService = apiService ?? SpringbootBackendService(
            baseUrl: ServicesUrls.springBootUrl,
        );

  /// Récupère toutes les campagnes
  Future<List<Campaign>> fetchCampaigns() async {
    try {
      final dynamic data = await _apiService.get('/api/campaigns');

      if (data == null || data is! List) {
        return [];
      }

      return data.map<Campaign>((item) =>
          Campaign.fromJson(item as Map<String, dynamic>),).toList();
    } catch (e) {
      throw Exception('Erreur lors de la récupération des campagnes: $e');
    }
  }

  /// Récupère une campagne par son ID
  Future<Campaign?> fetchCampaignById(String id) async {
    try {
      final dynamic data = await _apiService.get('/api/campaigns/$id');

      if (data == null || data is! Map<String, dynamic>) {
        return null;
      }

      return Campaign.fromJson(data);
    } catch (e) {
      throw Exception('Erreur lors de la récupération de la campagne: $e');
    }
  }

  /// Ajoute une nouvelle campagne
  Future<Campaign> addCampaign(Campaign campaign) async {
    try {
      final dynamic data = await _apiService.post('/api/campaigns', data: campaign.toJson());

      if (data == null || data is! Map<String, dynamic>) {
        throw Exception('Format de réponse API inattendu');
      }

      return Campaign.fromJson(data);
    } catch (e) {
      throw Exception("Erreur lors de l'ajout de la campagne: $e");
    }
  }

  /// Met à jour une campagne existante
  Future<Campaign> updateCampaign(Campaign campaign) async {
    try {
      final dynamic data = await _apiService.put(
          '/api/campaigns/${campaign.id}',
          data: campaign.toJson(),
      );

      if (data == null || data is! Map<String, dynamic>) {
        throw Exception('Format de réponse API inattendu');
      }

      return Campaign.fromJson(data);
    } catch (e) {
      throw Exception('Erreur lors de la mise à jour de la campagne: $e');
    }
  }

  /// Supprime une campagne
  Future<void> deleteCampaign(String id) async {
    try {
      await _apiService.delete('/api/campaigns/$id');
    } catch (e) {
      throw Exception('Erreur lors de la suppression de la campagne: $e');
    }
  }
}
