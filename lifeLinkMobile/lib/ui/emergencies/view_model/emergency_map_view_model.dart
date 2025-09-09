import 'package:flutter/material.dart';
import 'package:just_audio/just_audio.dart';
import 'package:latlong2/latlong.dart';
import 'package:lifelink/data/repositories/emergency_repository.dart';
import 'package:lifelink/domain/models/emergency.dart';

///
class EmergencyMapViewModel extends ChangeNotifier {
  final EmergencyRepository _repository = EmergencyRepository();
  List<Emergency> _emergencies = [];
  List<Emergency> _filteredEmergencies = [];
  bool _isLoading = true;
  String _selectedBloodType = '';
  String _selectedRegion = '';

  final AudioPlayer _audioPlayer = AudioPlayer();

  // Map of Senegal regions with optimized bounds and zoom
  // Each region has a center location, zoom level, and bounds
  final Map<String, Map<String, dynamic>> _regionData = {
    'Dakar': {
      'center': const LatLng(14.7167, -17.4677),
      'zoom': 11.0,
      'bounds': {
        'north': 14.8367, 'south': 14.5967,
        'east': -17.2677, 'west': -17.6677,
      },
    },
    'Thiès': {
      'center': const LatLng(14.7894, -16.9253),
      'zoom': 9.5,
      'bounds': {
        'north': 15.1094, 'south': 14.4694,
        'east': -16.5253, 'west': -17.3253,
      },
    },
    'Diourbel': {
      'center': const LatLng(14.7167, -16.2500),
      'zoom': 9.5,
      'bounds': {
        'north': 15.0367, 'south': 14.3967,
        'east': -15.8500, 'west': -16.6500,
      },
    },
    'Fatick': {
      'center': const LatLng(14.3390, -16.4108),
      'zoom': 9.0,
      'bounds': {
        'north': 14.7590, 'south': 13.9190,
        'east': -16.0108, 'west': -16.8108,
      },
    },
    'Kaolack': {
      'center': const LatLng(14.1825, -16.0756),
      'zoom': 9.0,
      'bounds': {
        'north': 14.6025, 'south': 13.7625,
        'east': -15.6756, 'west': -16.4756,
      },
    },
    'Kaffrine': {
      'center': const LatLng(14.1050, -15.5423),
      'zoom': 9.0,
      'bounds': {
        'north': 14.5250, 'south': 13.6850,
        'east': -15.1423, 'west': -15.9423,
      },
    },
    'Tambacounda': {
      'center': const LatLng(13.7712, -13.6730),
      'zoom': 8.0,
      'bounds': {
        'north': 14.5712, 'south': 12.9712,
        'east': -12.8730, 'west': -14.4730,
      },
    },
    'Kédougou': {
      'center': const LatLng(12.5540, -12.1755),
      'zoom': 9.0,
      'bounds': {
        'north': 13.0740, 'south': 12.0340,
        'east': -11.7755, 'west': -12.5755,
      },
    },
    'Kolda': {
      'center': const LatLng(12.8863, -14.9382),
      'zoom': 9.0,
      'bounds': {
        'north': 13.3063, 'south': 12.4663,
        'east': -14.5382, 'west': -15.3382,
      },
    },
    'Sédhiou': {
      'center': const LatLng(12.7058, -15.5773),
      'zoom': 9.0,
      'bounds': {
        'north': 13.1258, 'south': 12.2858,
        'east': -15.1773, 'west': -15.9773,
      },
    },
    'Ziguinchor': {
      'center': const LatLng(12.5598, -16.2646),
      'zoom': 9.5,
      'bounds': {
        'north': 12.9798, 'south': 12.1398,
        'east': -15.8646, 'west': -16.6646,
      },
    },
    'Saint-Louis': {
      'center': const LatLng(16.0323, -16.4842),
      'zoom': 8.5,
      'bounds': {
        'north': 16.8323, 'south': 15.2323,
        'east': -15.6842, 'west': -17.2842,
      },
    },
    'Louga': {
      'center': const LatLng(15.6170, -16.2249),
      'zoom': 8.5,
      'bounds': {
        'north': 16.4170, 'south': 14.8170,
        'east': -15.4249, 'west': -17.0249,
      },
    },
    'Matam': {
      'center': const LatLng(15.6562, -13.2576),
      'zoom': 8.5,
      'bounds': {
        'north': 16.4562, 'south': 14.8562,
        'east': -12.4576, 'west': -14.0576,
      },
    },
  };

  // Default center for Senegal when no region is selected
  static const LatLng _defaultCenter = LatLng(14.4974, -14.4524);
  final double _defaultZoom = 7;

  ///
  EmergencyMapViewModel() {
    _loadEmergencies();
  }

  /// Getters
  List<Emergency> get emergencies => _emergencies;
  /// Getters
  List<Emergency> get filteredEmergencies => _filteredEmergencies;
  /// Getters
  bool get isLoading => _isLoading;
  /// Getters
  String get selectedBloodType => _selectedBloodType;
  /// Getters
  String get selectedRegion => _selectedRegion;

