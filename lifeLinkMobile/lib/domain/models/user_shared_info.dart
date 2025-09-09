
class EnrichedShareInfoRequest {
  final String? id;
  final DateTime? requestDate;
  final DateTime? acceptanceDate;
  final bool? isAccepted;
  final String? phoneNumber;
  final String? sharedInfoSelection;

  // Informations de la campagne
  final int? campaignId;
  final String? campaignStatus;
  final DateTime? campaignStartAt;
  final DateTime? campaignEndAt;
  final String? campaignContact;
  final String? campaignDetails;
  final String? campaignImage;

  // Informations du centre de don
  final int? centerId;
  final String? centerName;
  final String? centerContact;
  final String? centerDetails;

  // Informations d'adresse
  final int? addressId;
  final String? addressText;
  final double? longitude;
  final double? latitude;
  final String? region;

  EnrichedShareInfoRequest({
    this.id,
    this.requestDate,
    this.acceptanceDate,
    this.isAccepted,
    this.phoneNumber,
    this.sharedInfoSelection,
    this.campaignId,
    this.campaignStatus,
    this.campaignStartAt,
    this.campaignEndAt,
    this.campaignContact,
    this.campaignDetails,
    this.campaignImage,
    this.centerId,
    this.centerName,
    this.centerContact,
    this.centerDetails,
    this.addressId,
    this.addressText,
    this.longitude,
    this.latitude,
    this.region,
  });

  factory EnrichedShareInfoRequest.fromJson(Map<String, dynamic> json) {
    return EnrichedShareInfoRequest(
      id: json['id'] as String,
      requestDate: json['requestDate'] != null ? DateTime.parse(json['requestDate'] as String) : null,
      acceptanceDate: json['acceptanceDate'] != null ? DateTime.parse(json['acceptanceDate'] as String)  : null,
      isAccepted: json['isAccepted'] as bool,
      phoneNumber: json['phoneNumber'] as String,
      sharedInfoSelection: json['sharedInfoSelection'] as String,
      campaignId: json['campaignId'] as int,
      campaignStatus: json['campaignStatus'] as String,
      campaignStartAt: json['campaignStartAt'] != null ? DateTime.parse(json['campaignStartAt'] as String) : null,
      campaignEndAt: json['campaignEndAt'] != null ? DateTime.parse(json['campaignEndAt'] as String) : null,
      campaignContact: json['campaignContact'] as String,
      campaignDetails: json['campaignDetails'] as String,
      campaignImage: json['campaignImage'] as String,
      centerId: json['centerId'] as int,
      centerName: json['centerName'] as String,
      centerContact: json['centerContact'] as String,
      centerDetails: json['centerDetails'] as String,
      addressId: json['addressId'] as int,
      addressText: json['addressText'] as String,
      longitude: json['longitude'] as double,
      latitude: json['latitude'] as double,
      region: json['region'] as String,
    );
  }

  String getSourceName() {
    if (centerName != null) {
      return centerName!;
    } else if (campaignStatus != null) {
      return 'Campagne de don de sang ($campaignStatus)';
    } else {
      return 'Requête #$id';
    }
  }

  String getDetailsText() {
    if (campaignDetails != null && campaignDetails!.isNotEmpty) {
      return campaignDetails!;
    } else if (centerDetails != null && centerDetails!.isNotEmpty) {
      return centerDetails!;
    } else {
      return 'Aucun détail disponible';
    }
  }

  String getLocationText() {
    if (addressText != null && addressText!.isNotEmpty) {
      return addressText!;
    } else if (region != null) {
      return 'Région: $region';
    } else {
      return 'Lieu non spécifié';
    }
  }
}

class ShareInfoResponse {
  final bool accept;
  final String? feedback;

  ShareInfoResponse({
    required this.accept,
    this.feedback,
  });

  Map<String, dynamic> toJson() {
    return {
      'accept': accept,
      if (feedback != null) 'feedback': feedback,
    };
  }
}