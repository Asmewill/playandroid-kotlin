package fall.out.wanandroid.ui.fragment

import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseFragment

/**
 * Created by Owen on 2019/10/9
 */
class WeChatFragment:BaseFragment() {
    companion object{
        fun getInstance():WeChatFragment=WeChatFragment()
    }
    override fun attachLayoutRes(): Int {
         return  R.layout.fragment_wechat
    }
    override fun initView() {

    }
}