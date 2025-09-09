import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:http_interceptor/http_interceptor.dart';
import 'package:lifelink/utils/auth_interceptor.dart';
import 'package:lifelink/utils/auth_service.dart';

/// Classe pour gérer les requêtes HTTP
class ApiClient {
  /// URL de base de l'API
  final String baseUrl;
  final InterceptedClient _client;

  /// Constructeur qui initialise le client avec notre intercepteur
  ApiClient({required this.baseUrl})
      : _client = InterceptedClient.build(
    interceptors: [AuthInterceptor()],
  );

  /// Méthode pour les requêtes GET
  Future<dynamic> get(String endpoint) async {
    final response = await _client.get(Uri.parse('$baseUrl$endpoint'));
    return _processResponse(response);
  }

  /// Méthode pour les requêtes POST
  Future<dynamic> post(String endpoint, {Map<String, dynamic>? body}) async {
    final response = await _client.post(
      Uri.parse('$baseUrl$endpoint'),
      body: body != null ? jsonEncode(body) : null,
    );
    return _processResponse(response);
  }

  /// Méthode pour les requêtes PUT
  Future<dynamic> put(String endpoint, {Map<String, dynamic>? body}) async {
    final response = await _client.put(
      Uri.parse('$baseUrl$endpoint'),
      body: body != null ? jsonEncode(body) : null,
    );
    return _processResponse(response);
  }

  /// Méthode pour les requêtes DELETE
  Future<dynamic> delete(String endpoint) async {
    final response = await _client.delete(Uri.parse('$baseUrl$endpoint'));
    return _processResponse(response);
  }

  /// Méthode pour les requêtes PATCH
  Future<dynamic> patch(String endpoint, {Map<String, dynamic>? body}) async {
    final response = await _client.patch(
      Uri.parse('$baseUrl$endpoint'),
      body: body != null ? jsonEncode(body) : null,
    );
    return _processResponse(response);
  }

  /// Traitement de la réponse
  dynamic _processResponse(http.Response response) {
    if (response.statusCode >= 200 && response.statusCode < 300) {
      // Réponse réussie
      if (response.body.isEmpty) {
        return null;
      }

      try {
        return jsonDecode(response.body);
      } catch (e) {
        // Si la réponse n'est pas du JSON valide, retourner le texte brut
        return response.body;
      }
    } else if (response.statusCode == 401) {
      // Non autorisé - le token est peut-être expiré
      throw UnauthorizedException();
    } else if (response.statusCode == 404) {
      // Ressource non trouvée
      throw NotFoundException(body: response.body);
    } else if (response.statusCode >= 400 && response.statusCode < 500) {
      // Erreur client
      throw ClientException(
          message: 'Erreur client: ${response.statusCode}',
          statusCode: response.statusCode,
          body: response.body,
      );
    } else if (response.statusCode >= 500) {
      // Erreur serveur
      throw ServerException(
          message: 'Erreur serveur: ${response.statusCode}',
          statusCode: response.statusCode,
          body: response.body,
      );
    } else {
      // Autres erreurs
      throw ApiException(
          message: 'Erreur API: ${response.statusCode}',
          statusCode: response.statusCode,
          body: response.body,
      );
    }
  }

  /// Téléchargement de fichier
  Future<http.StreamedResponse> uploadFile(
      String endpoint,
      String filePath,
      String fieldName,
      {Map<String, String>? fields,}
      ) async {
    final request = http.MultipartRequest('POST',
        Uri.parse('$baseUrl$endpoint'),);

    // Ajouter le fichier à la requête
    request.files.add(await http.MultipartFile.fromPath(fieldName, filePath));

    // Ajouter des champs supplémentaires si nécessaire
    if (fields != null) {
      request.fields.addAll(fields);
    }

    // Ajouter les en-têtes d'authentification
    final token = await AuthService().getToken();
    if (token != null) {
      request.headers['Authorization'] = 'Bearer $token';
    }

    // Envoyer la requête
    return _client.send(request);
  }

  /// Téléchargement de fichier
  Future<http.Response> downloadFile(String endpoint) async {
    return _client.get(
      Uri.parse('$baseUrl$endpoint'),
      headers: {'Accept': 'application/octet-stream'},
    );
  }

  /// Obtenir directement le client HTTP avec intercepteur si nécessaire
  InterceptedClient get client => _client;
}

/// Classes d'exception
class ApiException implements Exception {
  /// Message d'erreur
  final String message;
  /// Code d'erreur
  final int statusCode;
  /// Corps de la réponse (peut être null)
  final String? body;

  /// Constructeur
  ApiException({
    required this.message,
    required this.statusCode,
    this.body,
  });

  @override
  String toString() => message;
}

/// Exceptions spécifiques
class UnauthorizedException extends ApiException {
  /// Constructeur
  UnauthorizedException()
      : super(
      message: 'Non autorisé: veuillez vous reconnecter',
      statusCode: 401,
  );
}

/// Exceptions spécifiques
class NotFoundException extends ApiException {
  ///
  NotFoundException({super.body})
      : super(
      message: 'Ressource non trouvée',
      statusCode: 404,
  );
}

/// Exceptions spécifiques
class ClientException extends ApiException {
  /// Constructeur
  ClientException({required super.message,
    required super.statusCode, super.body,
  });
}

/// Exceptions spécifiques
class ServerException extends ApiException {
  /// Constructeur
  ServerException({required super.message,
    required super.statusCode, super.body,
  });

}
