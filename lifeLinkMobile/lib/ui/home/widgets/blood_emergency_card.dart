import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:lifelink/domain/models/emergency.dart';
import 'package:lifelink/utils/app_colors.dart';

/// Emergency widget
class BloodEmergencyCard extends StatelessWidget {
  ///Emergencies data from parent widget
  final List<Emergency> emergencies;
  ///constructor
  const BloodEmergencyCard({required this.emergencies,super.key});

  // En-tête de la section avec indicateur audio
  Widget _buildEmergenciesHeader() {
    return Row(
      children: [
        const SizedBox(width: 8),
        const Text(
          'Urgences',
          style: TextStyle(
            fontSize: 20,
            fontWeight: FontWeight.bold,
            color: Color(0xFF2D2D2D),
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
    );
  }

  Widget _buildEmergencyCard(Emergency emergency) {
    // Créer le chemin d'image en fonction du type de groupe sanguin
    final imagePath = 'assets/icons/blood_group_${emergency.bloodType.replaceAll('+', 'plus').replaceAll('-', 'minus').toLowerCase()}.png';

    return Container(
      width: 330,
      margin: const EdgeInsets.only(right: 12),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(16),
        boxShadow: [
          BoxShadow(
            color: AppColors.customRed.withValues(alpha: 0.1),
            offset: const Offset(0, 3),
            blurRadius: 8,
          ),
        ],
      ),
      child: Padding(
        padding: const EdgeInsets.all(12),
        child: Row(
          children: [
            // Badge du groupe sanguin avec image
            SizedBox(
              width: 48,
              height: 48,
              child: Center(
                child: Image.asset(
                  imagePath,
                  width: 50,
                  height:50,
                ),
              ),
            ),
            const SizedBox(width: 12),
            // Détails de l'urgence
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  // Titre avec ellipsis
                  Text(
                    emergency.title,
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 14,
                      color: Color(0xFF2D2D2D),
                    ),
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                  ),
                  const SizedBox(height: 15),
                  // Lieu avec ellipsis
                  Row(
                    children: [
                      const Icon(
                        Icons.location_on_outlined,
                        size: 14,
                        color: Color(0xFF9E9E9E),
                      ),
                      const SizedBox(width: 4),
                      Expanded(
                        child: Text(
                          emergency.hospital,
                          style: const TextStyle(
                            fontSize: 12,
                            color: Color(0xFF9E9E9E),
                          ),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 4),
                  // Date/heure formatée
                  Row(
                    children: [
                      const Icon(
                        Icons.access_time_outlined,
                        size: 14,
                        color: Color(0xFF9E9E9E),
                      ),
                      const SizedBox(width: 4),
                      Text(
                        DateFormat('dd MMM yyyy, HH:mm').format(emergency.date),
                        style: const TextStyle(
                          fontSize: 12,
                          color: Color(0xFF9E9E9E),
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
            // Bouton pour jouer l'audio
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
      ),
    );
  }


  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        // En-tête avec indicateur audio
        _buildEmergenciesHeader(),
        const SizedBox(height: 16),
        // Liste des urgences
        SizedBox(
          height: 110,
          child: ListView.builder(
            scrollDirection: Axis.horizontal,
            itemCount: emergencies.length,
            itemBuilder: (context, index) {
              return _buildEmergencyCard(emergencies[index]);
            },
          ),
        ),
      ],
    );
  }

}
