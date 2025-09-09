import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:lifelink/domain/models/user_shared_info.dart';
import 'package:lifelink/utils/app_colors.dart';

class RequestDetailScreen extends StatelessWidget {
  final EnrichedShareInfoRequest request;
  final VoidCallback onAccept;
  final VoidCallback onReject;

  RequestDetailScreen({
    required this.request,
    required this.onAccept,
    required this.onReject,
  });

  @override
  Widget build(BuildContext context) {
    final DateFormat formatter = DateFormat('dd/MM/yyyy');

    return Scaffold(
      appBar: AppBar(
        title: const Text('Détails de la demande'),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // En-tête
            _buildHeaderSection(context),

            const SizedBox(height: 24),

            // Section des détails
            _buildDetailsSection(formatter),

            const SizedBox(height: 32),

            // Section des actions
            _buildActionsSection(context),
          ],
        ),
      ),
    );
  }

  Widget _buildHeaderSection(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          request.getSourceName(),
          style: const TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
          ),
        ),
        const SizedBox(height: 8),
        Row(
          children: [
            Icon(Icons.location_on, size: 16, color: Colors.grey[600]),
            const SizedBox(width: 4),
            Expanded(
              child: Text(
                request.getLocationText(),
                style: TextStyle(
                  fontSize: 16,
                  color: Colors.grey[600],
                ),
              ),
            ),
          ],
        ),
        const SizedBox(height: 16),
        Container(
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            color: AppColors.customLightRed,
            borderRadius: BorderRadius.circular(8),
          ),
          child: const Row(
            children: [
              Icon(
                Icons.info_outline,
                color: Colors.white,
              ),
              SizedBox(width: 16),
              Expanded(
                child: Text(
                  'Cette organisation souhaite accéder à vos informations personnelles pour faciliter le processus de don de sang.',
                  style: TextStyle(
                    color: Colors.white,
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildDetailsSection(DateFormat formatter) {
    return Card(
      color: Colors.white,
      elevation: 2,
      child: Padding(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Détails de la demande',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: 16),
            _buildDetailRow('Date de la demande', formatter.format(request.requestDate ?? DateTime.now())),

            if (request.campaignStartAt != null && request.campaignEndAt != null)
              _buildDetailRow(
                'Période de la campagne',
                '${formatter.format(request.campaignStartAt!)} - ${formatter.format(request.campaignEndAt!)}',
              ),

            if (request.campaignDetails != null && request.campaignDetails!.isNotEmpty)
              _buildDetailRow('Description de la campagne', request.campaignDetails!),

            if (request.centerName != null)
              _buildDetailRow('Centre de don', request.centerName!),

            if (request.centerContact != null)
              _buildDetailRow('Contact du centre', request.centerContact!),

            if (request.centerDetails != null && request.centerDetails!.isNotEmpty)
              _buildDetailRow('Détails du centre', request.centerDetails!),

            if (request.addressText != null)
              _buildDetailRow('Adresse', request.addressText!),

            if (request.region != null)
              _buildDetailRow('Région', request.region!),
          ],
        ),
      ),
    );
  }

  Widget _buildDetailRow(String label, String value) {
    return Padding(
      padding: EdgeInsets.only(bottom: 12),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            label,
            style: TextStyle(
              fontSize: 14,
              color: Colors.grey[600],
              fontWeight: FontWeight.w500,
            ),
          ),
          SizedBox(height: 4),
          Text(
            value,
            style: TextStyle(
              fontSize: 16,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildActionsSection(BuildContext context) {
    return Card(
      color: Colors.white,
      elevation: 2,
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Répondre à la demande',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
              ),
            ),
            const SizedBox(height: 16),
            const Text(
              "En acceptant, vous autorisez le partage de l'ensemble de vos données personnelles avec cette organisation pour faciliter le processus de don de sang.",
              style: TextStyle(fontSize: 14),
            ),
            const SizedBox(height: 24),
            Row(
              children: [
                Expanded(
                  child: OutlinedButton(
                    onPressed: () => _showRejectConfirmation(context),
                    style: OutlinedButton.styleFrom(
                      padding: const EdgeInsets.symmetric(vertical: 12),
                      side: const BorderSide(color: Colors.red),
                      foregroundColor: Colors.red,
                    ),
                    child: const Text('Refuser'),
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () => _showAcceptConfirmation(context),
                    style: ElevatedButton.styleFrom(
                      padding: const EdgeInsets.symmetric(vertical: 12),
                      backgroundColor: Colors.green,
                      foregroundColor: Colors.white,
                    ),
                    child: const Text('Accepter'),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  void _showAcceptConfirmation(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("Confirmer l'acceptation"),
          content: Text(
            "Vous êtes sur le point d'accepter de partager vos informations personnelles avec ${request.getSourceName()}.\n\n"
                'Cette action ne peut pas être annulée.',
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('Annuler'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).pop();
                onAccept();
                Navigator.of(context).pop(); // Retourner à l'écran de liste
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.green,
                foregroundColor: Colors.white,
              ),
              child: const Text('Confirmer'),
            ),
          ],
        );
      },
    );
  }

  void _showRejectConfirmation(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Confirmer le refus'),
          content: Text(
            'Vous êtes sur le point de refuser de partager vos informations personnelles avec ${request.getSourceName()}.\n\n'
                'Cette action ne peut pas être annulée.',
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: Text('Annuler'),
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.of(context).pop();
                onReject();
                Navigator.of(context).pop(); // Retourner à l'écran de liste
              },
              child: Text('Confirmer'),
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red,
                foregroundColor: Colors.white,
              ),
            ),
          ],
        );
      },
    );
  }
}