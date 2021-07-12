package com.example.oapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oapp.constant.Constant
import com.example.oapp.event.BaseEvent
import com.example.oapp.utils.Preference
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by jsxiaoshui on 2021/6/24
 */
abstract  class BaseActivity :AppCompatActivity(){
    protected var isLogin:Boolean by Preference(Constant.IS_LOGIN,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(attachLayoutRes())
        EventBus.getDefault().register(this)
        initView()
        initData()
    }

     abstract fun attachLayoutRes(): Int
     abstract  fun initView()
     abstract  fun initData()


     @Subscribe(threadMode = ThreadMode.MAIN)
      fun baseEvent(event:BaseEvent){

     }


    override fun onDestroy() {
        super.onDestroy()
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

}