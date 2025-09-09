// lib/domain/models/post.dart

/// Post model
class Post {
  /// Title of the post
  final String title;
  /// Content of the post
  final String content;
  /// Image url of the post
  final String imageUrl;
  /// Publish date of the post
  final DateTime publishDate;
  /// Author of the post
  final String author;
  /// ID of the post (optional)
  final String? id;
  /// Audio language (optional)
  final String? audioLanguage;
  /// Whether the post has audio (optional)
  final bool hasAudio;
  /// Whether the post is favorited (optional)
  final bool isFavorite;
  /// Author image URL (optional)
  final String? authorImageUrl;

  /// Constructor
  Post({
    required this.title,
    required this.content,
    required this.imageUrl,
    required this.publishDate,
    required this.author,
    this.id,
    this.audioLanguage,
    this.hasAudio = false,
    this.isFavorite = false,
    this.authorImageUrl,
  });

  ///Convert JSON object to Post
  factory Post.fromJson(Map<String, dynamic> json) {
    return Post(
      title: json['title']?.toString() ?? '',
      content: json['content']?.toString() ?? '',
      imageUrl: json['imageUrl']?.toString() ?? '',
      publishDate: json['publishDate'] != null
          ? DateTime.parse(json['publishDate'].toString())
          : DateTime.now(),
      author: json['author']?.toString() ?? '',
      id: json['id']?.toString(),
      audioLanguage: json['audioLanguage']?.toString(),
      hasAudio: json['hasAudio'] == true,
      isFavorite: json['isFavorite'] == true,
      authorImageUrl: json['authorImageUrl']?.toString(),
    );
  }

  /// Convert Post to Json
  Map<String, dynamic> toJson() {
    return {
      'title': title,
      'content': content,
      'imageUrl': imageUrl,
      'publishDate': publishDate.toIso8601String(),
      'author': author,
      'id': id,
      'audioLanguage': audioLanguage,
      'hasAudio': hasAudio,
      'isFavorite': isFavorite,
      'authorImageUrl': authorImageUrl,
    };
  }

  /// Get time ago string based on publish date
  String get timeAgo {
    final now = DateTime.now();
    final difference = now.difference(publishDate);

    if (difference.inDays > 365) {
      return 'il y a ${(difference.inDays / 365).floor()} an(s)';
    } else if (difference.inDays > 30) {
      return 'il y a ${(difference.inDays / 30).floor()} mois';
    } else if (difference.inDays > 0) {
      return 'il y a ${difference.inDays} jour(s)';
    } else if (difference.inHours > 0) {
      return 'il y a ${difference.inHours}h';
    } else if (difference.inMinutes > 0) {
      return 'il y a ${difference.inMinutes} min';
    } else {
      return "à l'instant";
    }
  }

  /// Create a copy of this Post with given fields replaced with the new values
  Post copyWith({
    String? title,
    String? content,
    String? imageUrl,
    DateTime? publishDate,
    String? author,
    String? id,
    String? audioLanguage,
    bool? hasAudio,
    bool? isFavorite,
    String? authorImageUrl,
  }) {
    return Post(
      title: title ?? this.title,
      content: content ?? this.content,
      imageUrl: imageUrl ?? this.imageUrl,
      publishDate: publishDate ?? this.publishDate,
      author: author ?? this.author,
      id: id ?? this.id,
      audioLanguage: audioLanguage ?? this.audioLanguage,
      hasAudio: hasAudio ?? this.hasAudio,
      isFavorite: isFavorite ?? this.isFavorite,
      authorImageUrl: authorImageUrl ?? this.authorImageUrl,
    );
  }
}
