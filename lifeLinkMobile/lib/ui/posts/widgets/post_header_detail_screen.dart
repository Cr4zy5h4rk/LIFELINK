import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/ui/posts/view_model/post_view_model.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/responsive_helper.dart';

/// Post header
class PostHeader extends StatelessWidget {

  /// responsive helper
  final ResponsiveHelper responsive;
  /// post
  final Post post;
  /// View model
  final PostViewModel viewModel;

  /// constructor
  const PostHeader({
    required this.responsive,
    required this.viewModel,
    required this.post,
    super.key,
  });


  @override
  Widget build(BuildContext context) {

    return Stack(
      children: [
        // Post Image with rounded corners at the bottom
        ClipRRect(
          borderRadius: const BorderRadius.only(
            bottomLeft: Radius.circular(20),
            bottomRight: Radius.circular(20),
          ),
          child: SizedBox(
            width: double.infinity,
            height: responsive.getAdaptiveHeight(300),
            child: Hero(
              tag: 'post-image-${post.id ?? post.title}',
              child: Image.network(
                post.imageUrl,
                fit: BoxFit.cover,
                errorBuilder: (context, error, stackTrace) {
                  return Container(
                    color: Colors.grey[300],
                    child: Center(
                      child: Icon(
                        Icons.error_outline,
                        color: Colors.grey[700],
                        size: 50,
                      ),
                    ),
                  );
                },
              ),
            ),
          ),
        ),

        // Back button
        Positioned(
          top: responsive.getAdaptiveHeight(16),
          left: responsive.getAdaptiveHeight(16),
          child: GestureDetector(
            onTap: () => Navigator.pop(context),
            child: Container(
              padding: const EdgeInsets.all(8),
              decoration: BoxDecoration(
                color: Colors.white.withValues(alpha: 0.7),
                borderRadius: BorderRadius.circular(20),
              ),
              child: const Icon(
                Icons.arrow_back_ios_rounded,
                color: Colors.black87,
                size: 20,
              ),
            ),
          ),
        ),

        // Title overlay
        Positioned(
          bottom: 0,
          left: 0,
          right: 0,
          child: Container(
            padding: responsive.getAdaptivePadding(
              horizontal: 20,
              vertical: 15,
            ),
            decoration: BoxDecoration(
              gradient: LinearGradient(
                begin: Alignment.bottomCenter,
                end: Alignment.topCenter,
                colors: [
                  Colors.black.withValues(alpha: 0.7),
                  Colors.transparent,
                ],
              ),
              borderRadius: const BorderRadius.only(
                bottomLeft: Radius.circular(20),
                bottomRight: Radius.circular(20),
              ),
            ),
            child: Text(
              post.title,
              style: TextStyle(
                color: Colors.white,
                fontSize: responsive.getAdaptiveFontSize(22),
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
        ),

        // Heart/favorite button
        Positioned(
          right: 20,
          bottom: 20,
          child: GestureDetector(
            onTap: () async {
              await viewModel.toggleFavorite();
            },
            child: Container(
              width: 50,
              height: 50,
              decoration: const BoxDecoration(
                color: AppColors.customRed,
                shape: BoxShape.circle,
              ),
              child: Icon(
                post.isFavorite ? Icons.favorite : Icons.favorite_border,
                color: Colors.white,
                size: 28,
              ),
            ),
          ),
        ),
      ],
    );
  }

}
