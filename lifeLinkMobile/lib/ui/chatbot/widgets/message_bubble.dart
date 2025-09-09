import 'package:flutter/material.dart';
import 'package:lifelink/domain/models/chat_message.dart';
import 'package:lifelink/ui/chatbot/app_theme.dart';

/// Classe pour afficher un message
class MessageBubble extends StatelessWidget {
  final ChatMessage message;

  const MessageBubble({
    Key? key,
    required this.message,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: message.isUser ? Alignment.centerRight : Alignment.centerLeft,
      child: Container(
        margin: const EdgeInsets.symmetric(vertical: 8.0),
        padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 12.0),
        decoration: message.isUser
            ? AppTheme.userMessageDecoration
            : AppTheme.botMessageDecoration,
        constraints: BoxConstraints(
          maxWidth: MediaQuery.of(context).size.width * 0.7,
        ),
        child: Text(
          message.text,
          style: message.isUser
              ? AppTheme.userMessageStyle
              : AppTheme.botMessageStyle,
          textAlign: TextAlign.left,
          softWrap: true,
          locale: const Locale('fr', 'FR'),
        ),
      ),
    );
  }
}
