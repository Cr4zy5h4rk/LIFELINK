import 'dart:math' as math;

import 'package:flutter/material.dart';
import 'package:lifelink/utils/app_colors.dart';

/// regeneration cycle
class RegenerationCycleCard extends StatelessWidget {
  /// From 0.0 to 1.0
  final double progress;
  ///
  final Color backgroundColor;
  ///
  final Color progressColor;
  ///
  final Color textColor;

  /// constant constructor
  const RegenerationCycleCard({
    required this.progress, super.key,
    this.backgroundColor = AppColors.customRed,
    this.progressColor = Colors.white70,
    this.textColor = Colors.white,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 150,
      width: MediaQuery.of(context).size.width / 2,
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
        color: backgroundColor,
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            'Cycle de regeneration',
            style: TextStyle(
              fontWeight: FontWeight.bold,
              fontSize: 16,
              color: textColor,
            ),
          ),
          const SizedBox(height: 15),
          Expanded(
            child: Center(
              child: Stack(
                alignment: Alignment.center,
                children: [
                  // Progress circle
                  CustomPaint(
                    size: const Size(80, 80),
                    painter: CircleProgressPainter(
                      progress: progress,
                      progressColor: progressColor,
                    ),
                  ),
                  // Percentage text
                  Text(
                    '${(progress * 100).toInt()}%',
                    style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: textColor,
                    ),
                  ),
                ],
              ),
            ),
          ),
          // Volume icon at the bottom right
          Align(
            alignment: Alignment.bottomRight,
            child: Image.asset(
                'assets/icons/audio-white.png',
            ),
          ),
        ],
      ),
    );
  }
}

///
class CircleProgressPainter extends CustomPainter {
  ///
  final double progress;
  ///
  final Color progressColor;

  ///
  CircleProgressPainter({
    required this.progress,
    required this.progressColor,
  });

  @override
  void paint(Canvas canvas, Size size) {
    final center = Offset(size.width / 2, size.height / 2);
    final radius = math.min(size.width, size.height) / 2;
    const strokeWidth = 8.0;

    // Draw background circle (empty)
    final backgroundPaint = Paint()
      ..color = progressColor.withValues(alpha: 0.2)
      ..style = PaintingStyle.stroke
      ..strokeWidth = strokeWidth;

    canvas.drawCircle(center, radius, backgroundPaint);

    // Draw progress arc
    final progressPaint = Paint()
      ..color = progressColor
      ..style = PaintingStyle.stroke
      ..strokeWidth = strokeWidth
      ..strokeCap = StrokeCap.round;

    final sweepAngle = 2 * math.pi * progress;
    canvas.drawArc(
      Rect.fromCircle(center: center, radius: radius),
      -math.pi / 2, // Start from top
      sweepAngle,
      false,
      progressPaint,
    );
  }

  @override
  bool shouldRepaint(covariant CircleProgressPainter oldDelegate) {
    return oldDelegate.progress != progress ||
        oldDelegate.progressColor != progressColor;
  }
}
