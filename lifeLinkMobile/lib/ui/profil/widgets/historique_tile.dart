import 'package:flutter/cupertino.dart';

import 'package:lifelink/utils/app_colors.dart';

///
class HistoriqueTile extends StatelessWidget{

  /// constant constructor
  const HistoriqueTile({
    required this.lieu, required this.volume, required this.date, super.key,
  });

  ///
  final String lieu;
  ///
  final String volume;
  ///
  final String date;
  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width,
      padding: const EdgeInsets.all(20),
      height: 120,
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(20),
    color: AppColors.customRed,
      ),
        child: Row(
        children: [
          Image.asset('assets/icons/historique.png'),
          const SizedBox(width: 20,),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                lieu,
                style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 18,
                    color: AppColors.lightColorScheme.onPrimary,
                ),
              ),
              Text('Volume : $volume',
                style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 18,
                    color: AppColors.lightColorScheme.onPrimary,
                ),
              ),
              Text(
                date,
                style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 18,
                    color: AppColors.lightColorScheme.onPrimary,
                )
                ,),
            ],
          ),
        ],
    ),
    );
  }
}
