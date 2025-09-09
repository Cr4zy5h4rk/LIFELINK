/// Blood group model
class BloodGroup {
  /// Type of blood group
  final String type;
  /// Availability of blood group
  final bool isAvailable;

  /// Constructor
  const BloodGroup({required this.type, this.isAvailable = true});
}
