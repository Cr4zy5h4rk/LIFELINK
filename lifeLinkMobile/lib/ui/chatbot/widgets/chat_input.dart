import 'package:flutter/material.dart';
import 'package:lifelink/ui/chatbot/app_theme.dart';

/// Classe pour l'entrée du chatbot
class ChatInputWidget extends StatelessWidget {
  /// Contrôleur du champ de texte
  final TextEditingController controller;
  final Function(String) onSend;

  /// Constructeur
  const ChatInputWidget({
    required this.controller, required this.onSend, super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 8),
      decoration: const BoxDecoration(
        color: Colors.white,
        boxShadow: [
          BoxShadow(
            color: Colors.black12,
            offset: Offset(0, -1),
            blurRadius: 4,
          ),
        ],
      ),
      child: Padding(
          padding: const EdgeInsets.fromLTRB(16, 8, 16, 20),
          child: Row(
            children: [
              IconButton(
                icon: const Icon(Icons.mic, color: AppTheme.primaryColor),
                onPressed: () {
                  // Gérer l'entrée vocale ici
                },
              ),
              Expanded(
                child: TextField(
                  controller: controller,
                  decoration: AppTheme.chatInputDecoration,
                  textCapitalization: TextCapitalization.sentences,
                  onSubmitted: (text) {
                    if (text.trim().isNotEmpty) {
                      onSend(text);
                    }
                  },
                ),
              ),
              IconButton(
                icon: const Icon(Icons.send, color: AppTheme.primaryColor),
                onPressed: () {
                  if (controller.text.trim().isNotEmpty) {
                    onSend(controller.text);
                  }
                },
              ),
            ],
          ),
      ),
    );
  }
}
