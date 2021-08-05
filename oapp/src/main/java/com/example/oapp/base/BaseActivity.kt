package com.example.oapp.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oapp.R
import com.example.oapp.constant.Constant
import com.example.oapp.event.BaseEvent
import com.example.oapp.utils.Preference
import com.example.oapp.utils.SettingUtil
import com.example.oapp.utils.StatusBarUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by jsxiaoshui on 2021/6/24
 */
abstract  class BaseActivity :AppCompatActivity(){
    protected var mThemeColor=SettingUtil.getColor()
    protected var isLogin  by Preference(Constant.IS_LOGIN,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(attachLayoutRes())
        EventBus.getDefault().register(this)
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
        initColor()
    }

     abstract fun attachLayoutRes(): Int
     abstract  fun initView()
     abstract  fun initData()


     @Subscribe(threadMode = ThreadMode.MAIN)
      fun baseEvent(event:BaseEvent){

     }

    /***
     * 只有Open的方法才可以被重写
     */
    open fun initColor() {
        mThemeColor=if(!SettingUtil.getIsNightMode()){
            SettingUtil.getColor()
        }else{
            resources.getColor(R.color.colorPrimary)
        }
        StatusBarUtil.setColor(this,mThemeColor,0)
        if(this.supportActionBar!=null){
            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(mThemeColor))
        }
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            if(SettingUtil.getNavBar()){
                window.navigationBarColor=mThemeColor
            }else{
                window.navigationBarColor= Color.BLACK
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

}