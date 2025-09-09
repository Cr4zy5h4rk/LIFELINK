import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:lifelink/domain/models/emergency.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/emergencies/view_model/emergency_map_view_model.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/posts/posts_screen.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:provider/provider.dart';




///
class EmergencyMapScreen extends StatefulWidget {
  ///
  const EmergencyMapScreen({super.key});

  @override
  State<EmergencyMapScreen> createState() => _EmergencyMapScreenState();
}

class _EmergencyMapScreenState extends State<EmergencyMapScreen>
    with SingleTickerProviderStateMixin {
  final MapController _mapController = MapController();
  String _selectedBloodType = '';
  String _selectedRegion = '';
  late TabController _tabController;

  bool _isMapTabActive = true;

  @override
  void initState() {
    super.initState();
    _tabController = TabController(length: 2, vsync: this);
    _tabController.addListener(_handleTabChange);
  }

  void _handleTabChange() {
    // Mettre à jour l'état de visibilité de la carte
    if (_tabController.index == 0) {
      if (!_isMapTabActive && mounted) {
        setState(() {
          _isMapTabActive = true;
        });
      }
    } else {
      if (_isMapTabActive && mounted) {
        setState(() {
          _isMapTabActive = false;
        });
      }
    }
  }

  @override
  void dispose() {
    _tabController..removeListener(_handleTabChange)
    ..dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final navigationProvider = Provider.of<NavigationProvider>(context);

    return ChangeNotifierProvider(
      create: (_) => EmergencyMapViewModel(),
      child: Consumer<EmergencyMapViewModel>(
        builder: (context, viewModel, child) {
          return Scaffold(
            backgroundColor: Colors.white,
            body: SafeArea(
              child: Column(
                children: [
                  // App Bar amélioré
                  _buildEnhancedAppBar(viewModel),

                  // Onglets pour basculer entre carte et liste
                  DecoratedBox(
                    decoration: BoxDecoration(
                      color: Colors.white,
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withValues(alpha: 0.05),
                          blurRadius: 4,
                          offset: const Offset(0, 2),
                        ),
                      ],
                    ),
                    child: TabBar(
                      controller: _tabController,
                      labelColor: AppColors.customRed,
                      unselectedLabelColor: Colors.grey,
                      indicatorColor: AppColors.customRed,
                      tabs: const [
                        Tab(icon: Icon(Icons.map), text: 'Carte'),
                        Tab(icon: Icon(Icons.list), text: 'Liste'),
                      ],
                    ),
                  ),

                  // Contenu principal
                  Expanded(
                    child: IndexedStack(
                      index: _tabController.index,
                      children: [
                        // Vue Carte - construite uniquement quand active
                        if (_isMapTabActive) _buildEnhancedMapView(viewModel)
                        else const Center(child: CircularProgressIndicator()),

                        // Vue Liste
                        _buildEnhancedListView(viewModel),
                      ],
                    ),
                  ),
                ],
              ),
            ),
            // FAB pour filtrer
            floatingActionButton: Padding(
              padding: const EdgeInsets.only(bottom: 20),
              child: FloatingActionButton(
                  backgroundColor: AppColors.customRed,
                  mini: true,
                  child: const Icon(Icons.filter_list, color: Colors.white),
                  onPressed: () {
                    _showEnhancedFilterDialog(viewModel);
                  },
            )
            ,),
            bottomNavigationBar: CustomBottomNavBar(
              currentIndex: navigationProvider.currentIndex,
              onTap: (index) async {
                navigationProvider.setIndex(index);
                if(index == 0){
                  await Navigator.push(
                    context,
                    MaterialPageRoute<dynamic>(
                      builder: (context) => const HomeScreen(),
                    ),
                  );
                  navigationProvider.setIndex(2);
                }else if(index == 1){
                  await Navigator.push(
                    context,
                    MaterialPageRoute<dynamic>(
                      builder: (context) => const   PostsScreen(),
                    ),
                  );
                  navigationProvider.setIndex(2);
                }
            },),
          );
        },
      ),
    );
  }

  Widget _buildEnhancedAppBar(EmergencyMapViewModel viewModel) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        boxShadow: [
          BoxShadow(
            color: Colors.black.withValues(alpha: 0.05),
            blurRadius: 4,
            offset: const Offset(0, 2),
          ),
        ],
      ),
      child: Row(
        children: [
          InkWell(
            onTap: () => Navigator.of(context).pop(),
            borderRadius: BorderRadius.circular(8),
            child: Container(
              padding: const EdgeInsets.all(8),
              child: const Icon(Icons.arrow_back, color: Colors.black87),
            ),
          ),
          const SizedBox(width: 8),
          const Icon(Icons.notifications_active, color: AppColors.customRed),
          const SizedBox(width: 12),
          const Text(
            'Urgences',
            style: TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.bold,
              color: Colors.black87,
            ),
          ),
          const Spacer(),
          // Affichage des filtres actifs
          if (_selectedBloodType.isNotEmpty || _selectedRegion.isNotEmpty)
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
              decoration: BoxDecoration(
                color: Colors.red.withValues(alpha: 0.1),
                borderRadius: BorderRadius.circular(16),
              ),
              child: Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  if (_selectedBloodType.isNotEmpty)
                    Text(
                      _selectedBloodType,
                      style: const TextStyle(
                        color: AppColors.customRed,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  if (_selectedBloodType.isNotEmpty &&
                      _selectedRegion.isNotEmpty)
                    const Text(' • '),
                  if (_selectedRegion.isNotEmpty)
                    Text(
                      _selectedRegion,
                      style: const TextStyle(
                        color: AppColors.customRed,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                ],
              ),
            ),
        ],
      ),
    );
  }

  Widget _buildEnhancedMapView(EmergencyMapViewModel viewModel) {

    // Méthode pour regrouper les urgences par position
    Map<String, List<Emergency>> groupEmergenciesByLocation() {
      final groupedEmergencies = <String, List<Emergency>>{};

      for (final emergency in viewModel.filteredEmergencies) {
        final location = viewModel.getEmergencyLocation(emergency);
        if (location == null) {
          continue;
        }

        final locationKey = '${location.latitude.toStringAsFixed(5)},'
            '${location.longitude.toStringAsFixed(5)}';

        if (groupedEmergencies.containsKey(locationKey)) {
          groupedEmergencies[locationKey]!.add(emergency);
        } else {
          groupedEmergencies[locationKey] = [emergency];
        }
      }

      return groupedEmergencies;
    }

    // Regrouper les urgences par position
    final groupedEmergencies = groupEmergenciesByLocation();

    return Stack(
      children: [
        // Carte
        FlutterMap(
          mapController: _mapController,
          options: MapOptions(
            initialCenter: viewModel.getMapCenter(),
            initialZoom: viewModel.getMapZoom(),
            onMapReady: () {
              _mapController.move(
                  viewModel.getMapCenter(),
                  viewModel.getMapZoom(),
              );
            },
          ),
          children: [
            TileLayer(
              urlTemplate: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
              userAgentPackageName: 'sn.lifelink.app',
            ),
            // Marqueurs pour les urgences
            MarkerLayer(
              markers: groupedEmergencies.entries.map((entry) {
                final emergencies = entry.value;
                final firstEmergency = emergencies.first;
                final location =
                viewModel.getEmergencyLocation(firstEmergency)!;

                // Si un seul marqueur à cette position, afficher normalement
                if (emergencies.length == 1) {
                  return Marker(
                    point: location,
                    width: 60,
                    height: 60,
                    child: GestureDetector(
                      onTap: () {
                        _showEmergencyDetailBottomSheet(
                          context,
                          firstEmergency,
                          viewModel,
                        );
                      },
                      child: _buildEnhancedMarker(firstEmergency),
                    ),
                  );
                }

                // Si plusieurs marqueurs à la même position,
                // afficher un cluster
                return Marker(
                  point: location,
                  width: 70,
                  height: 70,
                  child: GestureDetector(
                    onTap: () {
                      _showClusteredEmergenciesBottomSheet(
                        context,
                        emergencies,
                        viewModel,
                      );
                    },
                    child: _buildClusteredMarker(emergencies),
                  ),
                );
              }).toList(),
            ),
          ],
        ),

        // Compteur d'urgences
        if (viewModel.filteredEmergencies.isNotEmpty)
          Positioned(
            top: 16,
            left: 16,
            child: Container(
              padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(16),
                boxShadow: [
                  BoxShadow(
                    color: Colors.black.withValues(alpha: 0.1),
                    blurRadius: 4,
                    offset: const Offset(0, 2),
                  ),
                ],
              ),
              child: Text(
                '${viewModel.filteredEmergencies.length} '
                    "urgence${viewModel.filteredEmergencies.length > 1 ?
                's' : ''}",
                style: const TextStyle(
                  fontWeight: FontWeight.bold,
                  color: AppColors.customRed,
                ),
              ),
            ),
          ),

        // Bouton de géolocalisation
        Positioned(
          bottom: 94,
          right: 16,
          child: DecoratedBox(
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              color: Colors.white,
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withValues(alpha: 0.1),
                  blurRadius: 4,
                  offset: const Offset(0, 2),
                ),
              ],
            ),
            child: IconButton(
              icon: const Icon(Icons.my_location, color: AppColors.customRed),
              onPressed: () {
                // Logique pour centrer sur la position actuelle
              },
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildClusteredMarker(List<Emergency> emergencies) {
    return DecoratedBox(
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        color: Colors.white,
        border: Border.all(color: AppColors.customRed, width: 2),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withValues(alpha: 0.2),
            blurRadius: 6,
            offset: const Offset(0, 3),
          ),
        ],
      ),
      child: Stack(
        children: [
          // Image de fond
          Image.asset(
            'assets/icons/blood_group_${emergencies.first.bloodType.replaceAll('+', 'plus').replaceAll('-', 'minus').toLowerCase()}.png',
            width: 60,
            height: 60,
            color: Colors.black.withValues(alpha: 0.1),
            colorBlendMode: BlendMode.darken,
          ),
          // Badge avec le nombre d'urgences
          Positioned(
            right: 0,
            top: 0,
            child: Container(
              padding: const EdgeInsets.all(6),
              decoration: const BoxDecoration(
                shape: BoxShape.circle,
                color: AppColors.customRed,
              ),
              child: Text(
                '${emergencies.length}',
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
    );
  }

  void _showClusteredEmergenciesBottomSheet(
      BuildContext context,
      List<Emergency> emergencies,
      EmergencyMapViewModel viewModel,
      ) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (context) {
        return Container(
          height: MediaQuery.of(context).size.height * 0.7,
          decoration: const BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              // Poignée de défilement
              Center(
                child: Container(
                  margin: const EdgeInsets.only(top: 12, bottom: 16),
                  width: 40,
                  height: 4,
                  decoration: BoxDecoration(
                    color: Colors.grey.shade300,
                    borderRadius: BorderRadius.circular(2),
                  ),
                ),
              ),

              // En-tête
              Padding(
                padding: const EdgeInsets.fromLTRB(20, 0, 20, 20),
                child: Row(
                  children: [
                    Container(
                      width: 50,
                      height: 50,
                      decoration: BoxDecoration(
                        gradient: const LinearGradient(
                          begin: Alignment.topLeft,
                          end: Alignment.bottomRight,
                          colors: [
                            AppColors.customLightRed,
                            AppColors.customRed,
                          ],
                        ),
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Center(
                        child: Text(
                          '${emergencies.length}',
                          style: const TextStyle(
                            color: Colors.white,
                            fontWeight: FontWeight.bold,
                            fontSize: 20,
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(width: 16),
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            '${emergencies.length} urgences à cet emplacement',
                            style: const TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              color: Colors.black87,
                            ),
                          ),
                          const SizedBox(height: 4),
                          Text(
                            emergencies.first.hospital,
                            style: TextStyle(
                              fontSize: 14,
                              color: Colors.grey.shade700,
                            ),
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ),

              const Divider(height: 1),

              // Liste des urgences
              Expanded(
                child: ListView.separated(
                  padding: const EdgeInsets.all(20),
                  itemCount: emergencies.length,
                  separatorBuilder: (context, index) =>
                  const Divider(height: 1),
                  itemBuilder: (context, index) {
                    final emergency = emergencies[index];
                    return InkWell(
                      onTap: () {
                        Navigator.pop(context);
                        _showEmergencyDetailBottomSheet(
                          context,
                          emergency,
                          viewModel,
                        );
                      },
                      child: Padding(
                        padding: const EdgeInsets.symmetric(vertical: 12),
                        child: Row(
                          children: [
                            Container(
                              width: 40,
                              height: 40,
                              decoration: BoxDecoration(
                                color: Colors.red.withValues(alpha: 0.1),
                                borderRadius: BorderRadius.circular(8),
                              ),
                              child: Center(
                                child: Text(
                                  emergency.bloodType,
                                  style: const TextStyle(
                                    color: AppColors.customRed,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ),
                            ),
                            const SizedBox(width: 16),
                            Expanded(
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    'Besoin de sang ${emergency.bloodType}',
                                    style: const TextStyle(
                                      fontWeight: FontWeight.bold,
                                      fontSize: 16,
                                      color: Colors.black87,
                                    ),
                                  ),
                                  const SizedBox(height: 4),
                                  Text(
                                    _formatDate(emergency.date),
                                    style: TextStyle(
                                      fontSize: 14,
                                      color: Colors.grey.shade700,
                                    ),
                                  ),
                                ],
                              ),
                            ),
                            IconButton(
                              onPressed: () async {
                                await
                                viewModel.playAudio(emergency.wolofAudioUrls);
                              },
                              icon: const Icon(Icons.volume_up, size: 20),
                              style: IconButton.styleFrom(
                                backgroundColor: AppColors.customLightRed,
                                foregroundColor: Colors.white,
                                padding: const EdgeInsets.all(6),
                                shape: const CircleBorder(),
                              ),
                            ),
                          ],
                        ),
                      ),
                    );
                  },
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildEnhancedMarker(Emergency emergency) {
    return Stack(
      children: [
        // Épingle de carte stylisée
        Image.asset(
          'assets/icons/blood_group_${emergency.bloodType.replaceAll('+', 'plus').replaceAll('-', 'minus').toLowerCase()}.png',
          width: 60,
          height: 60,
        ),
      ],
    );
  }

  Widget _buildEnhancedListView(EmergencyMapViewModel viewModel) {
    if (viewModel.isLoading) {
      return const Center(child: CircularProgressIndicator(
          color: AppColors.customRed,),
      );
    }

    if (viewModel.filteredEmergencies.isEmpty) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.search_off, size: 64, color: Colors.grey.shade300),
            const SizedBox(height: 16),
            Text(
              'Aucune urgence trouvée',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
                color: Colors.grey.shade600,
              ),
            ),
            const SizedBox(height: 8),
            Text(
              'Essayez de modifier vos filtres',
              style: TextStyle(
                color: Colors.grey.shade400,
              ),
            ),
          ],
        ),
      );
    }

    return ListView.separated(
      padding: const EdgeInsets.all(16),
      itemCount: viewModel.filteredEmergencies.length,
      separatorBuilder: (context, index) => const Divider(
          height: 1, indent: 60,
      ),
      itemBuilder: (context, index) {
        final emergency = viewModel.filteredEmergencies[index];
        return _buildEnhancedEmergencyCard(emergency, viewModel);
      },
    );
  }

  Widget _buildEnhancedEmergencyCard(
      Emergency emergency,
      EmergencyMapViewModel viewModel,
      ) {
    return InkWell(
      onTap: () {
        _showEmergencyDetailBottomSheet(
          context,
          emergency,
          viewModel,
        );
      },
      borderRadius: BorderRadius.circular(8),
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 12),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Badge groupe sanguin
            Container(
              width: 48,
              height: 48,
              decoration: BoxDecoration(
                gradient: const LinearGradient(
                  begin: Alignment.topLeft,
                  end: Alignment.bottomRight,
                  colors: [AppColors.customLightRed, AppColors.customRed],
                ),
                borderRadius: BorderRadius.circular(8),
                boxShadow: [
                  BoxShadow(
                    color: Colors.red.withValues(alpha: 0.2),
                    blurRadius: 4,
                    offset: const Offset(0, 2),
                  ),
                ],
              ),
              child: Center(
                child: Text(
                  emergency.bloodType,
                  style: const TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                    fontSize: 18,
                  ),
                ),
              ),
            ),
            const SizedBox(width: 16),
            Expanded(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Urgence, Besoin de sang ${emergency.bloodType}',
                    style: const TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 16,
                      color: Colors.black87,
                    ),
                  ),
                  const SizedBox(height: 8),
                  Row(
                    children: [
                      Icon(
                        Icons.location_on,
                        size: 16,
                        color: Colors.grey.shade600,
                      ),
                      const SizedBox(width: 4),
                      Expanded(
                        child: Text(
                          emergency.hospital,
                          style: TextStyle(
                            fontSize: 14,
                            color: Colors.grey.shade700,
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
                      Icon(
                        Icons.access_time,
                        size: 16,
                        color: Colors.grey.shade600,
                      ),
                      const SizedBox(width: 4),
                      Text(
                        _formatDate(emergency.date),
                        style: TextStyle(
                          fontSize: 14,
                          color: Colors.grey.shade700,
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
            const SizedBox(width: 8),
            IconButton(
              onPressed: () async {
                await viewModel.playAudio(emergency.wolofAudioUrls);
              },
              icon: const Icon(Icons.volume_up),
              style: IconButton.styleFrom(
                backgroundColor: AppColors.customLightRed,
                foregroundColor: Colors.white,
                padding: const EdgeInsets.all(8),
                shape: const CircleBorder(),
              ),
            ),
          ],
        ),
      ),
    );
  }

  void _showEmergencyDetailBottomSheet(
      BuildContext context, Emergency emergency,
      EmergencyMapViewModel viewModel,
      ) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (context) {
        return Container(
          height: MediaQuery.of(context).size.height * 0.5,
          decoration: const BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              // Poignée de défilement
              Center(
                child: Container(
                  margin: const EdgeInsets.only(top: 12, bottom: 16),
                  width: 40,
                  height: 4,
                  decoration: BoxDecoration(
                    color: Colors.grey.shade300,
                    borderRadius: BorderRadius.circular(2),
                  ),
                ),
              ),

              // En-tête
              Padding(
                padding: const EdgeInsets.fromLTRB(20, 0, 20, 20),
                child: Row(
                  children: [
                    Container(
                      width: 60,
                      height: 60,
                      decoration: BoxDecoration(
                        gradient: const LinearGradient(
                          begin: Alignment.topLeft,
                          end: Alignment.bottomRight,
                          colors: [
                            AppColors.customLightRed,
                            AppColors.customRed,
                          ],
                        ),
                        borderRadius: BorderRadius.circular(12),
                      ),
                      child: Center(
                        child: Text(
                          emergency.bloodType,
                          style: const TextStyle(
                            color: Colors.white,
                            fontWeight: FontWeight.bold,
                            fontSize: 24,
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(width: 16),
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const Text(
                            'Besoin urgent de sang',
                            style: TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold,
                              color: Colors.black87,
                            ),
                          ),
                          const SizedBox(height: 4),
                          Text(
                            'Groupe ${emergency.bloodType}',
                            style: const TextStyle(
                              fontSize: 16,
                              color: AppColors.customRed,
                              fontWeight: FontWeight.w500,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ),

              const Divider(height: 1),

              // Détails
              Expanded(
                child: SingleChildScrollView(
                  padding: const EdgeInsets.all(20),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      _buildDetailItem(
                        icon: Icons.location_on,
                        title: 'Hôpital',
                        content: emergency.hospital,
                      ),
                      const SizedBox(height: 16),
                      _buildDetailItem(
                        icon: Icons.access_time,
                        title: 'Date & Heure',
                        content: _formatDate(emergency.date),
                      ),
                    ],
                  ),
                ),
              ),

              // Actions
              Padding(
                padding: const EdgeInsets.all(20),
                child: Row(
                  children: [
                    Expanded(
                      child: ElevatedButton.icon(
                        onPressed: () {
                          Navigator.pop(context);
                          // Centrer la carte sur cette urgence
                          if (viewModel.getEmergencyLocation(emergency)
                              != null)
                          {
                            _tabController.animateTo(0); // Afficher la carte
                            Future.delayed(const Duration(milliseconds: 300),
                                    ()
                            {
                              _mapController.move(
                                viewModel.getEmergencyLocation(emergency)!,
                                15,
                              );
                            });
                          }
                        },
                        icon: const Icon(
                            Icons.map,
                            color: Colors.white,
                        ),
                        label: const Text('Voir sur la carte'),
                        style: ElevatedButton.styleFrom(
                          backgroundColor: AppColors.customRed,
                          foregroundColor: Colors.white,
                          padding: const EdgeInsets.symmetric(vertical: 12),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(width: 12),
                    IconButton(
                      onPressed: () async {
                        await viewModel.playAudio(emergency.wolofAudioUrls);
                      },
                      icon: const Icon(Icons.volume_up),
                      style: IconButton.styleFrom(
                        backgroundColor: AppColors.customLightRed,
                        foregroundColor: Colors.white,
                        padding: const EdgeInsets.all(12),
                        shape: const CircleBorder(),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        );
      },
    );
  }

  Widget _buildDetailItem({
    required IconData icon,
    required String title,
    required String content,
  }) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Container(
          padding: const EdgeInsets.all(8),
          decoration: BoxDecoration(
            color: Colors.red.withValues(alpha: 0.1),
            borderRadius: BorderRadius.circular(8),
          ),
          child: Icon(
            icon,
            color: Colors.red,
            size: 20,
          ),
        ),
        const SizedBox(width: 12),
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                title,
                style: const TextStyle(
                  fontSize: 14,
                  color: Colors.grey,
                ),
              ),
              const SizedBox(height: 4),
              Text(
                content,
                style: const TextStyle(
                  fontSize: 16,
                  color: Colors.black87,
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  void _showEnhancedFilterDialog(EmergencyMapViewModel viewModel) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (context) {
        return StatefulBuilder(
          builder: (context, setState) {
            return Container(
              padding: EdgeInsets.only(
                bottom: MediaQuery.of(context).viewInsets.bottom,
              ),
              decoration: const BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
              ),
              child: SingleChildScrollView(
                child: Padding(
                  padding: const EdgeInsets.all(20),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      // Titre et barre
                      Center(
                        child: Container(
                          width: 40,
                          height: 4,
                          margin: const EdgeInsets.only(bottom: 16),
                          decoration: BoxDecoration(
                            color: Colors.grey.shade300,
                            borderRadius: BorderRadius.circular(2),
                          ),
                        ),
                      ),
                      const Text(
                        'Filtrer par',
                        style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                          color: Colors.black87,
                        ),
                      ),
                      const SizedBox(height: 24),

                      // Groupe sanguin
                      const Text(
                        'Groupe sanguin',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                          color: Colors.black87,
                        ),
                      ),
                      const SizedBox(height: 16),
                      Wrap(
                        spacing: 8,
                        runSpacing: 8,
                        children: [
                          _buildFilterChip(
                              '', 'Tous',
                              _selectedBloodType.isEmpty,
                                  (value) {
                                setState(() {
                                  _selectedBloodType = value;
                                });
                              }
                          ),
                          _buildFilterChip(
                              'A+', 'A+',
                              _selectedBloodType == 'A+',
                                  (value) {
                                setState(() {
                                  _selectedBloodType = value;
                                });
                              }
                          ),
                          _buildFilterChip(
                              'A-', 'A-',
                              _selectedBloodType == 'A-',
                                  (value) {
                                setState(() {
                                  _selectedBloodType = value;
                                });
                              }
                          ),
                          _buildFilterChip(
                              'B+', 'B+',
                              _selectedBloodType == 'B+',
                                  (value) {
                                setState(() {
                                  _selectedBloodType = value;
                                });
                              }
                          ),
                          _buildFilterChip(
                              'B-', 'B-',
                              _selectedBloodType == 'B-',
                                  (value) {
                                setState(() {
                                  _selectedBloodType = value;
                                });
                              }
                          ),
                          _buildFilterChip(
                              'AB+', 'AB+',
                              _selectedBloodType == 'AB+',
                                  (value) {
                                setState(() {
                                  _selectedBloodType = value;
                                });
                              }
                          ),
                          _buildFilterChip(
                              'AB-', 'AB-',
                              _selectedBloodType == 'AB-',
                                  (value) {
                                setState(() {
                                  _selectedBloodType = value;
                                });
                              }
                          ),
                          _buildFilterChip(
                              'O+', 'O+',
                              _selectedBloodType == 'O+',
                                  (value) {
                                setState(() {
                                  _selectedBloodType = value;
                                });
                              }
                          ),
                          _buildFilterChip(
                              'O-', 'O-',
                              _selectedBloodType == 'O-',
                                  (value) {
                                setState(() {
                                  _selectedBloodType = value;
                                });
                              }
                          ),
                        ],
                      ),

                      const SizedBox(height: 24),

                      // Région
                      const Text(
                        'Région',
                        style: TextStyle(
                          fontSize: 16,
                          fontWeight: FontWeight.bold,
                          color: Colors.black87,
                        ),
                      ),
                      const SizedBox(height: 16),
                      SingleChildScrollView(
                        scrollDirection: Axis.horizontal,
                        child: Padding(
                          padding: const EdgeInsets.symmetric(horizontal: 8),
                          child: Wrap(
                            spacing: 8,
                            runSpacing: 8,
                            children: [
                              _buildFilterChip('', 'Toutes',
                                  _selectedRegion.isEmpty, (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Dakar', 'Dakar',
                                  _selectedRegion == 'Dakar', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Thiès', 'Thiès',
                                  _selectedRegion == 'Thiès', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Tambacounda', 'Tambacounda',
                                  _selectedRegion == 'Tambacounda', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Diourbel', 'Diourbel',
                                  _selectedRegion == 'Diourbel', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Fatick', 'Fatick',
                                  _selectedRegion == 'Fatick', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Kaolack', 'Kaolack',
                                  _selectedRegion == 'Kaolack', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Kaffrine', 'Kaffrine',
                                  _selectedRegion == 'Kaffrine', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Kolda', 'Kolda',
                                  _selectedRegion == 'Kolda', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Kédougou', 'Kédougou',
                                  _selectedRegion == 'Kédougou', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Louga', 'Louga',
                                  _selectedRegion == 'Louga', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Sédhiou', 'Sédhiou',
                                  _selectedRegion == 'Sédhiou', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Ziguinchor', 'Ziguinchor',
                                  _selectedRegion == 'Ziguinchor', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Saint-Louis', 'Saint-Louis',
                                  _selectedRegion == 'Saint-Louis', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                              _buildFilterChip('Matam', 'Matam',
                                  _selectedRegion == 'Matam', (value) {
                                setState(() {
                                  _selectedRegion = value;
                                });
                              }),
                            ],
                          ),
                        ),
                      ),

                      const SizedBox(height: 32),

                      // Bouton d'application
                      SizedBox(
                        width: double.infinity,
                        child: ElevatedButton(
                          onPressed: () {
                            // Appliquer les filtres
                            viewModel..filterByBloodType(_selectedBloodType)
                            ..filterByRegion(_selectedRegion);

                            // Mettre à jour la carte
                            Future.delayed(const Duration(milliseconds: 300),
                                    () {
                              _mapController.move(
                                  viewModel.getMapCenter(),
                                  viewModel.getMapZoom(),
                              );
                            });

                            Navigator.pop(context);
                          },
                          style: ElevatedButton.styleFrom(
                            backgroundColor: AppColors.customRed,
                            foregroundColor: Colors.white,
                            padding: const EdgeInsets.symmetric(vertical: 16),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(8),
                            ),
                          ),
                          child: const Text(
                            'Appliquer le filtre',
                            style: TextStyle(
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            );
          },
        );
      },
    );
  }

  Widget _buildFilterChip(String value, String label, bool isSelected,
      Function(String) onSelected,) {
    return InkWell(
        onTap: () => onSelected(value),
        borderRadius: BorderRadius.circular(30),
        child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
    decoration: BoxDecoration(
    color: isSelected ? AppColors.customRed : Colors.grey.shade100,
    borderRadius: BorderRadius.circular(30),
    border: Border.all(
    color: isSelected ? AppColors.customRed : Colors.grey.shade300,
    ),
    ),
    child: Row(
    mainAxisSize: MainAxisSize.min,
    children: [
      if (isSelected)
        const Padding(
          padding: EdgeInsets.only(right: 6),
          child: Icon(
            Icons.check,
            color: Colors.white,
            size: 16,
          ),
        ),
      Text(
        label,
        style: TextStyle(
          fontSize: 14,
          fontWeight: isSelected ? FontWeight.bold : FontWeight.normal,
          color: isSelected ? Colors.white : Colors.black87,
        ),
      ),
    ],
    ),
        ),
    );
  }

  String _formatDate(DateTime date) {
    final now = DateTime.now();
    final today = DateTime(now.year, now.month, now.day);
    final dateToCheck = DateTime(date.year, date.month, date.day);

    if (dateToCheck == today) {
      return "Aujourd'hui, ${date.hour}h${date.minute.toString()
          .padLeft(2, '0')}";
    } else if (dateToCheck == today.subtract(const Duration(days: 1))) {
      return "Hier, ${date.hour}h${date.minute.toString().padLeft(2, '0')}";
    } else {
      const months = [
        'Jan', 'Fév', 'Mars', 'Avr', 'Mai', 'Juin',
        'Juil', 'Août', 'Sept', 'Oct', 'Nov', 'Déc',
      ];
      return '${date.day} ${months[date.month - 1]} ${date.year}, '
          "${date.hour}h${date.minute.toString().padLeft(2, '0')}";
    }
  }
}
