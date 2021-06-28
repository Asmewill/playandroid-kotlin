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

    override fun onCreate() {
        super.onCreate()
    }


}