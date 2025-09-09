import 'package:flutter/material.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/login/login_screen.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/auth_manager.dart';
import 'package:lifelink/utils/auth_service.dart';
import 'package:provider/provider.dart';

void main() {

  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => NavigationProvider()),
      ],
      child: const LifeLink(),
    ),
  );
}

/// Main widget
class LifeLink extends StatefulWidget {
  /// constant constructor
  const LifeLink({super.key});

  @override
  State<LifeLink> createState() => _LifeLinkState();
}

class _LifeLinkState extends State<LifeLink> {
  final GlobalKey<NavigatorState> _navigatorKey = GlobalKey<NavigatorState>();
  final AuthService _authService = AuthService();

  @override
  void initState() {
    super.initState();
    // Initialiser le gestionnaire d'authentification
    AuthManager().initialize(_navigatorKey);
  }

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: _navigatorKey,
      title: 'LifeLink',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.red,
        scaffoldBackgroundColor: const Color(0xFFFFF8F3),
        appBarTheme: const AppBarTheme(
          backgroundColor: Color(0xFFFFF8F3),
          elevation: 0,
          iconTheme: IconThemeData(color: Colors.black),
          titleTextStyle: TextStyle(
            color: Colors.black,
            fontSize: 18,
            fontWeight: FontWeight.bold,
          ),
        ),
        fontFamily: 'Roboto',
      ),
      home: FutureBuilder<bool>(
        future: _authService.isLoggedIn(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Scaffold(
              body: Center(
                  child: CircularProgressIndicator(
                    color: AppColors.customRed,
                  ),

              ),
            );
          } else {
            if (snapshot.data == true) {
              return const HomeScreen();
            } else {
              return const LoginScreen();
            }
          }
        },
      ),
      routes: {
        '/login': (context) => const LoginScreen(),
        '/home': (context) => const HomeScreen(),
      },
    );
  }
}
