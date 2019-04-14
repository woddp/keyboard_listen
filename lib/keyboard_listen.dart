import 'dart:async';

import 'package:flutter/services.dart';

class KeyboardListen {
//  static const MethodChannel _channel =
//      const MethodChannel('keyboard_listen');
//
//  static Future<String> get platformVersion async {
//    final String version = await _channel.invokeMethod('getPlatformVersion');
//    return version;
//  }
//https://juejin.im/post/5b3ae6b96fb9a024ba6e0dbb 从本地到flutter 通信
  static const EventChannel eventChannel = const EventChannel('keyboard_listen_plugin');
  static StreamSubscription eventSubscription;
  static init(cb) {
    eventSubscription =
        eventChannel.receiveBroadcastStream().listen((dynamic data) {
          cb(data);
        });
  }
}
