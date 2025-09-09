import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/campaign.dart';
import 'package:lifelink/domain/models/emergency.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/ui/More/more.dart';
import 'package:lifelink/ui/chatbot/chat_screen.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/home/view_model/home_view_model.dart';
import 'package:lifelink/ui/home/widgets/blood_campaigns.dart';
import 'package:lifelink/ui/home/widgets/blood_emergency_card.dart';
import 'package:lifelink/ui/home/widgets/blood_groups.dart';
import 'package:lifelink/ui/home/widgets/header.dart';
import 'package:lifelink/ui/home/widgets/posts.dart';
import 'package:lifelink/ui/home/widgets/sliding_banner.dart';
import 'package:lifelink/ui/home/widgets/statistics.dart';
import 'package:lifelink/ui/posts/posts_screen.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/auth_service.dart';
import 'package:lifelink/utils/responsive_helper.dart';
import 'package:provider/provider.dart';
import 'package:shimmer/shimmer.dart';

/// Home widget
class HomeScreen extends StatefulWidget {
  ///constructor
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final HomeViewModel _viewModel = HomeViewModel();
  final ScrollController _scrollController = ScrollController();
  final AuthService _authService = AuthService();
  Map<String, dynamic>? userData;

  List<Emergency> _emergencies = <Emergency>[];
  List<Campaign> _campaigns = <Campaign>[];
  List<Post> _testimonies = <Post>[];
  List<Post> _recentPosts = <Post>[];


  bool _isLoading = true;
  String _errorMessage = '';

  @override
  void initState() {
    super.initState();
    _loadData();
  }

