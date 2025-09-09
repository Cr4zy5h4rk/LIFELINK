import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:lifelink/data/repositories/share_info_repository.dart';
import 'package:lifelink/domain/models/user_shared_info.dart';
import 'package:lifelink/ui/core/providers/navigation_provider.dart';
import 'package:lifelink/ui/core/sharedWidgets/bottom_bar_navigation.dart';
import 'package:lifelink/ui/home/home_screen.dart';
import 'package:lifelink/ui/posts/posts_screen.dart';
import 'package:lifelink/ui/shareInfo/widgets/request_detail_screen.dart';
import 'package:lifelink/utils/app_colors.dart';
import 'package:lifelink/utils/auth_service.dart';
import 'package:provider/provider.dart';


class ShareInfoRequestsScreen extends StatefulWidget {
  @override
  _ShareInfoRequestsScreenState createState() => _ShareInfoRequestsScreenState();
}

class _ShareInfoRequestsScreenState extends State<ShareInfoRequestsScreen> {
  final AuthService _authService = AuthService();
  final ShareInfoRequestRepo _shareInfoRepo = ShareInfoRequestRepo();

  List<EnrichedShareInfoRequest> _pendingRequests = [];
  bool _isLoading = true;
  String? _errorMessage;
 Map<String, dynamic>? _userInfo;

  @override
  void initState() {
    super.initState();
    _loadUserInfoAndRequests();
  }

  Future<void> _loadUserInfoAndRequests() async {
    setState(() {
      _isLoading = true;
      _errorMessage = null;
    });

    try {
      // Récupérer les informations de l'utilisateur
      final userInfo = await _authService.getUserInfo();
      setState(() {
        _userInfo = userInfo;
      });

      // Charger les demandes pour cet utilisateur
      await _loadPendingRequests(userInfo?['phoneNumber'] as String);
    } catch (e) {
      setState(() {
        _errorMessage = 'Impossible de charger vos informations: ${e.toString()}';
        _isLoading = false;
      });
    }
  }

  Future<void> _loadPendingRequests(String phoneNumber) async {
    try {
      final requests = await _shareInfoRepo.getPendingRequestsByPhoneNumber(phoneNumber);
      setState(() {
        _pendingRequests = requests;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _errorMessage = 'Impossible de charger les demandes: ${e.toString()}';
        _isLoading = false;
      });
    }
  }

  void _navigateToDetailScreen(EnrichedShareInfoRequest request) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => RequestDetailScreen(
          request: request,
          onAccept: () => _acceptRequest(request.id!),
          onReject: () => _rejectRequest(request.id!),
        ),
      ),
    ).then((_) {
      // Recharger les demandes quand on revient
      if (_userInfo != null) {
        _loadPendingRequests(_userInfo?['phoneNumber'] as String);
      }
    });
  }

  Future<void> _acceptRequest(String requestId) async {
    try {
      await _shareInfoRepo.acceptRequest(requestId);
      if (_userInfo != null) {
        _loadPendingRequests(_userInfo?['phoneNumber'] as String);
      }

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Vous avez accepté de partager vos informations'),
          backgroundColor: Colors.green,
        ),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Erreur lors de l\'acceptation: ${e.toString()}'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  Future<void> _rejectRequest(String requestId) async {
    try {
      await _shareInfoRepo.rejectRequest(requestId);
      if (_userInfo != null) {
        _loadPendingRequests(_userInfo?['phoneNumber'] as String);
      }

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Vous avez refusé de partager vos informations'),
          backgroundColor: Colors.orange,
        ),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Erreur lors du refus: ${e.toString()}'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    final navigationProvider = Provider.of<NavigationProvider>(context);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
        title: const Row(
          children: [
            Icon(
                Icons.share,
                size: 24,
                color: AppColors.customLightRed,
            ),
            SizedBox(width: 16),
            Text('Demandes de partage'),
          ],
        ),
        bottom: PreferredSize(
          preferredSize: const Size.fromHeight(8),
          child: Container(),
        ),
      ),
      body: _buildBody(),
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

  Widget _buildBody() {
    if (_isLoading) {
      return const Center(
        child: CircularProgressIndicator(
          color: AppColors.customRed,
        ),
      );
    }

    if (_errorMessage != null) {
      return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.error_outline, size: 64, color: Colors.red),
            const SizedBox(height: 16),
            Text(
              _errorMessage!,
              textAlign: TextAlign.center,
              style: const TextStyle(color: Colors.red),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: _loadUserInfoAndRequests,
              style: ElevatedButton.styleFrom(
                backgroundColor: AppColors.customLightRed,
                foregroundColor: Colors.white,
              ),
              child: const Text('Réessayer'),
            ),
          ],
        ),
      );
    }

    if (_pendingRequests.isEmpty) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.check_circle, size: 64, color: Colors.green),
            SizedBox(height: 16),
            Text(
              'Aucune demande de partage en attente',
              style: TextStyle(fontSize: 18),
            ),
          ],
        ),
      );
    }

    return ListView.builder(
      itemCount: _pendingRequests.length,
      itemBuilder: (context, index) {
        final request = _pendingRequests[index];
        return _buildRequestCard(request);
      },
    );
  }

  Widget _buildRequestCard(EnrichedShareInfoRequest request) {
    final formatter = DateFormat('dd/MM/yyyy');

    return Card(
      color: Colors.white,
      margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
      child: InkWell(
        onTap: () => _navigateToDetailScreen(request),
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  CircleAvatar(
                    radius: 24,
                    backgroundColor: AppColors.customLightRed,
                    child: Icon(
                      request.campaignId != null ? Icons.campaign : Icons.local_hospital,
                      color: Colors.white,
                    ),
                  ),
                  const SizedBox(width: 16),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          request.getSourceName(),
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 16,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(
                          'Demande reçue le ${formatter.format(request.requestDate ?? DateTime.now())}',
                          style: TextStyle(
                            color: Colors.grey[600],
                            fontSize: 14,
                          ),
                        ),
                        const SizedBox(height: 8),
                        Text(
                          request.getDetailsText(),
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                          style: const TextStyle(fontSize: 14),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 16),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    request.getLocationText(),
                    style: TextStyle(
                      fontSize: 14,
                      color: Colors.grey[700],
                    ),
                  ),
                  Icon(
                    Icons.arrow_forward_ios,
                    size: 16,
                    color: Colors.grey[400],
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
