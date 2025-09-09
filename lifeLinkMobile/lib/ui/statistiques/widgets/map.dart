import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';

/// map
class MyMap extends StatefulWidget {
  @override
  State<MyMap> createState() => _MyMapState();
}

class _MyMapState extends State<MyMap> {
  List<Polygon> _polygons = [];

  @override
  void initState() {
    super.initState();
    _loadGeoJson();
  }

  /// Load GeoJson from file
  Future<void> _loadGeoJson() async {
    final file = File('assets/your_geojson_file.geojson'); // Update this path
    final jsonString = await file.readAsString();
    final geoJson = jsonDecode(jsonString) as Map<String, dynamic>;

    final features = geoJson['features'] as List;

    final polygons = <Polygon>[];

    // Loop through the features and extract coordinates
    for (final feature in features) {
      final geometry = feature['geometry'];
      if (geometry['type'] == 'Polygon') {
        final coordinates = geometry['coordinates'][0];

        // Convert the coordinates into LatLng points
        final polygonCoords =
        coordinates
            .map<LatLng>(
              (coord) => LatLng(coord[1] as double, coord[0] as double),
        ) // Lat, Long order
            .toList() as List<LatLng>;

        // Add the polygon to the list
        polygons.add(
          Polygon(
            points: polygonCoords,
            color: Colors.blue.withValues(alpha: 0.3),
            borderStrokeWidth: 2,
            borderColor: Colors.blue,
          ),
        );
      }
    }

    setState(() {
      _polygons = polygons;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        height: 400,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10), // Rounded edges
        ),
        child: ClipRRect(
          borderRadius: BorderRadius.circular(10), // Ensure rounded corners
          child: FlutterMap(
            options: MapOptions(
              initialCenter: const LatLng(14.4974, -14.4524), // San Francisco
              initialZoom: 7,
              minZoom: 6,
              maxZoom: 10,
              cameraConstraint: CameraConstraint.contain(
                bounds: LatLngBounds(
                  const LatLng(12, -17.5), // Southwest corner of Senegal
                  const LatLng(16.7, -11), // Northeast corner of Senegal
                ),
              ),
            ),
            children: [
              TileLayer(
                urlTemplate:
                'https://api.mapbox.com/styles/v1/sirnoodles/cm8sx09dq00go01skcssu3u8m/tiles/256/{z}/{x}/{y}@2x?access_token=pk.eyJ1Ijoic2lybm9vZGxlcyIsImEiOiJjbThzdHpsajkwNG9zMmxzNjhpMzYyOHlwIn0.3wexSseFBrrLiL01q3LgHg',
                subdomains: const ['a', 'b', 'c', 'd'],
                userAgentPackageName: 'sn.lifelink.ept',
              ),
              PolygonLayer(polygons: _polygons),
            ],
          ),
        ),
      ),
    );
  }
}
