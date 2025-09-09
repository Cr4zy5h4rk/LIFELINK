import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/ui/posts/post_detail_screen.dart';
import 'package:lifelink/utils/responsive_helper.dart';

/// post search bar
class FeaturedPostItem extends StatelessWidget{

  /// responsive helper
  final ResponsiveHelper responsive;
  /// post
  final Post post;

  /// constructor
  const FeaturedPostItem({
    required this.post,
    required this.responsive,
    super.key,
  });

  Future<void> _navigateToPostDetail(BuildContext context, Post post) async {
    await Navigator.push(
      context,
      MaterialPageRoute<dynamic>(
        builder: (context) => PostDetailScreen(postId: post.id ?? '1'),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () async => _navigateToPostDetail(context, post),
      child: Container(
        margin: const EdgeInsets.only(bottom: 16),
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(16),
          boxShadow: [
            BoxShadow(
              color: Colors.grey.withValues(alpha: 0.1),
              spreadRadius: 1,
              blurRadius: 6,
              offset: const Offset(0, 3),
            ),
          ],
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Image et titre superposé
            Stack(
              children: [
                ClipRRect(
                  borderRadius: const BorderRadius.only(
                    topLeft: Radius.circular(16),
                    topRight: Radius.circular(16),
                  ),
                  child: Image.network(
                    post.imageUrl,
                    height: 160, // Réduit la hauteur de la bannière
                    width: double.infinity,
                    fit: BoxFit.cover,
                    errorBuilder: (context, error, stackTrace) {
                      return Container(
                        height: 160,
                        color: Colors.grey[300],
                        child: const Center(
                          child: Icon(
                            Icons.image_not_supported,
                            color: Colors.grey,
                            size: 40,
                          ),
                        ),
                      );
                    },
                  ),
                ),
                Positioned(
                  bottom: 0,
                  left: 0,
                  right: 0,
                  child: Container(
                    padding: const EdgeInsets.all(12),
                    decoration: BoxDecoration(
                      gradient: LinearGradient(
                        begin: Alignment.topCenter,
                        end: Alignment.bottomCenter,
                        colors: [
                          Colors.transparent,
                          Colors.black.withValues(alpha: 0.7),
                        ],
                      ),
                    ),
                    child: Text(
                      post.title,
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: responsive.getAdaptiveFontSize(16),
                        fontWeight: FontWeight.bold,
                      ),
                      maxLines: 2,
                      overflow: TextOverflow.ellipsis,
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

}
