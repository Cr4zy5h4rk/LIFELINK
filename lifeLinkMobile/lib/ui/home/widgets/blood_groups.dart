import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/blood_group.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/responsive_helper.dart';


/// Blood groups widget
class BloodGroupsWidget extends StatelessWidget {
  /// Responsive helper
  final ResponsiveHelper responsiveHelper;

  ///Blood groups type data
  List<BloodGroup> get _bloodGroups =>  [
      const BloodGroup(type: 'A+'),
      const BloodGroup(type: 'A-'),
      const BloodGroup(type: 'B+'),
      const BloodGroup(type: 'B-'),
      const BloodGroup(type: 'O+'),
      const BloodGroup(type: 'O-'),
      const BloodGroup(type: 'AB+'),
      const BloodGroup(type: 'AB-'),
  ];
  ///constructor
  const BloodGroupsWidget({required this.responsiveHelper,super.key});

  Widget _buildBloodGroupItem(BloodGroup group) {
    final imagePath = 'assets/icons/blood_group_${group.type.replaceAll('+', 'plus').replaceAll('-', 'minus').toLowerCase()}.png';

    return DecoratedBox(
      decoration: BoxDecoration(
        gradient: const LinearGradient(
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
          colors: [
            Color(0xFFFFEEEE),
            Colors.white,
          ],
        ),
        borderRadius: BorderRadius.circular(12),
        boxShadow: [
          BoxShadow(
            color: AppColors.customRed.withValues(alpha: 0.15),
            offset: const Offset(0, 3),
            blurRadius: 6,
          ),
        ],
      ),
      child: Stack(
        children: [
          // Élément décoratif d'angle
          Positioned(
            top: 0,
            right: 0,
            child: Container(
              width: 20,
              height: 20,
              decoration: const BoxDecoration(
                color: AppColors.customRed,
                borderRadius: BorderRadius.only(
                  topRight: Radius.circular(12),
                  bottomLeft: Radius.circular(12),
                ),
              ),
            ),
          ),
          // Contenu principal centré
          Center(
            child: Padding(
              padding: const EdgeInsets.all(8),
              child: Image.asset(
                imagePath,
                width: 50,
                height: 50,
                fit: BoxFit.contain,
              ),
            ),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        const Text(
          'Groupes sanguins',
          style: TextStyle(
            fontSize: 20,
            fontWeight: FontWeight.bold,
          ),
        ),
        const SizedBox(height: 20),
        GridView.builder(
          shrinkWrap: true,
          physics: const NeverScrollableScrollPhysics(),
          gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
            crossAxisCount: responsiveHelper.isDesktop()
                ? 8
                : responsiveHelper.isTablet()
                ? 6
                : 4,
            crossAxisSpacing: 8,
            mainAxisSpacing: 8,
          ),
          itemCount: _bloodGroups.length,
          itemBuilder: (context, index) {
            return _buildBloodGroupItem(_bloodGroups[index]);
          },
        ),
      ],
    );
  }

}
