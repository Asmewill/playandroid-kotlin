package com.example.oapp.flutter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.oapp.R
import com.example.oapp.constant.Constant
import com.example.oapp.flutter.plugins.BasicMessageChannelPlugin
import com.example.oapp.flutter.plugins.EventChannelPlugin
import com.example.oapp.flutter.plugins.IShowMessage
import com.example.oapp.flutter.plugins.MethodChannelPlugin
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.TransparencyMode
import io.flutter.embedding.engine.FlutterEngine

/**
 * Created by jsxiaoshui on 2021-12-17
 */
class FlutterAppActivity: FlutterActivity(),IShowMessage {
    private var basicMessageChannelPlugin: BasicMessageChannelPlugin? = null


    var mInitParam: String? = null

    /**
     * 0 给Flutter传递初始化数据
     * 1 使用BasicMsgChannel传递数据
     * 2 使用EventChannel传递当前电量
     * 3 使用MethodChannel获取数据
     * 4 单纯跳转Flutter页面
     */
    companion object{
        var mtype = 0
        val INIT_PARAMS = "initParams"
        var iShowMessage:IShowMessage?=null

        fun goFlutter(context: Context, initParams: String?, type: Int) {
            mtype = type
            val intent = Intent(context, FlutterAppActivity::class.java)
            intent.putExtra(INIT_PARAMS, initParams)
            context.startActivity(intent)
        }

        fun goFlutterTest(context: Context, initParams: String?, type: Int,iShowMessage: IShowMessage) {
            this.iShowMessage=iShowMessage
            mtype = type
            val intent = Intent(context, FlutterAppActivity::class.java)
            intent.putExtra(INIT_PARAMS, initParams)
            context.startActivity(intent)
        }
    }

    /**
     * 传递初始化参数给Flutter
     */
    override fun getInitialRoute(): String? {
        Log.i("szjgetInitialRoute", mInitParam + "")
        return if (mInitParam == null) super.getInitialRoute() else mInitParam
    }


    override fun onStart() {
        super.onStart()
        //使用BasicMsgChannel传递数据
        mInitParam = intent.getStringExtra(INIT_PARAMS)
        sendMessage(mInitParam)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * configureFlutterEngine优先级比onCreate高！
     *
     * @param flutterEngine
     */
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        Log.i("szjonCreate", FlutterAppActivity.mtype.toString() + "")
        if (mtype == 0) { //初始化
            // 给Flutter传递初始化数据
            mInitParam = intent.getStringExtra(INIT_PARAMS)
        } else if (FlutterAppActivity.mtype == 1) { //basicMessageChannel
            //使用BasicMsgChannel传递数据
            basicMessageChannelPlugin = BasicMessageChannelPlugin.registerWith(
                flutterEngine.dartExecutor.binaryMessenger,
                this@FlutterAppActivity
            )
        } else if (mtype == 2) { //EventChannel
            //使用EventMessage传递当前电量  传递的是当前的flutterEngine 与 电量
            EventChannelPlugin.registerWith(
                flutterEngine.dartExecutor,
                intent.getStringExtra(INIT_PARAMS)
            )
        } else if (FlutterAppActivity.mtype == 3) { // MethodChannel
            MethodChannelPlugin.registerWith(flutterEngine.dartExecutor, this)
        } else if (FlutterAppActivity.mtype == 4) { //过渡动画
            //单纯跳转Flutter页面 过渡动画
            // 给Flutter传递初始化数据
            mInitParam = intent.getStringExtra(INIT_PARAMS)
            overridePendingTransition(R.anim.pageup_enter, R.anim.pageup_exit)
        }
    }

    override fun onShowMessage(message: String?) {
        Log.i("szjonShowMessage", message)

    }

    override fun sendMessage(message: String?) {
        Log.i("szjsendMessage", message)
        if (mtype == 1) {
            basicMessageChannelPlugin!!.send(message) { message: String? ->
                onShowMessage(message)//Android原生发送消息之后，收到flutter的回执
            }
        }
    }

    override fun getTransparencyMode(): TransparencyMode {
        Log.i("szjmType", mtype.toString() + "")
        return if (mtype == 4) TransparencyMode.transparent else super.getTransparencyMode()
    }


    /**
     * 使用在MyApplication预先初始化好的Flutter引擎以提升Flutter页面打开速度，
     * 注意：在这种模式下会导致getInitialRoute不被调用，所以无法设置初始化参数
     * 使用EventChannel传递数据必须注释掉！！！
     *
     * @return
     */
//    @Override
//    public String getCachedEngineId() {
//        return App.ENG_INED;
//    }



}