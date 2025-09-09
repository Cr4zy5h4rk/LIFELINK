// lib/ui/post/widgets/posts_screen.dart

import 'package:flutter/material.dart';
import 'package:lifelink/config/services_urls.dart';
import 'package:lifelink/data/repositories/post_repository.dart';
import 'package:lifelink/data/services/springboot_backend_service.dart';
import 'package:lifelink/ui/More/more.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/posts/view_model/post_view_model.dart';
import 'package:lifelink/ui/posts/widgets/featured_post_Item.dart';
import 'package:lifelink/ui/posts/widgets/post_header_posts_screen.dart';
import 'package:lifelink/ui/posts/widgets/post_item.dart';
import 'package:lifelink/ui/posts/widgets/posts_search_bar.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/responsive_helper.dart';
import 'package:provider/provider.dart';

/// Ecran des posts
class PostsScreen extends StatefulWidget {
  ///constructor
  const PostsScreen({super.key});

  @override
  State<PostsScreen> createState() => _PostsScreenState();
}

class _PostsScreenState extends State<PostsScreen> {
  late PostViewModel _viewModel;
  final TextEditingController _searchController = TextEditingController();
  final ScrollController _scrollController = ScrollController();

  bool _isLoadingMore = false;
  final int _postsPerPage = 5; // Nombre de posts à charger par page

  @override
  void initState() {
    super.initState();
    _viewModel = PostViewModel(
      PostRepository(
        apiService: SpringbootBackendService(
          baseUrl: ServicesUrls.springBootUrl,
        ),
      ),
    );

    // Initialiser le chargement des premiers posts
    _loadInitialPosts();

    // Ajouter un listener pour détecter quand l'utilisateur
    // atteint la fin de la liste
    _scrollController.addListener(_onScroll);
  }

  @override
  void dispose() {
    _searchController.dispose();
    _scrollController..removeListener(_onScroll)
    ..dispose();
    super.dispose();
  }

  // Méthode pour charger la première page de posts
  Future<void> _loadInitialPosts() async {
    await _viewModel.loadPaginatedPosts(limit: _postsPerPage);
  }

  // Méthode pour charger plus de posts lorsque l'utilisateur fait défiler
  Future<void> _loadMorePosts() async {
    if (_isLoadingMore || _viewModel.hasReachedEnd) {
      return;
    }

    setState(() {
      _isLoadingMore = true;
    });

    await _viewModel.loadMorePosts(limit: _postsPerPage);

    setState(() {
      _isLoadingMore = false;
    });
  }

  // Méthode appelée lorsque l'utilisateur fait défiler la liste
  Future<void> _onScroll() async {
    if (_scrollController.position.pixels >=
        _scrollController.position.maxScrollExtent - 200) {
      // Quand l'utilisateur est à moins de 200 pixels de la fin,
      // charger plus de posts
      await _loadMorePosts();
    }
  }


  @override
  Widget build(BuildContext context) {
    final responsive = ResponsiveHelper(context);
    final navigationProvider = Provider.of<NavigationProvider>(context);

    return ChangeNotifierProvider.value(
      value: _viewModel,
      child: Scaffold(
        backgroundColor: Colors.white,
        body: SafeArea(
          bottom: false,
          child: Consumer<PostViewModel>(
            builder: (context, viewModel, child) {
              return Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  PostsScreenHeader(
                    responsive: responsive,
                  ),
                  PostsSearchBar(
                    responsive: responsive,
                    searchController: _searchController,
                  ),
                  Expanded(
                    child: _buildPostsList(context, responsive, viewModel),
                  ),
                  CustomBottomNavBar(
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
                ],
              );
            },
          ),
        ),
      ),
    );
  }


  Widget _buildPostsList(BuildContext context, ResponsiveHelper responsive,
      PostViewModel viewModel,) {
    // Afficher un squelette de chargement si c'est le premier
    // chargement et qu'il n'y a pas encore de posts
    if (viewModel.isLoading && viewModel.posts.isEmpty) {
      return _buildPostsListSkeleton(responsive);
    }

    if (viewModel.posts.isEmpty) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.article_outlined,
              size: 80,
              color: Colors.grey[400],
            ),
            const SizedBox(height: 16),
            Text(
              'Aucun post disponible',
              style: TextStyle(
                fontSize: responsive.getAdaptiveFontSize(18),
                color: Colors.grey[600],
              ),
            ),
          ],
        ),
      );
    }

    return RefreshIndicator(
      onRefresh: _loadInitialPosts,
      child: ListView.builder(
        controller: _scrollController,
        padding: responsive.getAdaptivePadding(horizontal: 16, vertical: 8),
        itemCount: viewModel.posts.length + 1,
        // +1 pour l'indicateur de chargement
        itemBuilder: (context, index) {
          // Si on est à la fin de la liste,
          // afficher un indicateur de chargement
          if (index == viewModel.posts.length) {
            return _buildLoadMoreIndicator(viewModel);
          }

          final post = viewModel.posts[index];

          // Le premier post est mis en avant
          if (index == 0) {
            return FeaturedPostItem(
              post: post,
              responsive: responsive,
            );
          }

          // Les autres posts sont affichés dans un format standard
          return PostItem(
            post: post,
            responsive: responsive,
          );
        },
      ),
    );
  }


  Widget _buildLoadMoreIndicator(PostViewModel viewModel) {
    // Ne pas afficher d'indicateur si on a atteint la fin des posts
    if (viewModel.hasReachedEnd) {
      return Padding(
        padding: const EdgeInsets.symmetric(vertical: 16),
        child: Center(
          child: Text(
            'Fin des posts',
            style: TextStyle(
              color: Colors.grey[600],
              fontSize: 14,
            ),
          ),
        ),
      );
    }

    // Afficher un indicateur de chargement
    return const Padding(
      padding: EdgeInsets.symmetric(vertical: 16),
      child: Center(
        child: SizedBox(
          width: 24,
          height: 24,
          child: CircularProgressIndicator(
            strokeWidth: 2,
            valueColor: AlwaysStoppedAnimation<Color>(AppColors.customRed),
          ),
        ),
      ),
    );
  }


  Widget _buildPostsListSkeleton(ResponsiveHelper responsive) {
    return ListView.builder(
      padding: responsive.getAdaptivePadding(horizontal: 16, vertical: 8),
      itemCount: 4,
      itemBuilder: (context, index) {
        // Premier élément avec une bannière plus grande
        if (index == 0) {
          return Container(
            margin: const EdgeInsets.only(bottom: 16),
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(16),
            ),
            child: Column(
              children: [
                Container(
                  height: 160,
                  width: double.infinity,
                  decoration: BoxDecoration(
                    color: Colors.grey[300],
                    borderRadius: const BorderRadius.only(
                      topLeft: Radius.circular(16),
                      topRight: Radius.circular(16),
                    ),
                  ),
                ),
              ],
            ),
          );
        }

        // Éléments standard pour le reste
        return Container(
          margin: const EdgeInsets.only(bottom: 16),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(16),
          ),
          child: Row(
            children: [
              Container(
                width: 100,
                height: 100,
                decoration: BoxDecoration(
                  color: Colors.grey[300],
                  borderRadius: const BorderRadius.only(
                    topLeft: Radius.circular(16),
                    bottomLeft: Radius.circular(16),
                  ),
                ),
              ),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.all(12),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Container(
                        width: double.infinity,
                        height: 16,
                        color: Colors.grey[300],
                      ),
                      const SizedBox(height: 8),
                      Container(
                        width: 100,
                        height: 12,
                        color: Colors.grey[300],
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        );
      },
    );
  }

}
