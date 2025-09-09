import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/ui/posts/post_detail_screen.dart';
import 'package:lifelink/utils/responsive_helper.dart';


/// post item
class PostItem extends StatelessWidget{

  /// responsive helper
  final ResponsiveHelper responsive;
  /// post
  final Post post;

  /// constructor
  const PostItem({
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
        child: Row(
          children: [
            ClipRRect(
              borderRadius: const BorderRadius.only(
                topLeft: Radius.circular(16),
                bottomLeft: Radius.circular(16),
              ),
              child: Image.network(
                post.imageUrl,
                width: 100,
                height: 100,
                fit: BoxFit.cover,
                errorBuilder: (context, error, stackTrace) {
                  return Container(
                    width: 100,
                    height: 100,
                    color: Colors.grey[300],
                    child: const Icon(
                      Icons.image_not_supported,
                      color: Colors.grey,
                    ),
                  );
                },
              ),
            ),
            Expanded(
              child: Padding(
                padding: const EdgeInsets.all(12),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      post.title,
                      style: TextStyle(
                        fontSize: responsive.getAdaptiveFontSize(16),
                        fontWeight: FontWeight.bold,
                      ),
                      maxLines: 2,
                      overflow: TextOverflow.ellipsis,
                    ),
                    const SizedBox(height: 8),
                    Row(
                      children: [
                        Icon(
                          Icons.access_time,
                          size: 16,
                          color: Colors.grey[600],
                        ),
                        const SizedBox(width: 4),
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
              ),
            ),
          ],
        ),
      ),
    );
  }

}
