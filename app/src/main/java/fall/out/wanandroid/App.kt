package fall.out.wanandroid

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * Created by Owen on 2019/10/8
 */
class App :Application() {
    companion object{
        var context:Context by Delegates.notNull()
        lateinit var instance:Application
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
        context=applicationContext
    }


}