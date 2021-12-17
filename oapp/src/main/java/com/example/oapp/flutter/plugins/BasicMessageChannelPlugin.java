package com.example.oapp.flutter.plugins;

import android.widget.Toast;

import com.example.oapp.MyApp;

import io.flutter.Log;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StringCodec;

/**
 *  szj 2020/11/26
 *  CSDN博客:https://blog.csdn.net/weixin_44819566/
 *  微信公众号:码上变有钱
 *  BasicMessageChannelPlugin 用于Android与Flutter之间持续通信
 */
public class BasicMessageChannelPlugin implements BasicMessageChannel.MessageHandler<String> {
    private  IShowMessage iShowMessage;
    private final BasicMessageChannel<String> messageChannel;

    public static BasicMessageChannelPlugin registerWith(BinaryMessenger messenger, IShowMessage iShowMessage) {
        return new BasicMessageChannelPlugin(messenger, iShowMessage);
    }

    private BasicMessageChannelPlugin(BinaryMessenger messenger, IShowMessage iShowMessage) {
        this.iShowMessage = iShowMessage;
        this.messageChannel = new BasicMessageChannel(messenger, "BasicMessageChannelPlugin", StringCodec.INSTANCE);
        //设置消息处理器，处理来自Dart的消息
        messageChannel.setMessageHandler(this);

    }

    @Override//处理Dart发来的消息
    public void onMessage(String s, BasicMessageChannel.Reply<String> reply) {
        reply.reply("我是android的代码reply：" + s);//可以通过reply进行回复
        if (iShowMessage!=null) {
            iShowMessage.onShowMessage(s);
            Toast.makeText(MyApp.instance, s, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 向Dart发送消息，并接受Dart的反馈
     *
     * @param message  要给Dart发送的消息内容
     * @param callback 来自Dart的反馈
     */
   public void send(String message, BasicMessageChannel.Reply<String> callback) {
       Log.i("szjsend",message);
        messageChannel.send(message, callback);
   }

}