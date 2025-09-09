import 'package:flutter/material.dart';
import 'package:lifelink/ui/campagnes/campagnes.dart';
import 'package:lifelink/ui/chatbot/chat_screen.dart';
import 'package:lifelink/ui/emergencies/emergency_map_screen.dart';
import 'package:lifelink/ui/partener/partener_screen.dart';
import 'package:lifelink/ui/profil/profil.dart';
import 'package:lifelink/ui/shareInfo/share_request_info_screen.dart';
import 'package:lifelink/ui/statistiques/statistiques.dart';
import 'package:lifelink/utils/app_colors.dart';

class MenuItem extends StatelessWidget {
  final String imagePath;
  final String title;
  final Function()? onTap;

  const MenuItem({
    super.key,
    required this.imagePath,
    required this.title,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withValues(alpha: 0.1),
            spreadRadius: 1,
            blurRadius: 2,
            offset: const Offset(0, 1),
          ),
        ],
      ),
      child: ListTile(
        contentPadding: const EdgeInsets.symmetric(horizontal: 16, vertical: 8),
        onTap: () async {
          switch (title) {
            case 'Urgences':
              await Navigator.push(
                context,
                MaterialPageRoute<dynamic>(
                  builder: (context) => const EmergencyMapScreen(),
          )
              );
              break;
            case 'Statistiques':
              await Navigator.push(
                  context,
                  MaterialPageRoute<dynamic>(
                    builder: (context) =>  Statistiques(),
                  )
              );
              break;
            case 'Centres de collectes':
              // Pas encore de page à afficher
              break;
            case 'Campagnes de collecte':
              await Navigator.push(
                  context,
                  MaterialPageRoute<dynamic>(
                    builder: (context) =>  const Campagnes(),
                  )
              );
              break;
            case 'Profil & statistiques':
              await Navigator.push(
                  context,
                  MaterialPageRoute<dynamic>(
                    builder: (context) =>  const Profil(),
                  )
              );
              break;
            case 'Partenaires':
              await Navigator.push(
                  context,
                  MaterialPageRoute<dynamic>(
                    builder: (context) =>  const PartnersScreen(),
                  )
              );
              break;
            case 'Amina - chatbot':
              await Navigator.push(
                  context,
                  MaterialPageRoute<dynamic>(
                    builder: (context) =>  const ChatScreen(),
                  )
              );
              break;
            case "Demande de partage d'informations":
              await Navigator.push(
                  context,
                  MaterialPageRoute<dynamic>(
                    builder: (context) =>  ShareInfoRequestsScreen(),
                  )
              );
              break;
            case 'Voir les articles favoris':
              // Pas encore de page à afficher
              break;
            default:
              // logique de deconnexion
          }
        },
        leading: _buildLeadingIcon(),
        title: Text(
          title,
          style: const TextStyle(
            fontSize: 16,
            fontWeight: FontWeight.w500,
          ),
        ),
        trailing: const Icon(
          Icons.arrow_forward_ios,
          size: 16,
          color: Colors.grey,
        ),
      ),
    );
  }

  Widget _buildLeadingIcon() {
    if (imagePath.startsWith('Icons.')) {
      final iconName = imagePath.substring(6);

      IconData iconData;
      switch (iconName) {
        case 'warning':
          iconData = Icons.warning;
          break;
        case 'handshake':
          iconData = Icons.handshake;
          break;
        case 'share':
          iconData = Icons.share;
          break;
        case 'favorite':
          iconData = Icons.favorite;
          break;
        case 'logout':
          iconData = Icons.logout;
          break;
        default:
          iconData = Icons.circle; // Icône par défaut
      }

      return Icon(
        iconData,
        color: AppColors.customRed,
        size: 28,
      );
    } else {
      return Image.asset(
        imagePath,
        width: 28,
        height: 28,
        errorBuilder: (context, error, stackTrace) {
          final fallbackIcon = title == 'Urgences' ? Icons.warning :
          title == 'Statistiques' ? Icons.bar_chart :
          title == 'Centres de collectes' ? Icons.medical_services :
          title == 'Campagnes de collecte' ? Icons.campaign :
          title == 'Profil & statistiques' ? Icons.person :
          title == 'Partenaires' ? Icons.handshake :
          title == 'Amina - chatbot' ? Icons.chat_bubble :
          title == "Demande de partage d'informations" ? Icons.share :
          title == 'Voir les articles favoris' ? Icons.favorite :
          Icons.logout;

          return Icon(fallbackIcon, color: AppColors.customRed, size: 28);
        },
      );
    }
  }
}
