import 'package:flutter/material.dart';

import 'package:ringtone_player/ringtone_player.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
  }



  @override
  Widget build(BuildContext context) {
  return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Ringtone player'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              Padding(
                padding:const EdgeInsets.all(8),
                child: ElevatedButton(
                  child: const Text('playAlarm'),
                  onPressed: () {
                    RingtonePlayer.playAlarm();
                  },
                ),
              ),
              Padding(
                padding:const EdgeInsets.all(8),
                child: ElevatedButton(
                  child: const Text('playAlarm asAlarm: false'),
                  onPressed: () {
                    RingtonePlayer.playAlarm(asAlarm: false);
                  },
                ),
              ),
              Padding(
                padding:const EdgeInsets.all(8),
                child: ElevatedButton(
                  child: const Text('playNotification'),
                  onPressed: () {
                   RingtonePlayer.playNotification();
                  },
                ),
              ),
              Padding(
                padding:const  EdgeInsets.all(8),
                child: ElevatedButton(
                  child: const Text('playRingtone'),
                  onPressed: () {
                    RingtonePlayer.playRingtone();
                  },
                ),
              ),
              Padding(
                padding:const EdgeInsets.all(8),
                child: ElevatedButton(
                  child: const Text('play'),
                  onPressed: () {
                    RingtonePlayer.play(
                      android: AndroidSounds.notification,
                      ios: IosSounds.glass,
                      looping: true,
                      volume: 1.0,
                    );
                  },
                ),
              ),
              Padding(
                padding:const EdgeInsets.all(8),
                child: ElevatedButton(
                  child: const Text('stop'),
                  onPressed: () {
                   RingtonePlayer.stop();
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
