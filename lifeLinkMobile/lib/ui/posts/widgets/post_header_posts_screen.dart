import 'package:flutter/material.dart';
import 'package:lifelink/utils/responsive_helper.dart';

///
class PostsScreenHeader extends StatelessWidget{

  /// responsive helper
  final ResponsiveHelper responsive;

  /// constructor
  const PostsScreenHeader({
    required this.responsive,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: responsive.getAdaptivePadding(horizontal: 16, vertical: 8),
      child: Row(
        children: [
          GestureDetector(
            onTap: () => Navigator.pop(context),
            child: Container(
              padding: const EdgeInsets.all(8),
              decoration: BoxDecoration(
                color: Colors.grey.withValues(alpha: 0.1),
                shape: BoxShape.circle,
              ),
              child: const Icon(
                Icons.arrow_back,
                color: Colors.black87,
                size: 18,
              ),
            ),
          ),
          const SizedBox(width: 16),
          Text(
            'Posts',
            style: TextStyle(
              fontSize: responsive.getAdaptiveFontSize(22),
              fontWeight: FontWeight.bold,
            ),
          ),
        ],
      ),
    );
  }

}
