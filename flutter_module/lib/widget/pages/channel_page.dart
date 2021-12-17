import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class ChannelPage extends StatefulWidget {
   final  dynamic arguments;
   ChannelPage({this.arguments});

  @override
  _ChannelPageState createState() => _ChannelPageState(arguments);
}

class _ChannelPageState extends State<ChannelPage> {
  String _basicMessage=""; //用来接收BasicMsg消息
  String _eventMessage=""; //用来接收EventMes电量
  String _methodMessage=""; //用来获取当前给Android发送的MethodMessage数据
  String arguments;

  _ChannelPageState(this.arguments);

  /// BasicMessageChannel互相传递消息
  BasicMessageChannel<String> _basicMessageChannel = BasicMessageChannel('BasicMessageChannelPlugin', StringCodec());

  /// EventChannel接收Android发送来的电量
  EventChannel _eventChannelPlugin = EventChannel("demo.ht.com.androidproject/EventChannelPlugin");

  MethodChannel _methodChannel = new MethodChannel("MethodChannelPlugin");



  @override
  void initState() {
  //  使用BasicMessageChannel接受来自Native的消息，并向Native回复
    _basicMessageChannel.setMessageHandler((message) => Future<String>(() {
      setState(() {
        //Android --> Flutter
        _basicMessage = message!;
      });
      return "BasicMessageChannel收到android的消息：" + message!;
    }));



    //使用Event来接收电量消息
    _eventChannelPlugin.receiveBroadcastStream().listen((event) {
      setState(() {
        _eventMessage = event;
      });
    });
    super.initState();
  }

  void _onTextChange(value) async {
    String? response;
    /**
     * 在android对应的是 reply.reply()
     */
    response = await _basicMessageChannel.send(value);
    setState(() {
      _basicMessage = response!;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("AAA"),
      ),
      body: ListView(
        padding: EdgeInsets.all(20),
        children: [
          ElevatedButton(
              onPressed: () {
                ///命名路由跳转
                Navigator.pushNamed(context, '/tabs');
              },
              child: Text("Flutter商城")
          ),
          ElevatedButton(
              onPressed: () {
                ///命名路由跳转
                Navigator.pushNamed(context, '/');
              },
              child: Text("AAAAA")
          ),
          ElevatedButton(
            onPressed: () {
              _basicMessageChannel.send("我是Flutter的数据!!!");
            },
            child: Text("BasicMessageChannel发送消息给native"),
          ),
          TextField(
            onChanged: _onTextChange,
            decoration: InputDecoration(
              hintText: "使用BasicMessageChannel给原生发送的消息",
            ),
          ),

          SizedBox(
            height: 30,
          ),
          Text("接收android初始化数据为: ${arguments}"),
          SizedBox(
            height: 30,
          ),
          Text("BasicMessageChannel接收数据为: $_basicMessage"),
          SizedBox(
            height: 30,
          ),
          Text("EventChannel接收数据为: $_eventMessage"),
          SizedBox(
            height: 30,
          ),
          TextField(
            onChanged: _onMethodChannelTextChange,
            decoration: InputDecoration(
              hintText: "使用MethodChannel给原生发送的消息",
            ),
          ),
          Text("MethodChannel接收数据为:$_methodMessage")
        ],


      ),

    );
  }
  void _onMethodChannelTextChange(String value) async {
    var content = await _methodChannel.invokeMethod("send", value);
    print("szjmethodChannel$content");
    setState(() {
      _methodMessage = content ?? "MethodMessage消息为空啦";
    });
  }


}
