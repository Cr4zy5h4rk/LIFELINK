// lib/ui/post/widgets/post_detail_screen.dart

import 'package:flutter/material.dart';
import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/data/repositories/post_repository.dart';
import 'package:lifelink/data/services/springboot_backend_service.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/ui/More/more.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/posts/view_model/post_view_model.dart';
import 'package:lifelink/ui/posts/widgets/post_content.dart';
import 'package:lifelink/ui/posts/widgets/post_header_detail_screen.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/responsive_helper.dart';
import 'package:provider/provider.dart';


/// Post detail screen
class PostDetailScreen extends StatefulWidget {
  /// Post id
  final String postId;

  ///Constructor
  const PostDetailScreen({
    required this.postId, super.key,
  });

  @override
  State<PostDetailScreen> createState() => _PostDetailScreenState();
}

class _PostDetailScreenState extends State<PostDetailScreen> {
  late PostViewModel _viewModel;
  late PostRepository _repository;

  @override
  void initState(){
    super.initState();
    _repository = PostRepository(
      apiService: SpringbootBackendService(
        baseUrl: ServicesUrls.springBootUrl,
      ),
    );
    _viewModel = PostViewModel(_repository);

    // Charger les données du post
    _loadPostData();
  }

  Future<void> _loadPostData() async {
    await _viewModel.loadPost(widget.postId);
  }

  @override
  Widget build(BuildContext context) {
    final navigationProvider = Provider.of<NavigationProvider>(context);
    return ChangeNotifierProvider.value(
      value: _viewModel,
      child: Consumer<PostViewModel>(
        builder: (context, viewModel, child) {
          if (viewModel.isLoading) {
            return const Scaffold(
              body: Center(
                child: CircularProgressIndicator(),
              ),
            );
          }

          if (viewModel.errorMessage != null && viewModel.currentPost == null) {
            return Scaffold(
              body: SafeArea(
                child: Column(
                  children: [
                    // Header personnalisé avec bouton retour
                    Padding(
                      padding: const EdgeInsets.all(16),
                      child: Row(
                        children: [
                          GestureDetector(
                            onTap: () => Navigator.of(context).pop(),
                            child: Container(
                              padding: const EdgeInsets.all(8),
                              decoration: BoxDecoration(
                                color: AppColors.customLightRed,
                                borderRadius: BorderRadius.circular(50),
                              ),
                              child: const Icon(
                                Icons.arrow_back_ios_rounded,
                                color: Colors.white,
                                size: 20,
                              ),
                            ),
                          ),
                          const SizedBox(width: 20),
                          const Text(
                            'Détail du post',
                            style: TextStyle(
                              fontSize: 24,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ],
                      ),
                    ),
                    // Contenu principal avec message d'erreur
                    Expanded(
                      child: Center(
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            const Icon(
                              Icons.error_outline,
                              color: AppColors.customRed,
                              size: 60,
                            ),
                            const SizedBox(height: 16),
                            Container(
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 20,
                              ),
                              child: Text(
                                  viewModel.errorMessage!,
                                  textAlign: TextAlign.justify,
                              ),
                            ),
                            const SizedBox(height: 16),
                            ElevatedButton(
                              onPressed: _loadPostData,
                              child: const Text('Réessayer'),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            );
          }

          if (viewModel.currentPost == null) {
            return Scaffold(
              body: SafeArea(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    // Header personnalisé avec bouton retour
                    Padding(
                      padding: const EdgeInsets.all(16),
                      child: GestureDetector(
                        onTap: () => Navigator.of(context).pop(),
                        child: Container(
                          padding: const EdgeInsets.all(8),
                          decoration: BoxDecoration(
                            color: AppColors.customLightRed,
                            borderRadius: BorderRadius.circular(50),
                          ),
                          child: const Icon(
                            Icons.arrow_back_ios_rounded,
                            color: Colors.white,
                            size: 20,
                          ),
                        ),
                      ),
                    ),
                    // Corps avec message d'erreur
                    const Expanded(
                      child: Center(
                        child: Text(
                          'Post non trouvé',
                          style: TextStyle(
                            fontSize: 24,
                            fontWeight: FontWeight.bold,
                            color: AppColors.customRed,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            );
          }

          return _buildPostDetailsView(
              context,
              viewModel.currentPost!,
              navigationProvider,
          );
        },
      ),
    );
  }

  Widget _buildPostDetailsView(
      BuildContext context,
      Post post,
      NavigationProvider navigationProvider) {
    final responsive = ResponsiveHelper(context);

    return Scaffold(
      body: SafeArea(
        child: Column(
          children: [
            Expanded(
              child: SingleChildScrollView(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    PostHeader(
                      post: post,
                      responsive: responsive,
                      viewModel: _viewModel,
                    ),
                    PostContent(
                      post: post,
                      responsive: responsive,
                    ),
                  ],
                ),
              ),
            ),
          ],
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
            navigationProvider.setIndex(1);
          }else if(index == 2){
            await Navigator.push(
              context,
              MaterialPageRoute<dynamic>(
                builder: (context) => const MoreScreen(),
              ),
            );
            navigationProvider.setIndex(1);
          }
        },
      ),
    );
  }


}
