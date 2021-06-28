package com.example.oapp.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oapp.event.BaseEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by jsxiaoshui on 2021/6/24
 */
abstract  class BaseActivity :AppCompatActivity(){

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