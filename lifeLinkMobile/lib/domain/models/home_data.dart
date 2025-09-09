import 'package:lifelink/domain/models/campaign.dart';
import 'package:lifelink/domain/models/emergency.dart';
import 'package:lifelink/domain/models/post.dart';
import 'package:lifelink/domain/models/statistics.dart';

/// Classe pour contenir toutes les données de l'écran d'accueil
class HomeData {
  ///emergencies
  final List<Emergency> emergencies;
  /// campaigns
  final List<Campaign> campaigns;
  /// testimonies
  final List<Post> testimonies;
  /// statistics
  final Statistics statistics;
  /// recent posts
  final List<Post> recentPosts;

  ///Constructor
  HomeData({
    required this.emergencies,
    required this.campaigns,
    required this.testimonies,
    required this.statistics,
    required this.recentPosts,
  });
}
