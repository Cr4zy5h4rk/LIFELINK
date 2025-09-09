import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/domain/models/user_shared_info.dart';


class ShareInfoRequestRepo {
  final String baseUrl = ServicesUrls.springBootUrl;

  List<Map<String, dynamic>> generateFakeRequestsData(String phoneNumber) {
    return [
    {
      'id': 'req-001',
    'requestDate': DateTime.now().subtract(Duration(days: 3)).toIso8601String(),
    'acceptanceDate': null,
    'isAccepted': false,
    'phoneNumber': phoneNumber,
    'sharedInfoSelection': '{"status":"rejected","feedback":"Je ne suis pas disponible pour cette période","rejectedAt":"2023-04-10T15:30:00Z"}',
    'campaignId': 1001,
    'campaignStatus': 'active',
    'campaignStartAt': DateTime.now().subtract(Duration(days: 10)).toIso8601String(),
    'campaignEndAt': DateTime.now().add(Duration(days: 20)).toIso8601String(),
    'campaignContact': 'contact@campaign1.org',
    'campaignDetails': 'Campagne de sensibilisation à l\'hygiène',
    'campaignImage': 'https://example.com/campaign1.jpg',
    'centerId': 101,
    'centerName': 'Centre de Santé Communautaire',
    'centerContact': '+221 77 123 4567',
    'centerDetails': 'Centre médical principal de la région',
    'addressId': 201,
    'addressText': '123 Avenue Principale, Dakar',
    'longitude': -17.4676,
    'latitude': 14.7645,
    'region': 'Dakar',
    },
    {
    'id': 'req-002',
    'requestDate': DateTime.now().subtract(Duration(days: 1)).toIso8601String(),
    'acceptanceDate': null,
    'isAccepted': false,
    'phoneNumber': phoneNumber,
    'sharedInfoSelection': '{"status":"rejected","feedback":"Je ne suis pas disponible pour cette période","rejectedAt":"2023-04-10T15:30:00Z"}',
    'campaignId': 1002,
    'campaignStatus': 'active',
    'campaignStartAt': DateTime.now().subtract(Duration(days: 5)).toIso8601String(),
    'campaignEndAt': DateTime.now().add(Duration(days: 25)).toIso8601String(),
    'campaignContact': 'info@campaign2.org',
    'campaignDetails': 'Campagne de vaccination contre le paludisme',
    'campaignImage': 'https://example.com/campaign2.jpg',
    'centerId': 102,
    'centerName': 'Hôpital Régional de Matam',
    'centerContact': '+221 77 987 6543',
    'centerDetails': 'Hôpital spécialisé en maladies infectieuses',
    'addressId': 202,
    'addressText': '456 Rue Nationale, Matam',
    'longitude': -13.2553,
    'latitude': 15.6562,
    'region': 'Matam',
    },
      {
        'id': 'req-003',
        'requestDate': DateTime.now()
            .subtract(Duration(hours: 12))
            .toIso8601String(),
        'acceptanceDate': null,
        'isAccepted': false,
        'phoneNumber': phoneNumber,
        'sharedInfoSelection': '{"status":"rejected","feedback":"Je ne suis pas disponible pour cette période","rejectedAt":"2023-04-10T15:30:00Z"}',
        'campaignId': 1003,
        'campaignStatus': 'active',
        'campaignStartAt': DateTime.now()
            .subtract(Duration(days: 2))
            .toIso8601String(),
        'campaignEndAt': DateTime.now()
            .add(Duration(days: 30))
            .toIso8601String(),
        'campaignContact': 'support@campaign3.org',
        'campaignDetails': 'Campagne de dépistage du diabète',
        'campaignImage': 'https://example.com/campaign3.jpg',
        'centerId': 103,
        'centerName': 'Clinique espoir',
        'centerContact': '+221 77 987 6543',
        'centerDetails': 'Hôpital spécialisé en maladies infectieuses',
        'addressId': 202,
        'addressText': '456 Rue Nationale, Matam',
        'longitude': -13.2553,
        'latitude': 15.6562,
        'region': 'Matam',
      }
    ];
    }


  // Fonction de test pour simuler l'API
  Future<List<EnrichedShareInfoRequest>> getPendingRequestsByPhoneNumber(String phoneNumber) async {
    // Simule un délai de réseau
    await Future.delayed(Duration(milliseconds: 200));

    final responseData = generateFakeRequestsData(phoneNumber);
    return responseData.map((json) => EnrichedShareInfoRequest.fromJson(json)).toList();
  }
 
  // Future<List<EnrichedShareInfoRequest>> getPendingRequestsByPhoneNumber(String phoneNumber) async {
  //   final response = await http.get(
  //     Uri.parse('$baseUrl/share-info-requests/phone/$phoneNumber/pending'),
  //     headers: {'Content-Type': 'application/json'},
  //   );
  //
  //   if (response.statusCode == 200) {
  //     final responseData = json.decode(response.body) as List<dynamic>;
  //     return responseData.map((json) => EnrichedShareInfoRequest.fromJson(json as Map<String, dynamic>)).toList();
  //   } else {
  //     throw Exception('Échec du chargement des demandes en attente');
  //   }
  // }

  Future<EnrichedShareInfoRequest> acceptRequest(String requestId) async {
    final response = await http.put(
      Uri.parse('$baseUrl/share-info-requests/$requestId/accept'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode({
        'selectedInfoTypes': ['informations_personnelles', 'historique_dons', 'groupe_sanguin', 'localisation', 'contact']
      }),
    );

    if (response.statusCode == 200) {
      return EnrichedShareInfoRequest.fromJson(json.decode(response.body) as Map<String, dynamic>);
    } else {
      throw Exception('Échec de l\'acceptation de la demande');
    }
  }

  Future<EnrichedShareInfoRequest> rejectRequest(String requestId, {String? feedback}) async {
    final Uri uri = feedback != null && feedback.isNotEmpty
        ? Uri.parse('$baseUrl/share-info-requests/$requestId/reject?feedback=$feedback')
        : Uri.parse('$baseUrl/share-info-requests/$requestId/reject');

    final response = await http.put(
      uri,
      headers: {'Content-Type': 'application/json'},
    );

    if (response.statusCode == 200) {
      return EnrichedShareInfoRequest.fromJson(json.decode(response.body) as Map<String, dynamic>);
    } else {
      throw Exception('Échec du rejet de la demande');
    }
  }
}