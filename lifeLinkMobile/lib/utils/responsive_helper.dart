// lib/utils/responsive_helper.dart

import 'package:flutter/material.dart';

/// Responsive helper class to handle screen size and layout
class ResponsiveHelper {
  /// Context of the widget
  final BuildContext context;

  ///Breakpoints mobile
  static const double mobileBreakpoint = 600;
  ///Breakpoints tablet
  static const double tabletBreakpoint = 900;
  ///Breakpoints desktop
  static const double desktopBreakpoint = 1200;

  /// Constructor
  ResponsiveHelper(this.context);

  /// Get current screen width
  double get width => MediaQuery.of(context).size.width;

  /// Get current screen height
  double get height => MediaQuery.of(context).size.height;

  /// Check if current device is mobile
  bool isMobile() => width < mobileBreakpoint;

  /// Check if current device is tablet
  bool isTablet() => width >= mobileBreakpoint && width < desktopBreakpoint;

  /// Check if current device is desktop
  bool isDesktop() => width >= desktopBreakpoint;

  /// Get adaptive font size
  double getAdaptiveFontSize(double size) {
    if (isDesktop()) {
      return size * 1.2;
    } else if (isTablet()) {
      return size * 1.1;
    } else {
      return size;
    }
  }

  /// Get adaptive padding
  EdgeInsets getAdaptivePadding({
    double? horizontal,
    double? vertical,
  }) {
    var horizontalPadding = horizontal ?? 0;
    var verticalPadding = vertical ?? 0;

    if (isDesktop()) {
      horizontalPadding *= 1.5;
      verticalPadding *= 1.5;
    } else if (isTablet()) {
      horizontalPadding *= 1.2;
      verticalPadding *= 1.2;
    }

    return EdgeInsets.symmetric(
      horizontal: horizontalPadding,
      vertical: verticalPadding,
    );
  }

  /// Get adaptive height
  double getAdaptiveHeight(double height) {
    if (isDesktop()) {
      return height * 1.3;
    } else if (isTablet()) {
      return height * 1.15;
    } else {
      return height;
    }
  }

  /// Get grid column count based on screen size
  int getGridColumnCount({
    required int mobile,
    required int tablet,
    required int desktop,
  }) {
    if (isDesktop()) {
      return desktop;
    } else if (isTablet()) {
      return tablet;
    } else {
      return mobile;
    }
  }
}
