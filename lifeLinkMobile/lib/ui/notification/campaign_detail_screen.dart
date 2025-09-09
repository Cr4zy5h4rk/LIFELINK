// lib/ui/features/notifications/widgets/campaign_detail_screen.dart

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:lifelink/domain/models/campaign.dart';
import 'package:lifelink/utils/app_colors.dart';

/// Screen for campaign details
class CampaignDetailScreen extends StatelessWidget {
  /// constant constructor
  final Campaign campaign;
  ///pour savoir si l'utilisateur a déjà inscris
  final bool hasDoneSubscription;

  /// constant constructor
  const CampaignDetailScreen({
    required this.campaign,
    required this.hasDoneSubscription
    , super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFFFF8F3),
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: const Text(
          'Détails de la campagne',
          style: TextStyle(
            color: Color(0xFF2D2D2D),
            fontSize: 18,
            fontWeight: FontWeight.bold,
          ),
        ),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Color(0xFF2D2D2D)),
          onPressed: () => Navigator.of(context).pop(),
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Image de couverture
            SizedBox(
              width: double.infinity,
              height: 200,
              child: Stack(
                fit: StackFit.expand,
                children: [
                  // Image
                  Image.network(
                    campaign.imageUrl,
                    fit: BoxFit.cover,
                  ),
                  // Overlay gradient
                  Container(
                    decoration: BoxDecoration(
                      gradient: LinearGradient(
                        begin: Alignment.topCenter,
                        end: Alignment.bottomCenter,
                        colors: [
                          Colors.transparent,
                          Colors.black.withValues(alpha: 0.7),
                        ],
                      ),
                    ),
                  ),
                  // Dates sur l'image
                  Positioned(
                    bottom: 16,
                    left: 16,
                    child: Container(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 12,
                          vertical: 6,
                      ),
                      decoration: BoxDecoration(
                        color: AppColors.customRed,
                        borderRadius: BorderRadius.circular(16),
                      ),
                      child: Text(
                        _formatCampaignDates(
                            campaign.startDate,
                            campaign.endDate,
                        ),
                        style: const TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.bold,
                          fontSize: 12,
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),

            // Contenu principal
            Padding(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // Titre
                  Text(
                    campaign.title,
                    style: const TextStyle(
                      fontSize: 22,
                      fontWeight: FontWeight.bold,
                      color: Color(0xFF2D2D2D),
                    ),
                  ),
                  const SizedBox(height: 16),

                  // Informations avec icônes
                  _buildInfoRow(Icons.location_on, 'Lieu', campaign.location),
                  const SizedBox(height: 12),
                  _buildInfoRow(
                      Icons.phone,
                      'Contact',
                      campaign.contactPhone ?? 'no contact',
                  ),
                  const SizedBox(height: 12),
                  _buildInfoRow(
                      Icons.calendar_today,
                      'Dates',
                      '${DateFormat('dd MMM yyyy')
                          .format(campaign.startDate)} - '
                          '${DateFormat('dd MMM yyyy')
                          .format(campaign.endDate)}'
                  ),
                  const SizedBox(height: 12),
                  _buildInfoRow(
                      Icons.access_time,
                      'Horaires',
                       campaign.schedule ?? 'no schedule',
                  ),

                  const SizedBox(height: 24),
                  const SizedBox(height: 24),

                  // Informations supplémentaires
                  const Text(
                    'Informations importantes',
                    style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                      color: Color(0xFF2D2D2D),
                    ),
                  ),
                  const SizedBox(height: 12),
                  _buildInfoCard(
                    'Conditions pour donner',
                    'Être en bonne santé, avoir entre 18 et 65 ans, '
                        'peser au moins 50 kg, et ne pas avoir donné de sang '
                        'au cours des 3 derniers mois.',
                  ),
                  const SizedBox(height: 32),

                  // Bouton
                  if (!hasDoneSubscription)
                    _buildSubscriptionButton(
                      "S'inscrire",
                      () => {
                        // Logique d'inscription
                      },
                    ),

                  if (hasDoneSubscription)
                    _buildSubscriptionButton(
                      "Annuler l'inscription",
                          () => {
                        // Logique de desinscription
                      },
                    ),

                  const SizedBox(height: 16),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSubscriptionButton(String text, VoidCallback onPressed) {
    return SizedBox(
      width: double.infinity,
      child: ElevatedButton(
        onPressed: onPressed,
        style: ElevatedButton.styleFrom(
          backgroundColor:
          (text == "S'inscrire")? Colors.green : AppColors.customRed,
          padding: const EdgeInsets.symmetric(vertical: 16),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
          ),
        ),
        child: Text(
          text,
          style: const TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.bold,
            color: Colors.white,
          ),
        ),
      ),
    );
  }

  Widget _buildInfoRow(IconData icon, String label, String value) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Icon(
          icon,
          size: 20,
          color: AppColors.customRed,
        ),
        const SizedBox(width: 12),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                label,
                style: const TextStyle(
                  fontSize: 12,
                  color: Color(0xFF9E9E9E),
                ),
              ),
              const SizedBox(height: 2),
              Text(
                value,
                style: const TextStyle(
                  fontSize: 14,
                  color: Color(0xFF2D2D2D),
                  fontWeight: FontWeight.w500,
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildInfoCard(String title, String content) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withValues(alpha: 0.05),
            offset: const Offset(0, 2),
            blurRadius: 6,
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            title,
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.bold,
              color: Color(0xFF2D2D2D),
            ),
          ),
          const SizedBox(height: 8),
          Text(
            content,
            style: const TextStyle(
              fontSize: 14,
              color: Color(0xFF6E6E6E),
              height: 1.5,
            ),
          ),
        ],
      ),
    );
  }

  String _formatCampaignDates(DateTime startDate, DateTime endDate) {
    final dateFormat = DateFormat('dd MMM');
    final start = dateFormat.format(startDate);
    final end = dateFormat.format(endDate);

    return '$start - $end';
  }

}
