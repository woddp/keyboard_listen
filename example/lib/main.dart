import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:keyboard_listen/keyboard_listen.dart';
void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}



class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    KeyboardListen.init((data) {
        print(data["isVisible"]);//键盘是否弹出
        print(data["height"]);//键盘高度
    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body:new SizedBox(
          child: new Stack(
            children: <Widget>[
              new Positioned(
                bottom: 0.0,
                left: 0.0,
                width: 300.0,
                child: new Container(
                  child: new TextField(
                    decoration: new InputDecoration(
                      labelText: "输入框！"
                    ),
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
