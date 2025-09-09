/// Class to represent API response
class ChatResponse {
  /// The response text
  final String response;
  /// Whether the response is an error
  final bool isError;
  /// The error message if the response is an error
  final String? errorMessage;

  /// Constructor for ChatResponse
  ChatResponse({
    required this.response,
    this.isError = false,
    this.errorMessage,
  });

  /// Factory method to create a ChatResponse from a JSON
  factory ChatResponse.fromJson(Map<String, dynamic> json) {
    if (json.containsKey('response')) {
      return ChatResponse(
        response: json['response'] as String,
      );
    } else if (json.containsKey('erreur')) {
      return ChatResponse(
        response: json['erreur'] as String,
        isError: true,
        errorMessage: json['erreur'] as String,
      );
    } else {
      return ChatResponse(
        response: 'Une erreur est survenue',
        isError: true,
        errorMessage: 'Format de réponse inattendu',
      );
    }
  }

  /// Factory method to create an error response
  factory ChatResponse.error(String errorMessage) {
    return ChatResponse(
      response: "Désolé, une erreur s'est produite",
      isError: true,
      errorMessage: errorMessage,
    );
  }
}
