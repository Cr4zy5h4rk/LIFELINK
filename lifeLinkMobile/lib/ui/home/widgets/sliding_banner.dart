import 'dart:async';
import 'package:flutter/material.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/responsive_helper.dart';

///Banner widget
class SlidingBanner extends StatefulWidget {
  ///Constructor
  const SlidingBanner({super.key});

  @override
  State<SlidingBanner> createState() => _SlidingBannerState();
}

class _SlidingBannerState extends State<SlidingBanner> {
  late final PageController _pageController;
  int _currentPage = 0;
  Timer? _timer;

  final List<BannerData> _bannerItems = [
    BannerData(
      title: 'Sauvez des vies, faites un don',
      imagePath: 'assets/images/donation_illustration.png',
      color: const Color(0xFFFFEFEF),
    ),
    BannerData(
      title: 'Chaque goutte de sang peut sauver une vie',
      imagePath: 'assets/images/donation_illustration2.png',
      color: const Color(0xFFE6F2FF),
    ),
    BannerData(
      title: 'Rejoignez notre communauté de donneurs',
      imagePath: 'assets/images/donation_illustration3.png',
      color: const Color(0xFFE8FFF1),
    ),
  ];

  @override
  void initState() {
    super.initState();
    // Initialiser le PageController avec un index initial qui permet
    // le défilement infini
    _pageController = PageController(
      initialPage: _bannerItems.length * 100,
    );

    // Démarrer le défilement automatique
    _startAutoSlide();
  }

  @override
  void dispose() {
    _timer?.cancel();
    _pageController.dispose();
    super.dispose();
  }

  void _startAutoSlide() {
    _timer = Timer.periodic(const Duration(seconds: 5), (timer) async {
      // Simplement aller à la page suivante, le % prendra soin de
      // la circularité
      if (_pageController.hasClients) {
        await _pageController.nextPage(
          duration: const Duration(milliseconds: 800),
          curve: Curves.easeInOut,
        );
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    final responsiveHelper = ResponsiveHelper(context);

    return Container(
      height: responsiveHelper.isDesktop() ? 180 : 130,
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(16),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withValues(alpha: 0.08),
            offset: const Offset(0, 5),
            blurRadius: 15,
          ),
        ],
      ),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(16),
        child: Stack(
          children: [
            // PageView.builder pour le défilement infini
            PageView.builder(
              controller: _pageController,
              onPageChanged: (index) {
                setState(() {
                  // Utiliser le modulo pour obtenir l'index
                  // réel dans notre liste
                  _currentPage = index % _bannerItems.length;
                });
              },
              itemBuilder: (context, index) {
                // Utiliser le modulo pour obtenir l'élément de la liste
                final itemIndex = index % _bannerItems.length;
                return _buildBannerItem(
                  _bannerItems[itemIndex],
                  responsiveHelper,
                );
              },
            ),

            // Indicateurs de page
            Positioned(
              bottom: 16,
              left: 0,
              right: 0,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: List.generate(
                  _bannerItems.length,
                      (index) => _buildDotIndicator(index == _currentPage),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildBannerItem(BannerData data, ResponsiveHelper responsiveHelper) {
    return Container(
      decoration: BoxDecoration(
        color: data.color,
      ),
      padding: const EdgeInsets.all(16),
      child: Row(
        children: [
          Expanded(
            flex: 2,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  data.title,
                  style: TextStyle(
                    fontSize: responsiveHelper.isDesktop() ? 24 : 18,
                    fontWeight: FontWeight.bold,
                    color: Colors.black87,
                  ),
                ),
                const SizedBox(height: 12),
                if (responsiveHelper.isDesktop()) ...[
                  ElevatedButton(
                    onPressed: () {},
                    style: ElevatedButton.styleFrom(
                      backgroundColor: AppColors.customRed,
                      padding: const EdgeInsets.symmetric(
                          horizontal: 20,
                          vertical: 12,
                      ),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10),
                      ),
                    ),
                    child: const Text(
                      'Faire un don maintenant',
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                ],
              ],
            ),
          ),
          Expanded(
            flex: responsiveHelper.isDesktop() ? 3 : 2,
            child: Image.asset(
              data.imagePath,
              fit: BoxFit.contain,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildDotIndicator(bool isActive) {
    return AnimatedContainer(
      duration: const Duration(milliseconds: 150),
      margin: const EdgeInsets.symmetric(horizontal: 4),
      height: 8,
      width: isActive ? 24 : 8,
      decoration: BoxDecoration(
        color: isActive ? AppColors.customRed : const Color(0xFFD8D8D8),
        borderRadius: BorderRadius.circular(4),
      ),
    );
  }
}

///Banner data model
class BannerData {
  ///Title
  final String title;
  /// image path
  final String imagePath;
  /// Color
  final Color color;

  /// Constructor
  BannerData({
    required this.title,
    required this.imagePath,
    required this.color,
  });
}
