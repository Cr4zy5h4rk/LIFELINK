import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/ui/posts/post_detail_screen.dart';
import 'package:lifelink/utils/app_colors.dart';


/// Recent post widget
class PostsWidget extends StatelessWidget{
  /// Recent post data
  final List<Post> posts;
  /// Posts title(type)
  final String title;
  ///constructor
  const PostsWidget({required this.title,required this.posts,super.key});

  Widget _buildPostItem(Post post,BuildContext context) {
    return GestureDetector(
      onTap: () async {
        await Navigator.push(
            context,
            MaterialPageRoute<void>(
                builder: (context) =>
                    PostDetailScreen(
                  postId: post.id!,
                    ),
            ),
        );
      },
      child: Container(
        width: 220,
        margin: const EdgeInsets.only(right: 12),
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(12),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withValues(alpha: 0.05),
              offset: const Offset(0, 2),
              blurRadius: 4,
            ),
          ],
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            ClipRRect(
              borderRadius: const BorderRadius.only(
                topLeft: Radius.circular(12),
                topRight: Radius.circular(12),
              ),
              child: Image.network(
                post.imageUrl,
                height: 100,
                width: double.infinity,
                fit: BoxFit.cover,
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(8),
              child: Text(
                post.title,
                textAlign: TextAlign.justify,
                style: const TextStyle(
                  fontSize: 12,
                  fontWeight: FontWeight.w500,
                ),
                maxLines: 3,
                overflow: TextOverflow.ellipsis,
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        // En-tête de section
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            // Titre avec indicateur vertical
             Row(
              children: [
                const SizedBox(width: 8),
                Text(
                  title,
                  style: const TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Color(0xFF2D2D2D),
                  ),
                ),
              ],
            ),
            // Bouton "Voir tout"
            TextButton(
              onPressed: () {
                // Navigation vers la page d'articles
                // Navigator.push(context, MaterialPageRoute(builder: (context) => PostsScreen()));
              },
              child: const Text(
                'Tout voir',
                style: TextStyle(
                  color: AppColors.customRed,
                  fontWeight: FontWeight.w600,
                  fontSize: 14,
                ),
              ),
            ),
          ],
        ),
        const SizedBox(height: 16),
        // Liste des articles récents
        SizedBox(
          height: 185,
          child: ListView.builder(
            scrollDirection: Axis.horizontal,
            itemCount: posts.length,
            itemBuilder: (context, index) {
              return _buildPostItem(posts[index],context);
            },
          ),
        ),
      ],
    );
  }
}
