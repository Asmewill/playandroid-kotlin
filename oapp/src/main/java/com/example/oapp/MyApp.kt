package com.example.oapp

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
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

    }


}