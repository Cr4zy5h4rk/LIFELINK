// lib/ui/features/notifications/widgets/notification_screen.dart

import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:lifelink/domain/models/campaign.dart';
import 'package:lifelink/domain/models/emergency.dart';
import 'package:lifelink/ui/notification/campaign_detail_screen.dart';
import 'package:lifelink/ui/notification/view_model/notification_view_model.dart';
import 'package:lifelink/utils/app_colors.dart';


/// Screen for notifications
class NotificationScreen extends StatefulWidget {
  /// constant constructor
  const NotificationScreen({super.key});

  @override
  State<NotificationScreen> createState() => _NotificationScreenState();
}

class _NotificationScreenState extends State<NotificationScreen> with
    SingleTickerProviderStateMixin {
  late NotificationViewModel _viewModel;
  late TabController _tabController;

  @override
  void initState() {
    super.initState();
    _viewModel = NotificationViewModel();
    _tabController = TabController(length: 2, vsync: this);
    _loadData();
  }

  Future<void> _loadData() async {
    await _viewModel.loadNotifications();
    if (mounted) {
      setState(() {});
    }
  }

  @override
  void dispose() {
    _tabController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFFFF8F3),
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: const Text(
          'Notifications',
          style: TextStyle(
            color: Color(0xFF2D2D2D),
            fontSize: 18,
            fontWeight: FontWeight.bold,
          ),
        ),
        bottom: TabBar(
          controller: _tabController,
          labelColor: AppColors.customRed,
          unselectedLabelColor: Colors.grey,
          indicatorColor: AppColors.customRed,
          tabs: const [
            Tab(text: 'Urgences'),
            Tab(text: 'Campagnes'),
          ],
        ),
      ),
      body: TabBarView(
        controller: _tabController,
        children: [
          // Tab des urgences
          _buildEmergenciesTab(),

          // Tab des campagnes
          _buildCampaignsTab(),
        ],
      ),
    );
  }

  Widget _buildEmergenciesTab() {
    if (_viewModel.isLoading) {
      return const Center(child: CircularProgressIndicator(
          color: AppColors.customRed,),
      );
    }

    if (_viewModel.emergencies.isEmpty) {
      return const Center(
        child: Text(
            'Aucune urgence pour le moment',
             style: TextStyle(
               color: AppColors.customRed,
               fontSize: 24,
               fontWeight: FontWeight.bold,
             ),
        ),
      );
    }

    return RefreshIndicator(
      onRefresh: _loadData,
      color: AppColors.customRed,
      child: ListView.builder(
        padding: const EdgeInsets.all(16),
        itemCount: _viewModel.emergencies.length,
        itemBuilder: (context, index) {
          return _buildEmergencyItem(_viewModel.emergencies[index]);
        },
      ),
    );
  }

  Widget _buildCampaignsTab() {
    if (_viewModel.isLoading) {
      return const Center(child: CircularProgressIndicator(
          color: AppColors.customRed,
      ),
      );
    }

    if (_viewModel.campaigns.isEmpty) {
      return const Center(
        child: Text(
            'Aucune campagne pour le moment',
             style: TextStyle(
               color: AppColors.customRed,
               fontSize: 24,
               fontWeight: FontWeight.bold,
             ),

        ),
      );
    }

    return RefreshIndicator(
      onRefresh: _loadData,
      color: AppColors.customRed,
      child: ListView.builder(
        padding: const EdgeInsets.all(16),
        itemCount: _viewModel.campaigns.length,
        itemBuilder: (context, index) {
          return _buildCampaignItem(_viewModel.campaigns[index]);
        },
      ),
    );
  }

  Widget _buildEmergencyItem(Emergency emergency) {
    // Créer le chemin d'image en fonction du type de groupe sanguin
    final imagePath = 'assets/images/blood_group_${emergency.bloodType.replaceAll('+', 'plus').replaceAll('-', 'minus').toLowerCase()}.png';

    return Container(
      margin: const EdgeInsets.only(bottom: 16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(16),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withValues(alpha: 0.05),
            offset: const Offset(0, 3),
            blurRadius: 8,
          ),
        ],
      ),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // En-tête avec badge urgence
            Row(
              children: [
                Container(
                  padding: const EdgeInsets.symmetric(
                      horizontal: 10,
                      vertical: 4,
                  ),
                  decoration: BoxDecoration(
                    color: AppColors.customRed,
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: const Text(
                    'URGENT',
                    style: TextStyle(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                      fontSize: 10,
                    ),
                  ),
                ),
                const SizedBox(width: 8),
                Text(
                  DateFormat('dd MMM yyyy, HH:mm').format(emergency.date),
                  style: const TextStyle(
                    fontSize: 12,
                    color: Colors.grey,
                  ),
                ),
              ],
            ),
            const SizedBox(height: 12),
            // Titre
            Text(
              emergency.title,
              style: const TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 16,
                color: Color(0xFF2D2D2D),
              ),
            ),
            const SizedBox(height: 12),
            // Détails avec icônes
            Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // Badge du groupe sanguin
                Container(
                  width: 48,
                  height: 48,
                  decoration: BoxDecoration(
                    gradient: const LinearGradient(
                      begin: Alignment.topLeft,
                      end: Alignment.bottomRight,
                      colors: [
                        AppColors.customRed,
                        AppColors.customLightRed,
                      ],
                    ),
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: Center(
                    child: Image.asset(
                      imagePath,
                      width: 28,
                      height: 28,
                      color: Colors.white,
                      errorBuilder: (context, error, stackTrace) {
                        return Text(
                          emergency.bloodType,
                          style: const TextStyle(
                            color: Colors.white,
                            fontWeight: FontWeight.bold,
                            fontSize: 16,
                          ),
                        );
                      },
                    ),
                  ),
                ),
                const SizedBox(width: 12),
                // Infos hôpital
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Row(
                        children: [
                          const Icon(
                            Icons.location_on_outlined,
                            size: 16,
                            color: Color(0xFF9E9E9E),
                          ),
                          const SizedBox(width: 4),
                          Expanded(
                            child: Text(
                              emergency.hospital,
                              style: const TextStyle(
                                fontSize: 14,
                                color: Color(0xFF9E9E9E),
                              ),
                            ),
                          ),
                        ],
                      ),
                      const SizedBox(height: 4),
                      const Row(
                        children: [
                          Icon(
                            Icons.phone_outlined,
                            size: 16,
                            color: Color(0xFF9E9E9E),
                          ),
                          SizedBox(width: 4),
                          Text(
                            'Coming soon...',
                            style: TextStyle(
                              fontSize: 14,
                              color: Color(0xFF9E9E9E),
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            // Boutons d'action
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                // Bouton audio
                GestureDetector(
                  onTap: () {
                    // Logique pour jouer l'audio

                  },
                  child: Container(
                    width: 40,
                    height: 40,
                    decoration: BoxDecoration(
                      color: AppColors.customRed.withValues(alpha: 0.1),
                      shape: BoxShape.circle,
                    ),
                    child: const Icon(
                      Icons.volume_up,
                      color: AppColors.customRed,
                      size: 20,
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildCampaignItem(Campaign campaign) {
    final dateFormat = DateFormat('dd MMM');
    final startDate = dateFormat.format(campaign.startDate);
    final endDate = dateFormat.format(campaign.endDate);

    return GestureDetector(
      onTap: () async {
        // Navigation vers la page de détail de la campagne
        await Navigator.push(
          context,
          MaterialPageRoute<CampaignDetailScreen>(
            builder: (context) => CampaignDetailScreen(
                campaign: campaign,
                hasDoneSubscription: true,
            ),
          ),
        );
      },
      child: Container(
        margin: const EdgeInsets.only(bottom: 16),
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(16),
          boxShadow: [
            BoxShadow(
              color: Colors.black.withValues(alpha: 0.05),
              offset: const Offset(0, 3),
              blurRadius: 8,
            ),
          ],
        ),
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // En-tête avec dates
              Row(
                children: [
                  Container(
                    padding: const EdgeInsets.symmetric(
                        horizontal: 10,
                        vertical: 4,
                    ),
                    decoration: BoxDecoration(
                      color: AppColors.customBlue,
                      borderRadius: BorderRadius.circular(12),
                    ),
                    child: const Text(
                      'CAMPAGNE',
                      style: TextStyle(
                        color: Colors.white,
                        fontWeight: FontWeight.bold,
                        fontSize: 10,
                      ),
                    ),
                  ),
                  const SizedBox(width: 8),
                  Text(
                    'Du $startDate au $endDate',
                    style: const TextStyle(
                      fontSize: 12,
                      color: Colors.grey,
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 12),
              // Image et description
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // Image de la campagne
                  ClipRRect(
                    borderRadius: BorderRadius.circular(12),
                    child: Image.network(
                      campaign.imageUrl,
                      width: 80,
                      height: 80,
                      fit: BoxFit.cover,
                    ),
                  ),
                  const SizedBox(width: 12),
                  // Description
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          campaign.title,
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 14,
                            color: Color(0xFF2D2D2D),
                          ),
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                        ),
                        const SizedBox(height: 8),
                        Row(
                          children: [
                            const Icon(
                              Icons.location_on_outlined,
                              size: 16,
                              color: Color(0xFF9E9E9E),
                            ),
                            const SizedBox(width: 4),
                            Expanded(
                              child: Text(
                                campaign.location,
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
                        Row(
                          children: [
                            const Icon(
                              Icons.phone_outlined,
                              size: 16,
                              color: Color(0xFF9E9E9E),
                            ),
                            const SizedBox(width: 4),
                            Text(
                              campaign.contactPhone ?? 'no contact',
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
                ],
              ),
              const SizedBox(height: 12),
              // Boutons d'action
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  // Bouton "Voir détails"
                  const Text(
                    'Voir détails',
                    style: TextStyle(
                      color: AppColors.customBlue,
                      fontWeight: FontWeight.bold,
                      fontSize: 12,
                    ),
                  ),
                  // Bouton audio
                  GestureDetector(
                    onTap: () {
                      // Logique pour jouer l'audio

                    },
                    child: Container(
                      width: 40,
                      height: 40,
                      decoration: BoxDecoration(
                        color: AppColors.customRed.withValues(alpha: 0.1),
                        shape: BoxShape.circle,
                      ),
                      child: const Icon(
                        Icons.volume_up,
                        color: AppColors.customRed,
                        size: 20,
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
