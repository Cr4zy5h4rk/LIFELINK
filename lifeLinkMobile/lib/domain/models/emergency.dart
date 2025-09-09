
/// Emergency model
class Emergency {
  /// Unique identifier of the emergency
  final String id;
  /// Title of the emergency
  final String title;
  /// Hospital where the emergency is taking place
  final String hospital;
  /// Date of the emergency
  final DateTime date;
  /// Blood type of the emergency
  final String bloodType;
  /// URL of the Wolof audio announcement
  final String wolofAudioUrls;
  /// Latitude of the emergency location
  final double? latitude;
  /// Longitude of the emergency location
  final double? longitude;


  /// Constructor
  Emergency({
    required this.id,
    required this.title,
    required this.hospital,
    required this.date,
    required this.bloodType,
    required this.wolofAudioUrls,
    this.latitude,
    this.longitude,
  });

  /// Convert a JSON object to an emergency
  factory Emergency.fromJson(Map<String, dynamic> json) {
    return Emergency(
      id: json['id']?.toString() ?? '',
      title: json['title']?.toString() ?? '',
      hospital: json['hospital']?.toString() ?? 'Hôpital inconnu',
      date: json['date'] != null
          ? DateTime.parse(json['date'].toString())
          : DateTime.now(),
      bloodType: _formatBloodType(json['bloodType'], json['resusType']),
      wolofAudioUrls: json['wolofAudioUrls']?.toString() ?? '',
      // Récupérer les coordonnées depuis l'adresse du centre de don
      latitude: json['latitude'] != null ?
      double.tryParse(json['latitude'].toString()) : null,
      longitude: json['longitude'] != null ?
      double.tryParse(json['longitude'].toString()) : null,
    );
  }

  /// Convert the emergency to a JSON object
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'details': title,
      'createdAt': date.toIso8601String(),
      // Extraire le type sanguin et le type rhésus
      'bloodType': _extractBloodType(bloodType),
      'resusType': _extractResusType(bloodType),
      'wolofAudioDetails': wolofAudioUrls,
    };
  }

  /// Create a copy of this emergency with optional new values
  Emergency copyWith({
    String? id,
    String? title,
    String? hospital,
    DateTime? date,
    String? bloodType,
    String? wolofAudioUrls,
    double? latitude,
    double? longitude,
  }) {
    return Emergency(
      id: id ?? this.id,
      title: title ?? this.title,
      hospital: hospital ?? this.hospital,
      date: date ?? this.date,
      bloodType: bloodType ?? this.bloodType,
      wolofAudioUrls: wolofAudioUrls ?? this.wolofAudioUrls,
      latitude: latitude ?? this.latitude,
      longitude: longitude ?? this.longitude,
    );
  }

  /// Format blood type by combining type and rhesus
  static String _formatBloodType(bloodType, resusType) {
    if (bloodType == null) return '';

    final blood = bloodType.toString();
    var rhesus = '';

    if (resusType != null) {
      rhesus = resusType.toString() == 'POSITIVE' ? '+' : '-';
    }

    return '$blood$rhesus';
  }

  /// Extract blood type (A, B, AB, O) from combined string
  static String _extractBloodType(String combinedType) {
    if (combinedType.isEmpty) return '';
    return combinedType.replaceAll('+', '').replaceAll('-', '');
  }

  /// Extract rhesus type (+ or -) from combined string
  static String _extractResusType(String combinedType) {
    if (combinedType.isEmpty) return '';
    return combinedType.endsWith('+') ? 'POSITIVE' : 'NEGATIVE';
  }
}
