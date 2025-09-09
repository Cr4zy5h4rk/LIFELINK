// lib/providers/navigation_provider.dart
import 'package:flutter/material.dart';

/// Navigation provider for the bottom navigation bar
class NavigationProvider extends ChangeNotifier {
  int _currentIndex = 0;

  /// Getter for the current index
  int get currentIndex => _currentIndex;

  /// Setter for the current index
  void setIndex(int index) {
    _currentIndex = index;
    notifyListeners();
  }
}
