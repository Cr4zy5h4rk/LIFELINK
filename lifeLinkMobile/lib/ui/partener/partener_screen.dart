import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/partner.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/partener/widgets/partner_info_item.dart';
import 'package:lifelink/ui/partener/widgets/partner_list_item.dart';
import 'package:lifelink/ui/posts/posts_screen.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:provider/provider.dart';



///Partener screen
class PartnersScreen extends StatefulWidget{

  /// constant constructor
  const PartnersScreen({super.key});

  @override
  State<PartnersScreen> createState() => _PartnersScreenState();

}

class _PartnersScreenState extends State<PartnersScreen> {
  // Liste fictive de partenaires
  static final List<Partner> partners = [
    Partner(
      name: 'Entreprise Alpha',
      logo: 'https://via.placeholder.com/150',
      region: 'Île-de-France',
      address: '123 Avenue des Champs-Élysées, 75008 Paris',
      latitude: 48.8698,
      longitude: 2.3075,
    ),
    Partner(
      name: 'Société Beta',
      logo: 'https://via.placeholder.com/150',
      region: "Provence-Alpes-Côte d'Azur",
      address: '45 Boulevard de la Croisette, 06400 Cannes',
      latitude: 43.5500,
      longitude: 7.0170,
    ),
    Partner(
      name: 'Groupe Gamma',
      logo: 'https://via.placeholder.com/150',
      region: 'Auvergne-Rhône-Alpes',
      address: '10 Rue de la République, 69001 Lyon',
      latitude: 45.7671,
      longitude: 4.8342,
    ),
    Partner(
      name: 'Corporation Delta',
      logo: 'https://via.placeholder.com/150',
      region: 'Nouvelle-Aquitaine',
      address: '25 Cours du Chapeau Rouge, 33000 Bordeaux',
      latitude: 44.8422,
      longitude: -0.5751,
    ),
    Partner(
      name: 'Établissements Epsilon',
      logo: 'https://via.placeholder.com/150',
      region: 'Grand Est',
      address: '8 Place Kléber, 67000 Strasbourg',
      latitude: 48.5839,
      longitude: 7.7455,
    ),
    Partner(
      name: 'Établissements Epsilon',
      logo: 'https://via.placeholder.com/150',
      region: 'Grand Est',
      address: '8 Place Kléber, 67000 Strasbourg',
      latitude: 48.5839,
      longitude: 7.7455,
    ),
    Partner(
      name: 'Établissements Epsilon',
      logo: 'https://via.placeholder.com/150',
      region: 'Grand Est',
      address: '8 Place Kléber, 67000 Strasbourg',
      latitude: 48.5839,
      longitude: 7.7455,
    ),
    Partner(
      name: 'Établissements Epsilon',
      logo: 'https://via.placeholder.com/150',
      region: 'Grand Est',
      address: '8 Place Kléber, 67000 Strasbourg',
      latitude: 48.5839,
      longitude: 7.7455,
    ),
  ];

  // Pour suivre quel partenaire est actuellement sélectionné
  int? expandedIndex;

  Future<void> _openMaps(double lat, double lng) async {
    // open google maps app
  }

  void _showPartnerDetails(Partner partner) {
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      backgroundColor: Colors.transparent,
      builder: (context) {
        return GestureDetector(
          onTap: () => Navigator.of(context).pop(), // tap en dehors
          child: Stack(
            children: [
              Container(color: Colors.transparent), // fond semi-transparent

              GestureDetector(
                onTap: () {}, // empêche la propagation du clic à l’intérieur
                child: DraggableScrollableSheet(
                  initialChildSize: 0.6,
                  minChildSize: 0.5,
                  maxChildSize: 0.95,
                  builder: (_, controller) => DecoratedBox(
                    decoration: BoxDecoration(
                      color: Theme.of(context).colorScheme.surface,
                      borderRadius: const BorderRadius.vertical(
                          top: Radius.circular(20),),
                    ),
                    child: Column(
                      children: [
                        Padding(
                          padding: const EdgeInsets.only(top: 12),
                          child: Container(
                            width: 40,
                            height: 4,
                            decoration: BoxDecoration(
                              color: Colors.grey.withValues(alpha: 0.3),
                              borderRadius: BorderRadius.circular(2),
                            ),
                          ),
                        ),
                        Expanded(
                          child: ListView(
                            controller: controller,
                            padding: const EdgeInsets.all(20),
                            children: [
                              // Logo and name header
                              Row(
                                children: [
                                  ClipRRect(
                                    borderRadius: BorderRadius.circular(16),
                                    child: Image.network(
                                      partner.logo,
                                      width: 80,
                                      height: 80,
                                      fit: BoxFit.cover,
                                      errorBuilder: (context, error, stackTrace) {
                                        return Container(
                                          width: 80,
                                          height: 80,
                                          color: AppColors.customLightRed,
                                          child: const Icon(
                                            Icons.business,
                                            size: 40,
                                            color: Colors.white,
                                          ),
                                        );
                                      },
                                    ),
                                  ),
                                  const SizedBox(width: 20),
                                  Expanded(
                                    child: Text(
                                      partner.name,
                                      style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                  ),
                                ],
                              ),

                              const SizedBox(height: 32),

                              // Region
                              PartnerInfoItem(
                                icon: Icons.location_city,
                                title: 'Région',
                                content: partner.region,
                              ),

                              const Divider(height: 32),

                              // Address
                              PartnerInfoItem(
                                icon: Icons.location_on,
                                title: 'Adresse',
                                content: partner.address,
                              ),

                              const SizedBox(height: 40),

                              // Google Maps button
                              SizedBox(
                                width: double.infinity,
                                child: ElevatedButton.icon(
                                  onPressed: () => _openMaps(partner.latitude, partner.longitude),
                                  icon: const Icon(
                                      Icons.map,
                                      color: AppColors.customLightRed,
                                  ),
                                  label: const Text(
                                    'Ouvrir dans Google Maps',
                                    style: TextStyle(
                                        fontWeight: FontWeight.bold,
                                        color: AppColors.customLightRed,
                                    ),
                                  ),
                                  style: ElevatedButton.styleFrom(
                                    padding: const EdgeInsets.symmetric(vertical: 16),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(12),
                                    ),
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }


  @override
  Widget build(BuildContext context) {
    final navigationProvider = Provider.of<NavigationProvider>(context);

    return Scaffold(
      appBar: AppBar(
        title: const Row(
          children: [
            // IconButton(
            //   icon: const Icon(Icons.arrow_back, color: Colors.black87),
            //   onPressed: () {
            //     Navigator.of(context).pop();
            //   },
            // ),
            SizedBox(width: 20,),
            Icon(
              Icons.handshake_outlined,
              color: AppColors.customRed,
              size: 25,
            ),
            SizedBox(width: 10,),
            Text(
              'Nos Partenaires',
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 22,
              ),
            ),
          ],
        ),
        elevation: 0,
      ),
      body: DecoratedBox(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter,
            colors: [
              Theme.of(context).colorScheme.surface,
              Theme.of(context).colorScheme.surfaceContainerHighest
                  .withValues(alpha: 0.3),
            ],
          ),
        ),
        child: ListView.builder(
          padding: const EdgeInsets.all(16),
          itemCount: partners.length,
          itemBuilder: (context, index) {
            return PartnerListItem(
              partner: partners[index],
              onTap: () => _showPartnerDetails(partners[index]),
            );
          },
        ),
      ),
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
                builder: (context) => const PostsScreen(),
              ),
            );
            navigationProvider.setIndex(2);
          }
        },
      ),
    );
  }
}



