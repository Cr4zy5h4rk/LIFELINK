import 'package:flutter/material.dart';


/// Colors variables
class AppColors {
  /// custom red color
  static const Color customRed = Color(0xFFFF5C3B);
  /// custom red blue
  static const Color customBlue= Color(0xFF3B82FF);
  /// custom light red color
  static const Color customLightRed = Color(0xFFFF8A6B);

  ///
  static const orange = Color (0xFFFF4747);
  ///
  static const orangeLight = Color.fromARGB(100,255, 89, 89);
  ///
  static const white = Color(0xFFFFFFFF);
  static const black = Color(0x00000000);
  ///
  static const whiteTransparent = Color(
    0x4DFFFFFF,
  ); // Figma rgba(255, 255, 255, 0.3)
  ///
  static const blackTransparent = Color(0x4D000000);

  ///
  static const lightColorScheme = ColorScheme(
    brightness: Brightness.light,
    primary: AppColors.orange,
    onPrimary: AppColors.white,
    secondary: AppColors.orangeLight,
    onSecondary: AppColors.white,
    surface: Colors.white,
    onSurface: AppColors.orange,
    error: Colors.red,
    onError: AppColors.black,
  );
}
