import 'package:flutter/material.dart';
import 'package:lifelink/utils/app_colors.dart';

/// Campagne
class CampagneTile extends StatelessWidget {

  /// constant constructor
  const CampagneTile({
    required this.location,
    required this.phone,
    required this.horaires,
    super.key,
  });

  /// location
  final String location;
  /// phone
  final String phone;
  /// horaires
  final String horaires;
  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          height: 220,
          padding: const EdgeInsets.all(20),
          decoration: BoxDecoration(
            color: AppColors.customRed,
            borderRadius: BorderRadius.circular(10),
          ),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Image.asset('assets/images/thumbnail.png'),
              const SizedBox(width: 20),
              Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      Image.asset('assets/icons/location.png'),
                      const SizedBox(width: 10),
                      Text(
                        location,
                        style: TextStyle(
                          color: AppColors.lightColorScheme.onPrimary,
                          fontSize: 16,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 10),
                  Row(
                    children: [
                      Image.asset('assets/icons/call.png'),
                      const SizedBox(width: 10),
                      Text(
                        phone,
                        style: TextStyle(
                          color: AppColors.lightColorScheme.onPrimary,
                          fontSize: 16,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 10),
                  Row(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Image.asset('assets/icons/clock.png'),
                      const SizedBox(width: 10),
                      SizedBox(
                        width: 200,
                        child: Text(
                          horaires,
                          style: TextStyle(
                            color: AppColors.lightColorScheme.onPrimary,
                            fontSize: 16,
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 10),
                ],
              ),
            ],
          ),
        ),
        Positioned(
          right: 20,
          top: 20,
          child: Image.asset('assets/icons/audio-white.png'),
        ),
        Positioned(
          right: 20,
          bottom: 20,
          child: Container(
            height: 40,
            width: 120,
            padding: const EdgeInsets.all(10),
            decoration: BoxDecoration(
              color: AppColors.lightColorScheme.onPrimary,
              borderRadius: BorderRadius.circular(100),
            ),
            child: const Center(
              child: Text(
                "S'inscrire",
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                  color: Colors.green,
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }
}
