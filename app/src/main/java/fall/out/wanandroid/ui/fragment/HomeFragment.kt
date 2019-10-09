package fall.out.wanandroid.ui.fragment

import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseFragment

/**
 * Created by Owen on 2019/10/9
 */
class HomeFragment: BaseFragment() {
    companion object{
        fun getInstance():HomeFragment=HomeFragment()
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {

    }
}