/// Campaign model
class Campaign {
  /// Unique identifier of the campaign
  final String id;
  /// Title of the campaign
  final String title;
  /// Description of the campaign (wolof audio)
  final String description;
  /// Image url of the campaign
  final String imageUrl;
  /// Start date of the campaign
  final DateTime startDate;
  /// End date of the campaign
  final DateTime endDate;
  /// Location of the campaign
  final String location;
  /// Contact phone number of the campaign
  final String? contactPhone;
  /// Schedule of the campaign
  final String? schedule;
  /// Statut de la campagne
  final String? status;

  /// Constructor
  Campaign({
    required this.id,
    required this.title,
    required this.description,
    required this.imageUrl,
    required this.startDate,
    required this.endDate,
    required this.location,
    this.contactPhone,
    this.schedule,
    this.status,
  });

  /// Convert Json object to campaign
  factory Campaign.fromJson(Map<String, dynamic> json) {
    return Campaign(
      id: json['id']?.toString() ?? '',
      title: json['title']?.toString() ?? '',
      description: json['description']?.toString() ?? '',
      imageUrl: json['imageUrl']?.toString() ?? '',
      startDate: json['startDate'] != null
          ? DateTime.parse(json['startDate'].toString())
          : DateTime.now(),
      endDate: json['endDate'] != null
          ? DateTime.parse(json['endDate'].toString())
          : DateTime.now().add(const Duration(days: 1)),
      location: json['location']?.toString() ?? '',
      contactPhone: json['contactPhone']?.toString() ?? '',
      schedule: json['schedule']?.toString() ?? '',
      status: json['status']?.toString() ?? '',
    );
  }

  /// Convert campaign to Json
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'imageUrl': imageUrl,
      'startDate': startDate.toIso8601String(),
      'endDate': endDate.toIso8601String(),
      'location': location,
      'contactPhone': contactPhone,
      'schedule': schedule,
      'status': status,
    };
  }
}
