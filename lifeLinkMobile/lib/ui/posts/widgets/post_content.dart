import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/responsive_helper.dart';

/// Post content
class PostContent extends StatelessWidget{
  /// responsive helper
  final ResponsiveHelper responsive;
  /// post
  final Post post;

  /// constructor
  const PostContent({
    required this.responsive,
    required this.post,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: responsive.getAdaptivePadding(horizontal: 20, vertical: 16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          // Author info
          Row(
            children: [
              CircleAvatar(
                radius: 20,
                backgroundImage: post.authorImageUrl != null
                    ? NetworkImage(post.authorImageUrl!)
                    : null,
                child: post.authorImageUrl == null
                    ? Text(
                    post.author.isNotEmpty ?
                    post.author[0].toUpperCase() :
                    '?',
                )
                    : null,
              ),
              const SizedBox(width: 12),
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    post.author,
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: responsive.getAdaptiveFontSize(16),
                    ),
                  ),
                  Text(
                    post.timeAgo,
                    style: TextStyle(
                      color: Colors.grey[600],
                      fontSize: responsive.getAdaptiveFontSize(12),
                    ),
                  ),
                ],
              ),
            ],
          ),

          const SizedBox(height: 12),

          // Audio button if available
          if (post.hasAudio)
            Container(
              margin: const EdgeInsets.only(bottom: 16),
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
              decoration: BoxDecoration(
                color: Colors.grey[100],
                borderRadius: BorderRadius.circular(10),
              ),
              child: Row(
                children: [
                  const Icon(
                    Icons.volume_up,
                    color: AppColors.customBlue,
                  ),
                  const SizedBox(width: 12),
                  Text(
                    post.audioLanguage ?? 'Audio',
                    style: TextStyle(
                      color: AppColors.customBlue,
                      fontWeight: FontWeight.w500,
                      fontSize: responsive.getAdaptiveFontSize(14),
                    ),
                  ),
                ],
              ),
            ),

          // Main content
          Text(
            post.content,
            style: TextStyle(
              fontSize: responsive.getAdaptiveFontSize(16),
              height: 1.5,
            ),
          ),

          // Additional space at the bottom
          SizedBox(height: responsive.getAdaptiveHeight(30)),
        ],
      ),
    );
  }

}
