import 'package:flutter/material.dart';
import 'package:lifelink/data/services/api_service.dart';
import 'package:lifelink/domain/models/chat_message.dart';
import 'package:lifelink/ui/chatbot/widgets/chat_input.dart';
import 'package:lifelink/ui/chatbot/widgets/message_list.dart';
import 'package:lifelink/ui/chatbot/widgets/user_profile.dart';

///
class ChatScreen extends StatefulWidget {

  /// Constructeur
  const ChatScreen({super.key});

  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  final TextEditingController _controller = TextEditingController();
  final List<ChatMessage> _messages = [];
  final ScrollController _scrollController = ScrollController();
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    // Ajouter le message initial du bot
    _addBotMessage('Bonjour comment puis-je vous aider?');
  }

  void _scrollToBottom() {
    WidgetsBinding.instance.addPostFrameCallback((_) async {
      if (_scrollController.hasClients) {
        await _scrollController.animateTo(
          _scrollController.position.maxScrollExtent,
          duration: const Duration(milliseconds: 300),
          curve: Curves.easeOut,
        );
      }
    });
  }

  Future<void> _sendMessage(String text) async {
    if (text.trim().isEmpty) {
      return;
    }

    setState(() {
      _messages.add(ChatMessage(
        text: text,
        isUser: true,
      ));
      _isLoading = true;
    });

    _controller.clear();
    _scrollToBottom();

    // Appeler l'API
    try {
      final response = await ApiService.sendMessage(text);
      setState(() {
        _addBotMessage(response.response);
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _addBotMessage("Désolé, une erreur s'est produite. "
            'Veuillez réessayer plus tard.');
        _isLoading = false;
      });
    }

    _scrollToBottom();
  }

  void _addBotMessage(String message) {
    _messages.add(ChatMessage(
      text: message,
      isUser: false,
    ));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 1,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () {
            // Gérer le bouton retour
            Navigator.maybePop(context);
          },
        ),
        title: const UserProfileWidget(name: 'Amina', imageUrl: "assets/images/chatbot.png"),
        centerTitle: true,
      ),
      body: Column(
        children: [
          Expanded(
            child: ChatMessageList(
              messages: _messages,
              scrollController: _scrollController,
              isLoading: _isLoading,
            ),
          ),
          ChatInputWidget(
            controller: _controller,
            onSend: _sendMessage,
          ),
        ],
      ),
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    _scrollController.dispose();
    super.dispose();
  }
}
