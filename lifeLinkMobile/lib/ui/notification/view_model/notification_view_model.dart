import 'package:flutter/material.dart';

import 'package:lifelink/data/repositories/campaign_repository.dart';
import 'package:lifelink/data/repositories/emergency_repository.dart';
import 'package:lifelink/domain/models/campaign.dart';
import 'package:lifelink/domain/models/emergency.dart';

/// ViewModel pour les notifications
class NotificationViewModel extends ChangeNotifier {
  // Repositories spécifiques au lieu du BloodDonationRepository général
  final EmergencyRepository _emergencyRepository = EmergencyRepository();
  final CampaignRepository _campaignRepository = CampaignRepository();

  List<Emergency> _emergencies = <Emergency>[];
  List<Campaign> _campaigns = <Campaign>[];
  bool _isLoading = false;
  String _errorMessage = '';

  /// Getters pour les données
  List<Emergency> get emergencies => _emergencies;
  /// Getters pour les données
  List<Campaign> get campaigns => _campaigns;
  /// Getters pour les données
  bool get isLoading => _isLoading;
  /// Getters pour les données
  String get errorMessage => _errorMessage;

  /// Charge les notifications depuis les repositories
  Future<void> loadNotifications() async {
    try {
      _isLoading = true;
      notifyListeners();

      // Récupère les données depuis les repositories
      final emergenciesFuture = _emergencyRepository.fetchEmergencies();
      final campaignsFuture = _campaignRepository.fetchCampaigns();

      // Exécute les requêtes en parallèle
      final results = await Future.wait<dynamic>([
        emergenciesFuture,
        campaignsFuture,
      ]);

      // Assigne les résultats avec conversion explicite
      _emergencies = results[0] as List<Emergency>;
      _campaigns = results[1] as List<Campaign>;

      _isLoading = false;
      _errorMessage = '';
      notifyListeners();
    } catch (e) {
      _isLoading = false;
      _errorMessage = 'Erreur lors du chargement des notifications: $e';
      notifyListeners();
    }
  }

  /// Rafraîchit uniquement les données d'urgence
  Future<void> refreshEmergencies() async {
    try {
      final emergencies = await _emergencyRepository.fetchEmergencies();
      _emergencies = emergencies;
      notifyListeners();
    } catch (e) {
      _errorMessage = 'Erreur lors du rafraîchissement des urgences: $e';
      notifyListeners();
    }
  }

  /// Rafraîchit uniquement les données de campagne
  Future<void> refreshCampaigns() async {
    try {
      final campaigns = await _campaignRepository.fetchCampaigns();
      _campaigns = campaigns;
      notifyListeners();
    } catch (e) {
      _errorMessage = 'Erreur lors du rafraîchissement des campagnes: $e';
      notifyListeners();
    }
  }
}
