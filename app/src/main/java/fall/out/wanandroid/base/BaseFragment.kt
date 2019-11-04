package fall.out.wanandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fall.out.wanandroid.Utils.Preference
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.event.NetworkChangeEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Owen on 2019/10/9
 */
abstract  class BaseFragment:Fragment() {

    abstract fun attachLayoutRes(): Int
    abstract fun initView()
    abstract fun initData()
    protected  var isLogin:Boolean by Preference(Constant.LOGIN_KEY,false)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(attachLayoutRes(),null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        initView()
        initData()

    }

    override fun onDestroy() {
        super.onDestroy()
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun baseEvent(event: NetworkChangeEvent) {
    }

}