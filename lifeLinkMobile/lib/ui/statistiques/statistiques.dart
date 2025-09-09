/*
* Cette page est statique et ne contient pas de données dynamiques.
* Nous n'avons pas eu le temps de l'integrer avec le backend.
* */

import 'package:flutter/material.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/posts/posts_screen.dart';
import 'package:lifelink/ui/statistiques/widgets/map.dart';

import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/dimens.dart';
import 'package:provider/provider.dart';


/// Statistiques
class Statistiques extends StatelessWidget {
  final List<String> bloodTypes = [
    'A+',
    'A-',
    'B+',
    'B-',
    'AB+',
    'AB-',
    'O+',
    'O-',
  ];
  String? selectedBloodType;
  double progress = 0.5;
  @override
  Widget build(BuildContext context) {
    final navigationProvider = Provider.of<NavigationProvider>(context);
    return Scaffold(
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
      backgroundColor: AppColors.lightColorScheme.onPrimary,
      body: SafeArea(
        child: Padding(
          padding: Dimens.of(context).edgeInsetsScreenSymmetric,
          child: SingleChildScrollView(
            child: Center(
              child: Column(
                children: <Widget>[
                  SizedBox(
                    width: MediaQuery.of(context).size.width,
                    child: Stack(
                      alignment: Alignment.center,
                      children: <Widget>[
                        const Positioned(
                          left: 0,
                          child: BackButton(
                            color: Colors.black, // Customize the color as needed
                          ),
                        ),
                        Row(
                          mainAxisSize: MainAxisSize.min,
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Image.asset('assets/icons/statistiques.png'),
                            const SizedBox(width: 10),
                            const Text(
                              'Statistiques',
                              style: TextStyle(
                                fontSize: 25,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 40),
                  Container(
                    padding: Dimens.of(context).edgeInsetsScreenSymmetric,
                    width: MediaQuery.of(context).size.width,
                    height: 150,
                    decoration: BoxDecoration(
                      color: AppColors.customRed,
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              '12,458',
                              style: TextStyle(
                                fontSize: 40,
                                fontWeight: FontWeight.bold,
                                color: AppColors.lightColorScheme.onPrimary,
                              ),
                            ),
                            Text(
                              "dons en 2025 jusqu'à présent",
                              style: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: AppColors.lightColorScheme.onPrimary,
                              ),
                            ),
                          ],
                        ),
                        Image.asset('assets/images/donation-photo.png'),
                      ],
                    ),
                  ),
                  const SizedBox(height: 10),
                  Container(
                    padding: Dimens.of(context).edgeInsetsScreenSymmetric,
                    width: MediaQuery.of(context).size.width,
                    height: 150,
                    decoration: BoxDecoration(
                      color: AppColors.customRed,
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              '320',
                              style: TextStyle(
                                fontSize: 40,
                                fontWeight: FontWeight.bold,
                                color: AppColors.lightColorScheme.onPrimary,
                              ),
                            ),
                            Text(
                              'vies sauvées en 2025',
                              style: TextStyle(
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                                color: AppColors.lightColorScheme.onPrimary,
                              ),
                            ),
                          ],
                        ),
                        Image.asset('assets/icons/heart-line.png'),
                      ],
                    ),
                  ),
                  const SizedBox(height: 10),
                  Container(
                    padding: Dimens.of(context).edgeInsetsScreenSymmetric,
                    width: MediaQuery.of(context).size.width,
                    height: 100,
                    decoration: BoxDecoration(
                      color: AppColors.customRed,
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          children: [
                            Text(
                              '${progress * 100}%',
                              style: TextStyle(
                                fontSize: 20,
                                fontWeight: FontWeight.bold,
                                color: AppColors.lightColorScheme.onPrimary,
                              ),
                            ),
                            const SizedBox(width: 5),
                            Text(
                              "de l'objectif atteint...",
                              style: TextStyle(
                                fontSize: 12,
                                color: AppColors.lightColorScheme.onPrimary,
                              ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 10),
                        Stack(
                          children: [
                            Container(
                              width: MediaQuery.of(context).size.width,
                              height: 10,
                              decoration: BoxDecoration(
                                color: Colors.grey,
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                            Container(
                              height: 10,
                              width:
                              MediaQuery.of(context).size.width /
                                  1.2 *
                                  progress,
                              decoration: BoxDecoration(
                                color: AppColors.lightColorScheme.onPrimary,
                                borderRadius: BorderRadius.circular(10),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 20),
                  Row(
                    children: [
                      const Text(
                        'Niveau de stock actuel',
                        style: TextStyle(fontSize: 20),
                      ),
                      const SizedBox(width: 10),
                      Image.asset('assets/icons/sound.png'),
                    ],
                  ),
                  const SizedBox(height: 10),
                  Row(
                    children: [
                      DropdownButton<String>(
                        value: selectedBloodType,
                        hint: const Text('Sélectionner un groupe sanguin'),
                        onChanged: (newValue) {
                          // setState(() {
                          //   selectedBloodType = newValue!;
                          // });
                        },
                        items:
                        bloodTypes.map<DropdownMenuItem<String>>((
                            value,
                            ) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(value),
                          );
                        }).toList(),
                        style: const TextStyle(fontSize: 16, color: Colors.black),
                        icon: const Icon(Icons.arrow_drop_down),
                        elevation: 16,
                        underline: Container(height: 2, color: Colors.grey),
                      ),
                    ],
                  ),
                  const SizedBox(height: 20),
                  MyMap(),
                  const SizedBox(height: 20,),
                  Container(
                    height: 350,
                    padding: const EdgeInsets.all(20),
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(10),
                      color: AppColors.lightColorScheme.secondary,
                    ),
                    child: Column(
                      children: [
                        Row(
                          children: [
                            Image.asset('assets/icons/heart-filled.png'),
                            const SizedBox(width: 10,),
                            Text(
                                'Le don de sang est indispensable',
                                style: TextStyle(
                                    fontSize: 18,
                                    fontWeight: FontWeight.bold,
                                    color:
                                    AppColors.lightColorScheme.onSecondary,
                                ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 20,),
                        Row(
                          children: [
                            Image.asset('assets/icons/checkmark.png'),
                            const SizedBox(width: 10,),
                            Text(
                                'pour les maladies du sang (drépanocytose...)',
                                style: TextStyle(
                                    fontSize: 14,
                                    color:
                                    AppColors.lightColorScheme.onSecondary,
                                ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 20,),
                        Row(
                          children: [
                            Image.asset('assets/icons/checkmark.png'),
                            const SizedBox(width: 10,),
                            Text(
                                'pour les patients sous chimiothérapie',
                                style: TextStyle(
                                    fontSize: 14,
                                    color:
                                    AppColors.lightColorScheme.onSecondary,
                                ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 20,),
                        Row(
                          children: [
                            Image.asset('assets/icons/checkmark.png'),
                            const SizedBox(width: 10,),
                            Text(
                                'pour les interventions chirurgicales',
                                style: TextStyle(
                                    fontSize: 14,
                                    color:
                                    AppColors.lightColorScheme.onSecondary,
                                ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 20,),
                        Row(
                          children: [
                            Image.asset('assets/icons/checkmark.png'),
                            const SizedBox(width: 10,),
                            Text(
                                'pour les urgences et autres situations vitales',
                                style: TextStyle(
                                    fontSize: 14,
                                    color:
                                    AppColors.lightColorScheme.onSecondary,
                                ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 20,),
                        Text(
                          "Un seul don peut sauver jusqu'à 3 vies."
                              " Faites la différence aujourd'hui !",
                          textAlign: TextAlign.center,
                          style: TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.bold ,
                              color: AppColors.lightColorScheme.onSecondary,
                          ),
                        ),
                        const SizedBox(height: 20,),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            const SizedBox(),
                            Image.asset('assets/icons/audio-white.png'),
                          ],
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
