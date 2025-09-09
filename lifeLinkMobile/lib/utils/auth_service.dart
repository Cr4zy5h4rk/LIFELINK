import 'dart:convert';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';

/// Classe pour gérer l'authentification
class AuthService {
  ///instance de FlutterSecureStorage
  static const storage = FlutterSecureStorage();

  /// Vérifier si l'utilisateur est connecté
  Future<bool> isLoggedIn() async {
    final token = await storage.read(key: 'jwt_token');
    return token != null;
  }

  /// Récupérer le token JWT
  Future<String?> getToken() async {
    return storage.read(key: 'jwt_token');
  }

  /// Stocker le token JWT
  Future<void> setToken(String token) async {
    await storage.write(key: 'jwt_token', value: token);
  }

  /// Stocker les informations utilisateur
  Future<void> setUserInfo(Map<String, dynamic> userInfo) async {
    await storage.write(key: 'user_info', value: jsonEncode(userInfo));
  }

  /// Récupérer les informations utilisateur
  Future<Map<String, dynamic>?> getUserInfo() async {
    final userInfoStr = await storage.read(key: 'user_info');
    if (userInfoStr != null) {
      final dynamic decodedJson = jsonDecode(userInfoStr);
      if (decodedJson is Map) {
        return Map<String, dynamic>.from(decodedJson);
      }
    }
    return null;
  }

  /// Déconnexion
  Future<void> logout() async {
    await storage.delete(key: 'jwt_token');
    await storage.delete(key: 'user_info');
    await storage.deleteAll();
  }
}
