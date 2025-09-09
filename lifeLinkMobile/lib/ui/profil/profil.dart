/*
* Cette page est statique et ne contient pas de données dynamiques.
* Nous n'avons pas eu le temps de l'integrer avec le backend.
* */

import 'package:flutter/material.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/posts/posts_screen.dart';
import 'package:lifelink/ui/profil/widgets/historique_tile.dart';
import 'package:lifelink/ui/profil/widgets/regenerationCycle.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/dimens.dart';
import 'package:provider/provider.dart';

/// profil
class Profil extends StatelessWidget {

  /// constant constructor
  const Profil({super.key});

  @override
  Widget build(BuildContext context) {
    final navigationProvider = Provider.of<NavigationProvider>(context);

    return Scaffold(
      backgroundColor: AppColors.lightColorScheme.onPrimary,
      body: SafeArea(
        child: Padding(
          padding: Dimens
              .of(context)
              .edgeInsetsScreenSymmetric,
          child: SingleChildScrollView(
            child: Center(
              child: Column(
                children: [
                  SizedBox(
                    width: MediaQuery
                        .of(context)
                        .size
                        .width,
                    child: Stack(
                      alignment: Alignment.center,
                      children: <Widget>[
                        const Positioned(
                          left: 0,
                          child: BackButton(
                            color: Colors.black,
                          ),
                        ),
                        Row(
                          mainAxisSize: MainAxisSize.min,
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Image.asset('assets/icons/profil-icon.png'),
                            const SizedBox(width: 10),
                            const Text(
                              'Profil & Statistiques',
                              style: TextStyle(
                                fontSize: 22,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 20),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      const Stack(
                        children: [
                          RegenerationCycleCard(
                            progress: 0.5,
                          ),
                        ],
                      ),
                      Stack(
                        children: [
                          Container(
                            height: 150,
                            width: MediaQuery
                                .of(context)
                                .size
                                .width / 2.6,
                            padding: const EdgeInsets.all(20),
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(10),
                              color: AppColors.customRed,
                            ),
                            child: Column(
                              children: [
                                Text(
                                  'Groupe sanguin',
                                  style: TextStyle(
                                    fontWeight: FontWeight.bold,
                                    fontSize: 16,
                                    color: AppColors.lightColorScheme.onPrimary,
                                  ),
                                ),
                              ],
                            ),
                          ),
                          Positioned(
                            top: 65,
                            left: MediaQuery
                                .of(context)
                                .size
                                .width / 2.6 / 2.5,
                            child: Image.asset('assets/icons/sang.png'),
                          ),
                          Positioned(
                            top: 70,
                            left: MediaQuery
                                .of(context)
                                .size
                                .width / 2.6 / 2.3,
                            child: Text(
                              'A+',
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 16,
                                color: AppColors.lightColorScheme.onPrimary,
                              ),
                            ),
                          ),
                          Positioned(
                            right: 20,
                            bottom: 20,
                            child: Image.asset('assets/icons/audio-white.png'),
                          ),
                        ],
                      ),
                    ],
                  ),
                  const SizedBox(height: 40),
                  Container(
                    width: MediaQuery
                        .of(context)
                        .size
                        .width,
                    padding: const EdgeInsets.all(20),
                    height: 160,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      color: Colors.grey[200],
                    ),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        const Text(
                          'Recevoir un sms pour les demandes urgentes de sang ',
                          textAlign: TextAlign.center,
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 18,
                          ),
                        ),
                        const SizedBox(height: 20),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Container(
                              height: 40,
                              width: 150,
                              decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(100),
                                color: AppColors.customRed,
                              ),
                              child: Center(
                                child: Text(
                                  'Desactiver',
                                  style: TextStyle(
                                    fontWeight: FontWeight.bold,
                                    color: AppColors.lightColorScheme.onPrimary,
                                    fontSize: 16,
                                  ),
                                ),
                              ),
                            ),
                            Image.asset('assets/icons/sound.png'),
                          ],
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 20),
                  Container(
                    width: MediaQuery
                        .of(context)
                        .size
                        .width,
                    padding: const EdgeInsets.all(20),
                    height: 250,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      color: Colors.grey[200],
                    ),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        const Column(
                          children: [
                            Text(
                              'Vos Récompenses Accumulées ',
                              textAlign: TextAlign.center,
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 18,
                              ),
                            ),
                            SizedBox(height: 20),
                            Text(
                              '2500 points',
                              textAlign: TextAlign.center,
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 30,
                              ),
                            ),
                            SizedBox(height: 10),
                            Text(
                              'Utilisez vos points pour obtenir des récompenses'
                                  ' dans nos structures partenaires !',
                              textAlign: TextAlign.center,
                              style: TextStyle(fontSize: 15),
                            ),
                          ],
                        ),
                        const SizedBox(height: 20),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Row(
                              children: [
                                Container(
                                  height: 40,
                                  width: 120,
                                  decoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(100),
                                    color: Colors.green,
                                  ),
                                  child: const Center(
                                    child: Text(
                                      'Partenaires',
                                      style: TextStyle(
                                        fontWeight: FontWeight.bold,
                                        fontSize: 16,
                                        color: Colors.white,
                                      ),
                                    ),
                                  ),
                                ),
                                const SizedBox(width: 10,),
                                Container(
                                  height: 40,
                                  width: 100,
                                  decoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(100),
                                    color: Colors.green,
                                  ),
                                  child: const Center(
                                    child: Text(
                                      'Utiliser',
                                      style: TextStyle(
                                        fontWeight: FontWeight.bold,
                                        fontSize: 16,
                                        color: Colors.white,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                            Image.asset('assets/icons/sound.png'),
                          ],
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 20,),
                  const Text(
                    'Historique des dons',
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      fontSize: 18,
                    ),),
                  const SizedBox(height: 20,),
                  const HistoriqueTile(
                    lieu: 'Centre national de collecte',
                    volume: '480 ml',
                    date: 'Le 28 fevrier 2025',
                  ),
                  const SizedBox(height: 20,),
                  const HistoriqueTile(
                    lieu: 'Centre national de collecte',
                    volume: '480 ml',
                    date: 'Le 28 fevrier 2025',
                  ),
                  const SizedBox(height: 20,),
                  const HistoriqueTile(
                    lieu: 'Centre national de collecte',
                    volume: '480 ml',
                    date: 'Le 28 fevrier 2025',
                  ),
                  const SizedBox(height: 20,),
                  const HistoriqueTile(
                    lieu: 'Centre national de collecte',
                    volume: '480 ml',
                    date: 'Le 28 fevrier 2025',
                  ),

                ],
              ),
            ),
          ),
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
                builder: (context) => const   PostsScreen(),
              ),
            );
            navigationProvider.setIndex(2);
          }
        },
      ),
    );
  }
}
