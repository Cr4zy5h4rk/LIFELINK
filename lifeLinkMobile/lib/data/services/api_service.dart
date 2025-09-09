import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/domain/models/chat_response.dart';

class ApiService {

  ///
  final String audioWolofChatbotUrl = 'coming later...';
  /// URL de base de l'API du chatbot
  static String baseUrl = ServicesUrls.chatbotApi;

  // Méthode pour envoyer un message au SangBot API
  static Future<ChatResponse> sendMessage(String message) async {
    try {
      // Assurez-vous que le message est correctement encodé
      final encodedMessage = utf8.encode(message);
      final String decodedMessage = utf8.decode(encodedMessage);

      final response = await http.post(
        Uri.parse('$baseUrl/chat/'),
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
          'Accept': 'application/json; charset=utf-8',
        },
        body: jsonEncode({'message': decodedMessage}),
      );

      if (response.statusCode == 200) {
        // Décodage explicite avec UTF-8
        final String responseBody = utf8.decode(response.bodyBytes);
        if (kDebugMode) {
          print("Réponse brute: $responseBody");
        }

        final jsonResponse = jsonDecode(responseBody);
        return ChatResponse.fromJson(jsonResponse as Map<String, dynamic>);
      } else {
        if (kDebugMode) {
          print("Erreur HTTP: ${response.statusCode}");
          print("Corps de la réponse: ${utf8.decode(response.bodyBytes)}");
        }
        return ChatResponse.error('Échec de la réponse: ${response.statusCode}');
      }
    } catch (e) {
      if (kDebugMode) {
        print("Exception: $e");
      }
      return ChatResponse.error('Erreur API: $e');
    }
  }

  // Méthode pour vérifier si l'API est disponible
  static Future<bool> checkApiStatus() async {
    try {
      final response = await http.get(
        Uri.parse(baseUrl),
        headers: {'Accept': 'application/json; charset=utf-8'},
      );
      return response.statusCode == 200;
    } catch (e) {
      return false;
    }
  }
}