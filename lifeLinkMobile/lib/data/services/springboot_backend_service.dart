
import 'package:lifelink/utils/api_client.dart';

/// Blood donation service
class SpringbootBackendService {
  /// Base URL of the API
  final String baseUrl;
  final ApiClient _apiClient;

  /// Constructor
  SpringbootBackendService({required this.baseUrl})
      : _apiClient = ApiClient(baseUrl: baseUrl);

  /// Generic method to make a GET request
  Future<dynamic> get(String endpoint) async {
    try {
      return await _apiClient.get(endpoint);
    } on UnauthorizedException {
      rethrow; // L'AuthManager s'occupera de la redirection
    } catch (e) {
      throw Exception('Erreur lors de la requête GET: $e');
    }
  }

  /// Generic method to make a POST request
  Future<dynamic> post(String endpoint, {required Map<String, dynamic> data})
  async {
    try {
      return await _apiClient.post(endpoint, body: data);
    } on UnauthorizedException {
      rethrow; // L'AuthManager s'occupera de la redirection
    } catch (e) {
      throw Exception('Erreur lors de la requête POST: $e');
    }
  }

  /// Generic method to make a PUT request
  Future<dynamic> put(String endpoint, {required Map<String, dynamic> data})
  async {
    try {
      return await _apiClient.put(endpoint, body: data);
    } on UnauthorizedException {
      rethrow; // L'AuthManager s'occupera de la redirection
    } catch (e) {
      throw Exception('Erreur lors de la requête PUT: $e');
    }
  }

  /// Generic method to make a DELETE request
  Future<dynamic> delete(String endpoint) async {
    try {
      return await _apiClient.delete(endpoint);
    } on UnauthorizedException {
      rethrow; // L'AuthManager s'occupera de la redirection
    } catch (e) {
      throw Exception('Erreur lors de la requête DELETE: $e');
    }
  }

  /// Generic method to make a PATCH request
  Future<dynamic> patch(String endpoint, {required Map<String, dynamic> data})
  async {
    try {
      return await _apiClient.patch(endpoint, body: data);
    } on UnauthorizedException {
      rethrow; // L'AuthManager s'occupera de la redirection
    } catch (e) {
      throw Exception('Erreur lors de la requête PATCH: $e');
    }
  }

  /// Method to upload a file
  Future<dynamic> uploadFile(
      String endpoint,
      String filePath,
      String fieldName,
      {Map<String, String>? fields,}
      ) async {
    try {
      final response = await _apiClient.uploadFile(
          endpoint, filePath, fieldName, fields: fields,);
      if (response.statusCode >= 200 && response.statusCode < 300) {
        return true;
      } else {
        throw Exception("Échec de l'upload avec le statut: "
            '${response.statusCode}');
      }
    } on UnauthorizedException {
      rethrow;
    } catch (e) {
      throw Exception("Erreur lors de l'upload: $e");
    }
  }
}
