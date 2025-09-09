/*
* Cette page est statique et ne contient pas de données dynamiques.
* Nous n'avons pas eu le temps de l'integrer avec le backend.
* */

import 'package:flutter/material.dart';
import 'package:lifelink/ui/campagnes/widgets/campagnes_tile.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/posts/posts_screen.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/dimens.dart';
import 'package:provider/provider.dart';

/// Campagnes
class Campagnes extends StatefulWidget {

  /// constant constructor
  const Campagnes({super.key});

  @override
  State<Campagnes> createState() => _CampagnesState();
}

class _CampagnesState extends State<Campagnes> {
  final List<String> regions = [
    'Dakar',
    'Diourbel',
    'Fatick',
    'Kaffrine',
    'Kaolack',
    'Kédougou',
    'Kolda',
    'Louga',
    'Matam',
    'Saint-Louis',
    'Sédhiou',
    'Tambacounda',
    'Thiès',
    'Ziguinchor',
  ];

  String? selectedRegion;

  final List<String> statuts = ['En cours', 'Programme'];

  String? selectedStatut;

  @override
  Widget build(BuildContext context) {
    final navigationProvider = Provider.of<NavigationProvider>(context);
    return Scaffold(
      backgroundColor: AppColors.lightColorScheme.onPrimary,
      body: SafeArea(
        child: Padding(
          padding: Dimens.of(context).edgeInsetsScreenSymmetric,
          child: SingleChildScrollView(
            child: Center(
              child: Column(
                children: [
                  SizedBox(
                    width: MediaQuery.of(context).size.width,
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
                            Image.asset('assets/icons/campagne.png'),
                            const SizedBox(width: 10),
                            const Text(
                              'Campagnes de collectes',
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
                  Image.asset('assets/images/campagne-hero.png'),
                  const SizedBox(height: 20),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          const Text('Region',),
                          const SizedBox(height: 10),
                          Container(
                            padding: const EdgeInsets.all(10),
                            height: 50,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(5),
                              color: Colors.grey[200],
                            ),
                            child: DropdownButton<String>(
                              value: selectedRegion,
                              hint: const Text('Dakar'),
                              onChanged: (newValue) {
                                setState(() {
                                  selectedRegion = newValue;
                                });
                              },
                              items:
                                  regions.map<DropdownMenuItem<String>>((
                                    value,
                                  ) {
                                    return DropdownMenuItem<String>(
                                      value: value,
                                      child: Text(value),
                                    );
                                  }).toList(),
                              style: const TextStyle(
                                fontSize: 16,
                                color: Colors.black,
                              ),
                              icon: const Icon(Icons.arrow_drop_down),
                              elevation: 16,
                            ),
                          ),
                        ],
                      ),
                      Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          const Text('Status', style: TextStyle()),
                          const SizedBox(height: 10),
                          Container(
                            padding: const EdgeInsets.all(10),
                            width: 150,
                            height: 50,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(5),
                              color: Colors.grey[200],
                            ),
                            child: DropdownButton<String>(
                              value: selectedStatut,
                              hint: const Text('En cours'),
                              onChanged: (newValue) {
                                setState(() {
                                  selectedStatut = newValue;
                                });
                              },
                              items:
                                  statuts.map<DropdownMenuItem<String>>((
                                    value,
                                  ) {
                                    return DropdownMenuItem<String>(
                                      value: value,
                                      child: Text(value),
                                    );
                                  }).toList(),
                              style: const TextStyle(
                                fontSize: 16,
                                color: Colors.black,
                              ),
                              icon: const Icon(Icons.arrow_drop_down),
                              elevation: 16,
                            ),
                          ),
                        ],
                      ),
                    ],
                  ),
                  const SizedBox(height: 40),
                  const CampagneTile(
                    location: 'Rue Place 79, Dakar',
                    phone: '77 123 45 67',
                    horaires:
                        'Du lundi 26 au vendredi  01 de 9h a 13h et '
                            'le samedi 02 de 8h a 12h',
                  ),
                  const SizedBox(height: 20),
                  const CampagneTile(
                    location: 'Rue Place 79, Dakar',
                    phone: '77 123 45 67',
                    horaires:
                        'Du lundi 26 au vendredi  01 de 9h a 13h et '
                            'le samedi 02 de 8h a 12h',
                  ),
                  const SizedBox(height: 20),
                  const CampagneTile(
                    location: 'Rue Place 79, Dakar',
                    phone: '77 123 45 67',
                    horaires:
                        'Du lundi 26 au vendredi  01 de 9h a 13h et '
                            'le samedi 02 de 8h a 12h',
                  ),
                  const SizedBox(height: 20),
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