  /// Get appropriate map center based on filters
  LatLng getMapCenter() {
    // If a region is selected, center on that region
    if (_selectedRegion.isNotEmpty && _regionData.containsKey(_selectedRegion)){
      return _regionData[_selectedRegion]!['center'] as LatLng;
    }

    /// If there are filtered emergencies with location data,
    /// center on their average
    if (_filteredEmergencies.isNotEmpty) {
      final validLocations = _filteredEmergencies
          .where((e) => e.latitude != null && e.longitude != null)
          .map((e) => LatLng(e.latitude!, e.longitude!))
          .toList();

      if (validLocations.isNotEmpty) {
        // Calculate average center
        double sumLat = 0;
        double sumLng = 0;
        for (final location in validLocations) {
          sumLat += location.latitude;
          sumLng += location.longitude;
        }
        return LatLng(sumLat / validLocations.length,
            sumLng / validLocations.length,);
      }
    }

    return _defaultCenter;
  }

  /// Get appropriate zoom level based on filters
  double getMapZoom() {
    // If a region is selected, use its zoom level
    if (_selectedRegion.isNotEmpty && _regionData.containsKey(_selectedRegion)){
      return _regionData[_selectedRegion]!['zoom'] as double;
    }

    // If there are filtered emergencies, adapt zoom level
    if (_filteredEmergencies.length == 1) {
      return 14; // Closer zoom for single emergency
    } else if (_filteredEmergencies.length > 1
        && _filteredEmergencies.length <= 5) {
      return 11; // Medium zoom for few emergencies
    } else if (_filteredEmergencies.length > 5) {
      return 9; // Further zoom for many emergencies
    }

    return _defaultZoom;
  }

  /// Get region bounds for optimal display
  Map<String, double>? getRegionBounds() {
    if (_selectedRegion.isNotEmpty && _regionData.containsKey(_selectedRegion)) {
      return _regionData[_selectedRegion]!['bounds'] as Map<String, double>?;
    }
    return null;
  }

  Future<void> _loadEmergencies() async {
    _isLoading = true;
    notifyListeners();

    try {
      final emergencies = await _repository.fetchEmergencies();
      _emergencies = emergencies;
      _filteredEmergencies = emergencies;
      _isLoading = false;
      notifyListeners();
    } catch (e) {
      print('Error loading emergencies: $e');
      _isLoading = false;
    }
  }


  /// Get emergency location based on its coordinates
  LatLng? getEmergencyLocation(Emergency emergency) {
    if (emergency.latitude != null && emergency.longitude != null) {
      return LatLng(emergency.latitude!, emergency.longitude!);
    }
    return null;
  }

  /// Refresh emergencies data
  Future<void> refreshEmergencies() async {
    await _loadEmergencies();
    _applyFilters();
    return Future.value();
  }

  /// Play audio for a given emergency
  Future<void> playAudio(String audioUrl) async {
    try {
      await _audioPlayer.setUrl(audioUrl);
      await _audioPlayer.play();
    } catch (e) {
      print('Error playing audio: $e');
    }
  }

  /// filter
  void filterByBloodType(String bloodType) {
    _selectedBloodType = bloodType;
    _applyFilters();
  }

  /// filter
  void filterByRegion(String region) {
    _selectedRegion = region;
    _applyFilters();
  }

  // Get region based on hospital name or coordinates
  String _getEmergencyRegion(Emergency emergency) {
    // Try to detect region from hospital name
    for (final region in _regionData.keys) {
      if (emergency.hospital.contains(region)) {
        return region;
      }
    }

    // If not found in name, try to find closest region based on coordinates
    if (emergency.latitude != null && emergency.longitude != null) {
      var minDistance = double.infinity;
      var closestRegion = '';

      final emergencyPosition = LatLng(emergency.latitude!, emergency.longitude!);

      for (final entry in _regionData.entries) {
        final location = entry.value['center'] as LatLng;
        final distance = _calculateDistance(emergencyPosition, location);
        if (distance < minDistance) {
          minDistance = distance;
          closestRegion = entry.key;
        }
      }

      return closestRegion;
    }

    return '';
  }

  // Calculate distance between two points (simplified)
  double _calculateDistance(LatLng point1, LatLng point2) {
    return (point1.latitude - point2.latitude).abs() +
        (point1.longitude - point2.longitude).abs();
  }

  void _applyFilters() {
    _filteredEmergencies = _emergencies;

    // Apply blood type filter
    if (_selectedBloodType.isNotEmpty) {
      _filteredEmergencies = _filteredEmergencies
          .where((emergency) => emergency.bloodType == _selectedBloodType)
          .toList();
    }

    // Apply region filter
    if (_selectedRegion.isNotEmpty) {
      _filteredEmergencies = _filteredEmergencies
          .where((emergency) =>
      _getEmergencyRegion(emergency) == _selectedRegion,)
          .toList();
    }

    notifyListeners();
  }

  @override
  Future<void> dispose() async {
    await _audioPlayer.dispose();
    super.dispose();
  }
}
