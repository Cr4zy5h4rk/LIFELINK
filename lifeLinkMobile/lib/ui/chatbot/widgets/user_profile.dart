import 'package:flutter/material.dart';

///
class UserProfileWidget extends StatelessWidget {
  final String name;
  final String imageUrl;

  ///
  const UserProfileWidget({
    required this.name, required this.imageUrl, super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        CircleAvatar(
          radius: 16,
          backgroundImage: AssetImage(imageUrl),
        ),
        const SizedBox(width: 8),
        Text(
          name,
          style: const TextStyle(
            color: Colors.black,
            fontWeight: FontWeight.bold,
            fontSize: 18,
          ),
        ),
      ],
    );
  }
}
