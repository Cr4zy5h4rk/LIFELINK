
/// Statistics model
class Statistics {
  /// Number of donors
  final int donorsCount;
  ///objective percentage
  final double objectivePercentage;
  ///lives saved
  final int livesSaved;

  /// Constructor
  Statistics({
    required this.donorsCount,
    required this.objectivePercentage,
    required this.livesSaved,
  });

  static double _parseDoubleSafely(value) {
    if (value == null) {
      return 0;
    }
    if (value is int) {
      return value.toDouble();
    }
    if (value is double) {
      return value;
    }
    if (value is num) {
      return value.toDouble();
    }
    if (value is String) {
      return double.tryParse(value) ?? 0;
    }
    return 0;
  }

  /// Convert JSON object to Statistics
  factory Statistics.fromJson(Map<String, dynamic> json) {
    return Statistics(
      donorsCount: json['donorsCount'] != null
          ? int.tryParse(json['donorsCount'].toString()) ?? 0
          : 0,
      objectivePercentage:  _parseDoubleSafely(json['objectivePercentage']),
      livesSaved: json['livesSaved'] != null
          ? int.tryParse(json['livesSaved'].toString()) ?? 0
          : 0,
    );
  }

  ///Convert Statistics to JSON
  Map<String, dynamic> toJson() {
    return {
      'donorsCount': donorsCount,
      'objectivePercentage': objectivePercentage,
      'livesSaved': livesSaved,
    };
  }
}
