package com.example.oapp.flutter

import android.os.Handler
import android.text.TextUtils
import android.util.Log
import com.example.oapp.R
import com.example.oapp.base.BaseActivity
import com.example.oapp.constant.Constant
import com.example.oapp.ext.showToast
import com.example.oapp.flutter.plugins.IShowMessage
import com.example.oapp.ui.MainActivity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.common.StringCodec
import kotlinx.android.synthetic.main.activity_flutter_channel.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by jsxiaoshui on 2021-12-17
 */
class FlutterChannelActivity:BaseActivity() {

    private var basicMessageChannel: BasicMessageChannel<String>?=null

    override fun attachLayoutRes(): Int {
        return R.layout.activity_flutter_channel
    }

    override fun initView() {
        toolbar?.let {
            it.title="Flutter和原生通信"
            setSupportActionBar(it)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        MainActivity.flutterEngine?.run {
//            setMessageChannel(this)
//        }

        /**
         * 0 给Flutter传递初始化数据
         * 1 使用BasicMsgChannel传递数据
         * 2 使用EventChannel传递当前电量
         * 3 使用MethodChannel获取数据
         * 4 单纯跳转Flutter页面
         */
        btn_basicmsg_channel.setOnClickListener {
            if(TextUtils.isEmpty(et_input.text.toString())){
                showToast("请输入消息~")
                return@setOnClickListener
            }
            //1.第一种方式
            FlutterAppActivity.goFlutter(this,et_input.text.toString(),1)
        }
        btn_method_channel.setOnClickListener {
            FlutterAppActivity.goFlutter(this,"EventChannel",2)
        }
        btn_method_channel.setOnClickListener {
            FlutterAppActivity.goFlutter(this,"MethodChannel",3)
        }
        btn_init.setOnClickListener {
            FlutterAppActivity.goFlutter(this,"init...",0)
        }
        btn_goflutter.setOnClickListener {
            FlutterAppActivity.goFlutter(this,"",4)
        }
    }

    override fun initData() {

    }

//    private fun setMessageChannel(flutterEngine: FlutterEngine) {
//        basicMessageChannel= BasicMessageChannel<String>( flutterEngine.dartExecutor.binaryMessenger,
//            "BasicMessageChannelPlugin", StringCodec.INSTANCE)
//        basicMessageChannel!!.setMessageHandler(BasicMessageChannel.MessageHandler { message, reply ->
//            Log.i("message:","接收到flutter中的消息："+message.toString())
//            showToast(message.toString())
//             et_input.setText(message.toString())
//            reply.reply("回执消息："+message.toString())
//        })
//    }
}