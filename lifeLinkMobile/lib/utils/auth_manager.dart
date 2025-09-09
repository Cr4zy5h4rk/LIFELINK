import 'package:flutter/material.dart';
import 'package:lifelink/utils/auth_events.dart';
import 'package:lifelink/utils/auth_service.dart';

/// Classe pour gérer l'authentification
class AuthManager {
  static final AuthManager _instance = AuthManager._internal();
  final AuthService _authService = AuthService();

  /// Constructeur factory pour le singleton
  factory AuthManager() {
    return _instance;
  }

  AuthManager._internal();

  /// Méthode pour initialiser l'écouteur d'événements
  void initialize(GlobalKey<NavigatorState> navigatorKey) {
    // Écouter les événements d'authentification
    authEventController.stream.listen((event) async {
      if (event.type == AuthEventType.tokenExpired) {
        await _handleTokenExpired(navigatorKey);
      } else if (event.type == AuthEventType.loggedOut) {
        await _navigateToLogin(navigatorKey);
      }
    });
  }

  // Gérer l'expiration du token
  Future<void> _handleTokenExpired(GlobalKey<NavigatorState> navigatorKey)
  async {
    // Déconnecter l'utilisateur
    await _authService.logout();

    // Naviguer vers l'écran de connexion
    await _navigateToLogin(navigatorKey);
  }

  // Naviguer vers l'écran de connexion
  Future<void> _navigateToLogin(GlobalKey<NavigatorState> navigatorKey) async {
    final context = navigatorKey.currentContext;
    if (context != null) {
      // Afficher un message
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('Votre session a expiré. Veuillez vous reconnecter.'),
          duration: Duration(seconds: 3),
        ),
      );

      await Future<void>.delayed(const Duration(milliseconds: 300));

      await Navigator.of(context).pushNamedAndRemoveUntil('/login', (route) => false);

    }
  }
}
