import 'package:flutter/material.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/responsive_helper.dart';

/// post search bar
class PostsSearchBar extends StatelessWidget{

  /// responsive helper
  final ResponsiveHelper responsive;
  /// search controller
  final TextEditingController searchController;

  /// constructor
  const PostsSearchBar({
    required this.searchController,
    required this.responsive,
    super.key,
  });

  void _searchPosts(String query) {
    // Logique de recherche
    // Pour l'instant, juste un message de débogage
    print('Recherche: $query');
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: responsive.getAdaptivePadding(horizontal: 16, vertical: 8),
      child: Row(
        children: [
          Expanded(
            child: Container(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              decoration: BoxDecoration(
                color: Colors.grey.withValues(alpha: 0.1),
                borderRadius: BorderRadius.circular(24),
              ),
              child: TextField(
                controller: searchController,
                decoration: const InputDecoration(
                  hintText: 'Search ...',
                  border: InputBorder.none,
                  icon: Icon(Icons.search, color: Colors.grey),
                ),
                onSubmitted: _searchPosts,
              ),
            ),
          ),
          const SizedBox(width: 8),
          Container(
            width: 48,
            height: 48,
            decoration: BoxDecoration(
              color: AppColors.customRed,
              borderRadius: BorderRadius.circular(24),
            ),
            child: IconButton(
              icon: const Icon(Icons.search, color: Colors.white),
              onPressed: () => _searchPosts(searchController.text),
            ),
          ),
        ],
      ),
    );
  }

}
