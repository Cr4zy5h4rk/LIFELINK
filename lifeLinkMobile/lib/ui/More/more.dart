import 'package:flutter/material.dart';
import 'package:lifelink/ui/More/widgets/menu_item.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/posts/posts_screen.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/auth_service.dart';
import 'package:provider/provider.dart';


///
class MoreScreen extends StatelessWidget {
  /// constant constructor
  const MoreScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final authService = AuthService();
    final navigationProvider = Provider.of<NavigationProvider>(context);

    return Scaffold(
      backgroundColor: Colors.grey[100],
      body: Column(
        children: [
          // Section fixe en haut
          ColoredBox(
            color: Colors.white,
            child: SafeArea(
              bottom: false,
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(top: 20, bottom: 20),
                    child: SizedBox(
                      height: 48,
                      width: double.infinity,
                      child: Stack(
                        alignment: Alignment.center,
                        children: [
                          // Titre au centre
                          const Text(
                            'Plus',
                            style: TextStyle(
                              fontSize: 24,
                              fontWeight: FontWeight.bold,
                              color: Colors.black87,
                            ),
                          ),
                          // Bouton retour à gauche
                          Positioned(
                            left: 8,
                            child: IconButton(
                              icon: const Icon(Icons.arrow_back, color: Colors.black87),
                              onPressed: () {
                                Navigator.of(context).pop();
                              },
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),

                  Padding(
                    padding: const EdgeInsets.only(top: 35),
                    child: Stack(
                      clipBehavior: Clip.none,
                      alignment: Alignment.topCenter,
                      children: [
                        // Container rouge avec FutureBuilder pour les données utilisateur
                        FutureBuilder(
                          future: authService.getUserInfo(),
                          builder: (context, snapshot) {
                            // Affichage pendant le chargement
                            if (snapshot.connectionState == ConnectionState.waiting) {
                              return Container(
                                width: double.infinity,
                                margin: const EdgeInsets.symmetric(horizontal: 16),
                                padding: const EdgeInsets.only(top: 45, bottom: 20),
                                decoration: BoxDecoration(
                                  color: AppColors.customRed,
                                  borderRadius: BorderRadius.circular(12),
                                ),
                                child: const Column(
                                  children: [
                                    CircularProgressIndicator(color: Colors.white),
                                    SizedBox(height: 10),
                                    Text('Chargement...', style: TextStyle(color: Colors.white)),
                                  ],
                                ),
                              );
                            }
                            // Gestion des erreurs
                            else if (snapshot.hasError) {
                              return Container(
                                width: double.infinity,
                                margin: const EdgeInsets.symmetric(horizontal: 16),
                                padding: const EdgeInsets.only(top: 45, bottom: 20),
                                decoration: BoxDecoration(
                                  color: AppColors.customRed,
                                  borderRadius: BorderRadius.circular(12),
                                ),
                                child: const Column(
                                  children: [
                                    Text(
                                      'Erreur de chargement',
                                      style: TextStyle(
                                        fontSize: 18,
                                        fontWeight: FontWeight.bold,
                                        color: Colors.white,
                                      ),
                                    ),
                                  ],
                                ),
                              );
                            }
                            // Affichage des données
                            else {
                              final userData = snapshot.data;
                              return Container(
                                width: double.infinity,
                                margin: const EdgeInsets.symmetric(horizontal: 16),
                                padding: const EdgeInsets.only(top: 45, bottom: 20),
                                decoration: BoxDecoration(
                                  color: AppColors.customRed,
                                  borderRadius: BorderRadius.circular(12),
                                ),
                                child: Column(
                                  children: [
                                    // Nom dynamique
                                    Text(
                                      (userData?['firstName'] != null || userData?['lastName'] != null)?
                                      '${userData?['firstName'] ?? ''} ${userData?['lastName'] ?? ''}':
                                      'Unkown user',
                                      style: const TextStyle(
                                        fontSize: 22,
                                        fontWeight: FontWeight.bold,
                                        color: Colors.white,
                                      ),
                                    ),
                                    const SizedBox(height: 6),
                                    Text(
                                      'Groupe sanguin : ${userData?['resusType'] == 'POSITIVE' ?
                                      "${userData?['bloodType'] ?? '+'}+":
                                      "${userData?['bloodType'] ?? '-'}-"}',
                                      style: const TextStyle(
                                        fontSize: 16,
                                        color: Colors.white,
                                        fontWeight: FontWeight.bold,
                                      ),
                                    ),
                                  ],
                                ),
                              );
                            }
                          },
                        ),
                        Positioned(
                          top: -35,
                          child: FutureBuilder(
                            future: authService.getUserInfo(),
                            builder: (context, snapshot) {
                              final userData = snapshot.data;
                              return Container(
                                width: 70,
                                height: 70,
                                decoration: BoxDecoration(
                                  color: const Color(0xFFFFD700),
                                  shape: BoxShape.circle,
                                  border: Border.all(
                                    color: Colors.white,
                                    width: 5,
                                  ),
                                ),
                                child: ClipOval(
                                  child: userData != null && userData is Map<String, dynamic> && userData['picture'] != null
                                      ? Image.network(
                                    userData['picture'] as String,
                                    fit: BoxFit.cover,
                                    width: 70,
                                    height: 70,
                                    errorBuilder: (context, error, stackTrace) {
                                      return const Center(
                                        child: Icon(
                                          Icons.person,
                                          size: 40,
                                          color: Colors.white,
                                        ),
                                      );
                                    },
                                  )
                                      : const Center(
                                    child: Icon(
                                      Icons.person,
                                      size: 40,
                                      color: Colors.white,
                                    ),
                                  ),
                                ),
                              );
                            },
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),

          // Liste défilante
          Expanded(
            child: ListView(
              children: [
                const MenuItem(imagePath: 'Icons.warning', title: 'Urgences',onTap: null,),
                const MenuItem(imagePath: 'assets/icons/statistiques.png', title: 'Statistiques',onTap: null,),
                const MenuItem(imagePath: 'assets/icons/DonationCenter.png', title: 'Centres de collectes',onTap: null,),
                const MenuItem(imagePath: 'assets/icons/campagne.png', title: 'Campagnes de collecte',onTap: null,),
                const MenuItem(imagePath: 'assets/icons/profil-icon.png', title: 'Profil & statistiques',onTap: null,),
                const MenuItem(imagePath: 'Icons.handshake', title: 'Partenaires',onTap: null,),
                const MenuItem(imagePath: 'assets/images/chatbot.png', title: 'Amina - chatbot',onTap: null,),
                const MenuItem(imagePath: 'Icons.share', title: "Demande de partage d'informations",onTap: null,),
                const MenuItem(imagePath: 'Icons.favorite', title: 'Voir les articles favoris',onTap: null,),
                MenuItem(imagePath: 'Icons.logout', title: 'Deconnexion',onTap: () async {
                  await AuthService().logout();
                },),
                const SizedBox(height: 30),
              ],
            ),
          ),
        ],
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