  Future<void> _loadData() async {
    setState(() {
      _isLoading = true;
      _errorMessage = '';
    });

    try {
      await _viewModel.loadHomeData();
      userData = await _authService.getUserInfo();

      setState(() {
        _emergencies = _viewModel.emergencies.cast<Emergency>();
        _campaigns = _viewModel.campaigns.cast<Campaign>();
        _testimonies = _viewModel.testimonies.cast<Post>();
        _recentPosts = _viewModel.recentPosts.cast<Post>();
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _isLoading = false;
        _errorMessage = 'Erreur lors du chargement des données: $e';
      });
    }
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final responsiveHelper = ResponsiveHelper(context);
    final navigationProvider = Provider.of<NavigationProvider>(context);

    return Scaffold(
      backgroundColor: const Color(0xFFFFF8F3),
      appBar: CustomAppBar(
        bloodtype: userData?['resusType'] == 'POSITIVE' ?
        "${userData?['bloodType'] ?? '+'}+":
        "${userData?['bloodType'] ?? '-'}-" ,
        userName:
        (userData?['firstName'] != null || userData?['lastName'] != null)?
        '${userData?['firstName'] ?? ''} ${userData?['lastName'] ?? ''}':
        'Unkown user',
        userImagePath: userData?['picture'] != null ?
        '${userData?["picture"]}' : 'assets/images/avatar.webp',
      ),
      body: _buildBody(responsiveHelper),
      bottomNavigationBar: CustomBottomNavBar(
        currentIndex: navigationProvider.currentIndex,
        onTap: (index) async {
          navigationProvider.setIndex(index);
          if(index == 1){
            await Navigator.push(
              context,
              MaterialPageRoute<dynamic>(
                builder: (context) => const PostsScreen(),
              ),
            );
            navigationProvider.setIndex(0);
          }else if(index == 2){
            await Navigator.push(
              context,
              MaterialPageRoute<dynamic>(
                builder: (context) => const MoreScreen(),
              ),
            );
            navigationProvider.setIndex(0);
          }
        },
      ),
      floatingActionButton: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8,vertical: 20) ,
          child: FloatingActionButton(
            onPressed: () async {
              await Navigator.push(
                context,
                MaterialPageRoute<dynamic>(
                  builder: (context) => const ChatScreen(),
                ),
              );
            },
            child: Image.asset('assets/images/chatbot.png'),
          ),
      )
    );
  }

  Widget _buildBody(ResponsiveHelper responsiveHelper) {

    if (_isLoading) {
      return _buildShimmerLoading(responsiveHelper);
    }

    if (_errorMessage.isNotEmpty) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text('Erreur: $_errorMessage'),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: _loadData,
              child: const Text('Réessayer'),
            ),
          ],
        ),
      );
    }

    if (_viewModel.statistics == null) {
      return RefreshIndicator(
        onRefresh: _loadData,
        child: SingleChildScrollView(
          physics: const AlwaysScrollableScrollPhysics(),
          child: SizedBox(
            // Définir une hauteur minimale pour permettre le défilement
            height: MediaQuery.of(context).size.height - 100, // Soustraire la hauteur approximative de l'AppBar et BottomNavBar
            child: const Center(
              child: Text(
                'Aucune donnée disponible',
                style: TextStyle(
                  color: AppColors.customRed,
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ),
          ),
        ),
      );
    }

    return RefreshIndicator(
      onRefresh: _loadData,
      child: SingleChildScrollView(
        controller: _scrollController,
        physics: const AlwaysScrollableScrollPhysics(),
        child: Center(
          child: ConstrainedBox(
            constraints: BoxConstraints(
              maxWidth: responsiveHelper.isDesktop() ? 1200 : 600,
            ),
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const SizedBox(height: 24),
                  const SlidingBanner(),
                  const SizedBox(height: 24),
                  BloodGroupsWidget(responsiveHelper: responsiveHelper),
                  const SizedBox(height: 24),
                  BloodEmergencyCard(emergencies: _emergencies),
                  const SizedBox(height: 24),
                  BloodCampaignsWidget(campaigns: _campaigns),
                  const SizedBox(height: 24),
                  PostsWidget(title: 'Témoignages',posts: _testimonies),
                  const SizedBox(height: 24),
                  StatisticsWidget(
                    numDonors:
                    '${_viewModel.statistics!.donorsCount ~/ 1000}K',
                    percentageAchieved:
                    '${_viewModel.statistics!.objectivePercentage.
                    toStringAsFixed(2)}%',
                    livesSaved:
                    '${_viewModel.statistics!.livesSaved ~/ 1000}K',
                  ),
                  const SizedBox(height: 24),
                  PostsWidget(title:'Articles récents',posts: _recentPosts),
                  const SizedBox(height: 60),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }


  Widget _buildShimmerLoading(ResponsiveHelper responsiveHelper) {
    return SingleChildScrollView(
      physics: const NeverScrollableScrollPhysics(),
      child: Center(
        child: ConstrainedBox(
          constraints: BoxConstraints(
            maxWidth: responsiveHelper.isDesktop() ? 1200 : 600,
          ),
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: Shimmer.fromColors(
              baseColor: Colors.grey[300]!,
              highlightColor: Colors.grey[100]!,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const SizedBox(height: 24),
                  // Shimmer pour SlidingBanner
                  Container(
                    height: 160,
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(12),
                    ),
                  ),
                  const SizedBox(height: 24),
                  // Shimmer pour BloodGroupsWidget
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: List.generate(
                      4,
                          (index) => Container(
                        width: (responsiveHelper.width - 56) / 4,
                        height: 100,
                        decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(8),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(height: 24),
                  // Shimmer pour BloodEmergencyCard
                  Container(
                    height: 180,
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(12),
                    ),
                  ),
                  const SizedBox(height: 24),
                  // Shimmer pour BloodCampaignsWidget
                  Column(
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Container(
                            width: 180,
                            height: 24,
                            color: Colors.white,
                          ),
                          Container(
                            width: 80,
                            height: 18,
                            color: Colors.white,
                          ),
                        ],
                      ),
                      const SizedBox(height: 12),
                      SizedBox(
                        height: 220,
                        child: ListView.builder(
                          scrollDirection: Axis.horizontal,
                          physics: const NeverScrollableScrollPhysics(),
                          itemCount: 3,
                          itemBuilder: (context, index) {
                            return Padding(
                              padding: const EdgeInsets.only(right: 16),
                              child: Container(
                                width: 280,
                                decoration: BoxDecoration(
                                  color: Colors.white,
                                  borderRadius: BorderRadius.circular(12),
                                ),
                              ),
                            );
                          },
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 24),
                  // Shimmer pour PostsWidget (testimonies)
                  _buildShimmerPosts(),
                  const SizedBox(height: 24),
                  // Shimmer pour StatisticsWidget
                  Container(
                    height: 120,
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(12),
                    ),
                  ),
                  const SizedBox(height: 24),
                  // Shimmer pour PostsWidget (recentPosts)
                  _buildShimmerPosts(),
                  const SizedBox(height: 60),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildShimmerPosts() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Container(
          width: 180,
          height: 24,
          color: Colors.white,
        ),
        const SizedBox(height: 12),
        ListView.builder(
          shrinkWrap: true,
          physics: const NeverScrollableScrollPhysics(),
          itemCount: 2,
          itemBuilder: (context, index) {
            return Padding(
              padding: const EdgeInsets.only(bottom: 12),
              child: Container(
                height: 140,
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(12),
                ),
              ),
            );
          },
        ),
      ],
    );
  }
}
