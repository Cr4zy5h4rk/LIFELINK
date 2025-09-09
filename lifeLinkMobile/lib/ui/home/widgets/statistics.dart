import 'package:flutter/material.dart';
import 'package:lifelink/utils/app_colors.dart';

/// Statistiques widget
class StatisticsWidget extends StatelessWidget{

  /// Nombre de donneurs
  final String numDonors;
  /// Pourcentage atteint
  final String percentageAchieved;
  /// Nombre de vies sauvées
  final String livesSaved;

  ///constructor
  const StatisticsWidget({
    required this.numDonors,
    required this.percentageAchieved,
    required this.livesSaved,
    super.key,
  });


  Widget _buildStatItem(String value, String label, Color color) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 16),
      decoration: BoxDecoration(
        color: color,
        borderRadius: BorderRadius.circular(12),
      ),
      child: Column(
        children: [
          Text(
            value,
            style: const TextStyle(
              fontSize: 18,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: 4),
          Text(
            label,
            style: const TextStyle(
              fontSize: 12,
              color: Colors.black54,
            ),
            textAlign: TextAlign.center,
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
        Row(
          children: [
            const Text(
              'Cette année en statistique',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(width: 8),
            GestureDetector(
              onTap: () {
                // Code pour jouer l'audio
                // Vous pourriez implémenter un AudioPlayer ici
              },
              child: Container(
                width: 36,
                height: 36,
                decoration: BoxDecoration(
                  color: AppColors.customRed.withValues(alpha: 0.1),
                  shape: BoxShape.circle,
                ),
                child: const Icon(
                  Icons.volume_up,
                  color: AppColors.customRed,
                  size: 18,
                ),
              ),
            ),
          ],
        ),
        const SizedBox(height: 20),
        Row(
          children: [
            Expanded(
              child: _buildStatItem(numDonors, 'Donneurs', Colors.blue.shade50),
            ),
            const SizedBox(width: 8),
            Expanded(
              child: _buildStatItem(
                percentageAchieved, "de l'objectif atteint",
                Colors.green.shade50,
              ),
            ),
            const SizedBox(width: 8),
            Expanded(
              child: _buildStatItem(
                livesSaved, 'Vies sauvées',
                Colors.purple.shade50,
              ),
            ),
          ],
        ),
      ],
    );
  }

}
