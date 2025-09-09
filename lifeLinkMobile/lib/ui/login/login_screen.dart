import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/utils/auth_service.dart';
import 'package:webview_flutter/webview_flutter.dart';

/// Classe pour la page de connexion
class LoginScreen extends StatefulWidget {
  /// Constructeur
  const LoginScreen({super.key});

  @override
  LoginScreenState createState() => LoginScreenState();
}

/// Etat de la page de connexion
class LoginScreenState extends State<LoginScreen> {
  late WebViewController _controller;
  final AuthService _authService = AuthService();

  /// Etat de chargement
  bool isLoading = true;

  /// URL de la page intermédiaire (à héberger sur votre serveur Spring Boot)
  final String bridgePageUrl = '${ServicesUrls.springBootUrl}/api/delegate/login-bridge';

  /// URL du backend Spring Boot
  final String backendUrl = ServicesUrls.springBootUrl;

  @override
  void initState() {
    super.initState();
    // Initialiser le contrôleur WebView
    _controller = WebViewController()
      ..setJavaScriptMode(JavaScriptMode.unrestricted)
      ..setNavigationDelegate(
        NavigationDelegate(
          onPageStarted: (url) {
            setState(() {
              isLoading = true;
            });
          },
          onPageFinished: (url) {
            setState(() {
              isLoading = false;
            });
            // Vérifier si c'est une URL de callback
            if (url.contains('/callback') && url.contains('code=')) {
              final uri = Uri.parse(url);
              final code = uri.queryParameters['code'];
              if (code != null) {
                _handleAuthCallback(url);
              }
            }
          },
          onNavigationRequest: (request) {
            // Si c'est une URL de callback, l'intercepter
            if (request.url.contains('/callback') && request.url.contains('code=')) {
              _handleAuthCallback(request.url);
              return NavigationDecision.prevent;
            }

            // Laisser passer les autres navigations
            return NavigationDecision.navigate;
          },
          onWebResourceError: (error) {
            print('Erreur WebView: ${error.description}');
          },
        ),
      )
      ..addJavaScriptChannel(
        'FlutterApp',
        onMessageReceived: (message) {
          try {
            // Essayer de parser le message comme JSON
            final dynamic jsonData = jsonDecode(message.message);
            if (jsonData is Map<String, dynamic>) {
              final data = jsonData;

              if (data['type'] == 'callback' && data['code'] != null) {
                // Traiter le code d'autorisation
                final code = data['code'];
                // Construire une URL de callback fictive
                final callbackUrl = '${ServicesUrls.loginCallBack}?code=$code';
                _handleAuthCallback(callbackUrl);
              }
            }


          } catch (e) {
            print('Erreur de parsing du message: $e');
          }
        },
      )
      ..loadRequest(Uri.parse(bridgePageUrl));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFFFF8F3),
      appBar: AppBar(
        title: Row(
          children: [
            Image.network(
              'https://i.imgur.com/q7oel6B.png',
              height: 32,
            ),
            const SizedBox(width: 8),
            const Text('LifeLink'),
          ],
        ),
        backgroundColor: Colors.white,
        elevation: 0,
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () async {
              setState(() {
                isLoading = true;
              });
              await _controller.reload();
            },
          ),
        ],
      ),
      body: Stack(
        children: [
          WebViewWidget(controller: _controller),
          if (isLoading)
            ColoredBox(
              color: Colors.white.withValues(alpha: 0.8),
              child: const Center(
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    CircularProgressIndicator(
                      valueColor: AlwaysStoppedAnimation<Color>(
                          Color(0xFFFF4D4D),
                      ),
                    ),
                    SizedBox(height: 16),
                    Text(
                      'Chargement en cours...',
                      style: TextStyle(
                        fontSize: 16,
                        color: Color(0xFF666666),
                      ),
                    ),
                  ],
                ),
              ),
            ),
        ],
      ),
    );
  }

  Future<void> _handleAuthCallback(String url) async {
    final uri = Uri.parse(url);
    final code = uri.queryParameters['code'];

    if (code != null) {
      try {
        final response = await http.get(
          Uri.parse('$backendUrl/api/delegate/fetchUserInfo?code=$code'),
        );

        if (response.statusCode == 200) {
          final dynamic jsonData = jsonDecode(response.body);
          if (jsonData is Map) {
            final data = Map<String, dynamic>.from(jsonData);

            if (data.containsKey('token') && data['token'] != null) {
              // Convertir explicitement en String avant de stocker le token JWT
              final token = data['token'].toString();
              await _authService.setToken(token);

              if (data.containsKey('user') && data['user'] != null) {
                final dynamic userData = data['user'];
                if (userData is Map) {
                  await _authService.setUserInfo(
                    Map<String, dynamic>.from(userData),
                  );
                }
              }

              // Rediriger vers l'écran principal
              await Navigator.of(context).pushReplacementNamed('/home');
            }
          }
        } else {
          // Gérer l'erreur
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text("Erreur d'authentification: "
                "'${response.statusCode}'"),),
          );
        }
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text('Erreur de connexion: $e')),
        );
      }
    }
  }
}
