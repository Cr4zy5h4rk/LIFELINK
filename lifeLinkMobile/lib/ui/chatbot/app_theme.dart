import 'package:flutter/material.dart';

///
class AppTheme {

  late final String prefix;

  /// Couleurs de l'application
  static const Color primaryColor = Color(0xFFFF5A5F);
  static const Color secondaryColor = Color(0xFFFFC1C1);
  static const Color backgroundColor = Colors.white;
  static const Color textColor = Colors.black87;
  static const Color lightGrey = Color(0xFFF2F2F2);

  /// Styles de texte
  static const TextStyle titleStyle = TextStyle(
    fontSize: 18,
    fontWeight: FontWeight.bold,
    color: textColor,
  );

  static const TextStyle userMessageStyle = TextStyle(
    fontSize: 16,
    color: Colors.white,
  );

  static const TextStyle botMessageStyle = TextStyle(
    fontSize: 16,
    color: textColor,
  );

  static const TextStyle inputTextStyle = TextStyle(
    fontSize: 16,
    color: textColor,
  );

  /// Styles de bouton
  static final ButtonStyle primaryButtonStyle = ElevatedButton.styleFrom(
    backgroundColor: primaryColor,
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(20),
    ),
    elevation: 0,
  );

  /// Décoration d'entrée
  static InputDecoration chatInputDecoration = InputDecoration(
    hintText: 'Message...',
    border: OutlineInputBorder(
      borderRadius: BorderRadius.circular(30.0),
      borderSide: BorderSide.none,
    ),
    filled: true,
    fillColor: lightGrey,
    contentPadding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
  );

  /// Décorations des bulles de message
  static BoxDecoration userMessageDecoration = BoxDecoration(
    color: primaryColor,
    borderRadius: BorderRadius.circular(20.0),
  );

  static BoxDecoration botMessageDecoration = BoxDecoration(
    color: secondaryColor,
    borderRadius: BorderRadius.circular(20.0),
  );

  /// Obtenir les données de thème complètes
  static ThemeData getTheme() {
    return ThemeData(
      primaryColor: primaryColor,
      scaffoldBackgroundColor: backgroundColor,
      appBarTheme: const AppBarTheme(
        backgroundColor: backgroundColor,
        elevation: 1,
        titleTextStyle: titleStyle,
        iconTheme: IconThemeData(color: textColor),
      ),
      fontFamily: 'Roboto',
      textTheme: const TextTheme(
        titleLarge: titleStyle,
        bodyLarge: botMessageStyle,
      ),
    );
  }
}
