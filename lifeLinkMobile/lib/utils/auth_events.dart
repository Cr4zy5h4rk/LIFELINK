import 'dart:async';

/// Types d'événements d'authentification
enum AuthEventType {
  ///token expired
  tokenExpired,
  ///logged out
  loggedOut
}

/// Classe pour les événements d'authentification
class AuthEvent {

  /// Type d'événement
  final AuthEventType type;

  /// Constructeur
  AuthEvent(this.type);
}

/// StreamController global pour les événements d'authentification
final StreamController<AuthEvent> authEventController =
StreamController<AuthEvent>.broadcast();
