/// Class to represent a chat message
class ChatMessage {
  /// The text of the message
  final String text;
  /// Whether the message is from the user or the bot
  final bool isUser;
  /// The timestamp of the message
  final DateTime timestamp;

  /// Constructor for ChatMessage
  ChatMessage({
    required this.text,
    required this.isUser,
    DateTime? timestamp,
  }) : timestamp = timestamp ?? DateTime.now();

  /// Create a user message
  factory ChatMessage.fromUser(String text) {
    return ChatMessage(
      text: text,
      isUser: true,
    );
  }

  /// Create a bot message
  factory ChatMessage.fromBot(String text) {
    return ChatMessage(
      text: text,
      isUser: false,
    );
  }
}
