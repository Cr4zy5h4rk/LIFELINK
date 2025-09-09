import 'package:flutter/material.dart';
import 'package:lifelink/ui/notification/notification_screen.dart';
import 'package:lifelink/utils/app_colors.dart';


/// CustomAppBar
class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  ///User name
  final String userName;
  ///image path
  final String userImagePath;
  /// blood type
  final String bloodtype;

  ///Constructor
  const CustomAppBar({
    required this.userName,
    required this.bloodtype,
    required this.userImagePath,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return DecoratedBox(
      decoration: BoxDecoration(
        color: const Color(0xFFFFF8F3),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withValues(alpha: 0.05),
            offset: const Offset(0, 2),
            blurRadius: 4,
          ),
        ],
      ),
      child: SafeArea(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
          child: Row(
            children: [
              // Avatar avec effet d'élévation
              DecoratedBox(
                decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  boxShadow: [
                    BoxShadow(
                      color: Colors.black.withValues(alpha: 0.1),
                      offset: const Offset(0, 2),
                      blurRadius: 4,
                    ),
                  ],
                ),
                child: CircleAvatar(
                  backgroundImage: userImagePath != 'assets/images/avatar.webp'
                      ? NetworkImage(userImagePath) as ImageProvider
                      : AssetImage(userImagePath),
                  radius: 20,
                ),
              ),
              const SizedBox(width: 12),
              // Nom d'utilisateur avec style amélioré
              Text(
                userName,
                style: const TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w600,
                  color: Colors.black87,
                  letterSpacing: 0.3,
                ),
              ),
              const Spacer(),
              BloodType(bloodType: bloodtype),
              const SizedBox(width: 8),
              const NotificationButton(hasNotifications: true),
              const SizedBox(width: 8),
            ],
          ),
        ),
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}

///NotificationButton
class NotificationButton extends StatelessWidget {
  /// notification state
  final bool hasNotifications;

  ///constructor
  const NotificationButton({super.key, this.hasNotifications = false});

  @override
  Widget build(BuildContext context) {
    return Stack(
      clipBehavior: Clip.none,
      children: [
        GestureDetector(
          onTap: () async {
            // Go to notification page
            await Navigator.push(
              context,
              MaterialPageRoute<dynamic>(
                  builder: (context) => const NotificationScreen(),
              ),
            );
          },
          child: Container(
            height: 40,
            width: 40,
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
            child: const Icon(
              Icons.notifications_outlined,
              color: Colors.black87,
              size: 22,
            ),
          ),
        ),
        if (hasNotifications)
          Positioned(
            top: -2,
            right: -2,
            child: Container(
              height: 10,
              width: 10,
              decoration: const BoxDecoration(
                color: AppColors.customRed,
                shape: BoxShape.circle,
              ),
            ),
          ),
      ],
    );
  }
}

/// Blood type widget
class BloodType extends StatelessWidget {
  ///Blood type text
  final String bloodType;

  ///constructor
  const BloodType({required this.bloodType, super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 40,
      width: 40,
      decoration: BoxDecoration(
        color: AppColors.customRed.withValues(alpha: 0.1),
        borderRadius: BorderRadius.circular(12),
        border: Border.all(
          color: AppColors.customRed,
          width: 1.5,
        ),
      ),
      child: Center(
        child: Text(
          bloodType,
          style: const TextStyle(
            color: AppColors.customRed,
            fontWeight: FontWeight.bold,
            fontSize: 16,
          ),
        ),
      ),
    );
  }
}
