package com.example.oapp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.example.oapp.utils.SettingUtil
import com.kingja.loadsir.core.LoadSir
import me.hgj.jetpackmvvm.demo.app.weight.loadCallBack.*
import org.litepal.LitePal
import kotlin.properties.Delegates

/**
 * Created by jsxiaoshui on 2021/6/24
 */
class MyApp :MultiDexApplication() {
    companion object{
       var context:Context by Delegates.notNull()
       lateinit var instance:Application
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
        context=applicationContext
        LitePal.initialize(this)//数据库初始化
        initLoadSir()
        setTheme()

    }

    private fun initLoadSir() {
        //界面加载管理 初始化
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(LoadingCallback2())
            .addCallback(ErrorCallback())//错误
            .addCallback(ErrorCallback2())//错误
            .addCallback(EmptyCallback())//空
            .addCallback(EmptyCallback2())
            //.setDefaultCallback(LoadingCallback::class.java)//设置默认加载状态页
            .commit()
    }

    /***
     * 设置主题
     */
    fun  setTheme(){
        if(SettingUtil.getIsNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            SettingUtil.setIsNightMode(true)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            SettingUtil.setIsNightMode(false)
        }
    }
}