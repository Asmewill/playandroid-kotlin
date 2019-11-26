package fall.out.wanandroid.bean

import fall.out.wanandroid.Utils.SettingUtil

/**
 * Created by Owen on 2019/11/26
 */
class ColorEvent constructor(var isRefresh:Boolean=false , var color:Int=SettingUtil.getColor()) {
}