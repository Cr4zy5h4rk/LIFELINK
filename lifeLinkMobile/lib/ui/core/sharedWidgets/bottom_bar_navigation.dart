import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:flutter/material.dart';

/// Custom bottom navigation bar widget
class CustomBottomNavBar extends StatelessWidget {
  ///current navigation index
  final int currentIndex;
  ///callback when an item is tapped
  final void Function(int) onTap;

  ///constructor
  const CustomBottomNavBar({
    required this.currentIndex, required this.onTap, super.key,
  });

  @override
  Widget build(BuildContext context) {
    return CurvedNavigationBar(
      backgroundColor: const Color(0xFFFFF8F3),
      color: const Color(0xFFFF5C3B),
      index: currentIndex,
      items: const <Widget>[
        Icon(
          Icons.home,
          size: 30,
          color: Colors.white,
        ),
        Icon(
          Icons.search,
          size: 30,
          color: Colors.white,
        ),
        Icon(
          Icons.apps,
          size: 30,
          color: Colors.white,
        ),
      ],
      onTap: onTap,
    );
  }
}
