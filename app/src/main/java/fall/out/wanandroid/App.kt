package fall.out.wanandroid

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import cat.ereza.customactivityoncrash.config.CaocConfig
import fall.out.wanandroid.Utils.DisplayManager
import fall.out.wanandroid.Utils.SettingUtil
import fall.out.wanandroid.ui.ErrorActivity
import fall.out.wanandroid.ui.SplashActivity
import org.litepal.LitePal
import kotlin.properties.Delegates

/**
 * Created by Owen on 2019/10/8
 */
class App :MultiDexApplication() {
    companion object{
        var context:Context by Delegates.notNull()
        lateinit var instance:Application
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
        context=applicationContext
        DisplayManager.init(this)
        setTheme()
        LitePal.initialize(this)//数据库初始化


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