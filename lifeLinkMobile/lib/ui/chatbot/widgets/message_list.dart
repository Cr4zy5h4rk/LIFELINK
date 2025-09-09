import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/chat_message.dart';
import 'package:lifelink/ui/chatbot/app_theme.dart';
import 'package:lifelink/ui/chatbot/widgets/message_bubble.dart';

/// Classe pour afficher la liste des messages
class ChatMessageList extends StatelessWidget {
  final List<ChatMessage> messages;
  final ScrollController scrollController;
  final bool isLoading;

  /// Constructeur
  const ChatMessageList({
    required this.messages,
    required this.scrollController,
    required this.isLoading,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      controller: scrollController,
      padding: const EdgeInsets.all(16),
      itemCount: messages.length + (isLoading ? 1 : 0),
      itemBuilder: (context, index) {
        if (index == messages.length && isLoading) {
          // Afficher l'indicateur de chargement
          return Align(
            alignment: Alignment.centerLeft,
            child: Container(
              margin: const EdgeInsets.symmetric(vertical: 8),
              padding: const EdgeInsets.all(12),
              decoration: AppTheme.botMessageDecoration,
              child: const SizedBox(
                width: 16,
                height: 16,
                child: CircularProgressIndicator(
                  strokeWidth: 2,
                  valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                ),
              ),
            ),
          );
        }
        return MessageBubble(message: messages[index]);
      },
    );
  }
}
