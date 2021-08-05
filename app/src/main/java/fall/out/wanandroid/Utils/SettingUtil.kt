package fall.out.wanandroid.Utils

import android.graphics.Color
import android.preference.PreferenceManager
import fall.out.wanandroid.App
import fall.out.wanandroid.R

/**
 * Created by Owen on 2019/11/13
 * show kotlin bytecode 可以看出object类就是一个单例类
 */
object SettingUtil {

    private val setting = PreferenceManager.getDefaultSharedPreferences(App.context)

    /**
     * 获取是否开启无图模式
     */
    fun getIsNoPhotoMode(): Boolean {
        return setting.getBoolean("switch_noPhotoMode", false) //&& NetWorkUtil.isMobile(App.context)
    }

    fun putNoPhotoMode(isNoPhotoMode:Boolean) {
        setting.edit().putBoolean("switch_noPhotoMode",isNoPhotoMode).apply()
    }

    /**
     * 获取是否开启显示首页置顶文章，true: 不显示  false: 显示
     */
    fun getIsShowTopArticle(): Boolean {
        return setting.getBoolean("switch_show_top", true)
    }

    fun putShowTopArticle(isShowTop:Boolean) {
        setting.edit().putBoolean("switch_show_top",isShowTop).apply()
    }

    /**
     * 获取主题颜色
     */
    fun getColor(): Int {
        val defaultColor = App.context.resources.getColor(R.color.colorPrimary)
        val color = setting.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else color
    }



    /**
     * 获取是否开启导航栏上色
     */
    fun getNavBar(): Boolean {
        return setting.getBoolean("nav_bar", false)
    }

    /**
     * 设置导航栏颜色
     */
    fun setNavBar(checked: Boolean) {
        setting.edit().putBoolean("nav_bar", checked).apply()
    }

    /**
     * 设置主题颜色
     */
    fun setColor(color: Int) {
        setting.edit().putInt("color", color).apply()
    }
    /**
     * 设置夜间模式
     */
    fun setIsNightMode(flag: Boolean) {
        setting.edit().putBoolean("switch_nightMode", flag).apply()
    }

    /**
     * 获取是否开启夜间模式
     */
    fun getIsNightMode(): Boolean {
        return setting.getBoolean("switch_nightMode", false)
    }

    /**
     * 获取是否开启自动切换夜间模式
     */
    fun getIsAutoNightMode(): Boolean {
        return setting.getBoolean("auto_nightMode", false)
    }

    fun getNightStartHour(): String {
        return setting.getString("night_startHour", "22").toString()
    }

    fun setNightStartHour(nightStartHour: String) {
        setting.edit().putString("night_startHour", nightStartHour).apply()
    }

    fun getNightStartMinute(): String {
        return setting.getString("night_startMinute", "00").toString()
    }

    fun setNightStartMinute(nightStartMinute: String) {
        setting.edit().putString("night_startMinute", nightStartMinute).apply()
    }

    fun getDayStartHour(): String {
        return setting.getString("day_startHour", "06").toString()
    }

    fun setDayStartHour(dayStartHour: String) {
        setting.edit().putString("day_startHour", dayStartHour).apply()
    }

    fun getDayStartMinute(): String {
        return setting.getString("day_startMinute", "00").toString()
    }

    fun setDayStartMinute(dayStartMinute: String) {
        setting.edit().putString("day_startMinute", dayStartMinute).apply()
    }


}