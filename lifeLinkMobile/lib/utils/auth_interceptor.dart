import 'package:http_interceptor/http_interceptor.dart';
import 'package:lifelink/utils/auth_events.dart';
import 'package:lifelink/utils/auth_service.dart';


/// Classe pour gérer les intercepteurs d'authentification
class AuthInterceptor implements InterceptorContract {
  final AuthService _authService = AuthService();

  @override
  Future<BaseRequest> interceptRequest({required BaseRequest request}) async {
    try {
      final token = await _authService.getToken();

      request.headers['Content-Type'] = 'application/json';

      if (token != null) {
        request.headers['Authorization'] = 'Bearer $token';
      }
    } catch (e) {
      print("Erreur dans l'intercepteur: $e");
    }

    return request;
  }

  @override
  Future<BaseResponse> interceptResponse({required BaseResponse response})
  async {
    if (response.statusCode == 401) {
      // Émettre un événement pour signaler que le token a expiré
      authEventController.add(AuthEvent(AuthEventType.tokenExpired));
    }

    return response;
  }

  @override
  Future<bool> shouldInterceptRequest() async {
    // Intercepter toutes les requêtes
    return true;
  }

  @override
  Future<bool> shouldInterceptResponse() async {
    // Intercepter toutes les réponses
    return true;
  }
}
