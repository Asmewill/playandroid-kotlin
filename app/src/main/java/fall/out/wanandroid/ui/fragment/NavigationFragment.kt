package fall.out.wanandroid.ui.fragment


import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseFragment

/**
 * Created by Owen on 2019/10/9
 */
class NavigationFragment:BaseFragment() {
    companion object{
        fun getInstance():NavigationFragment=NavigationFragment()
    }
    override fun attachLayoutRes(): Int {
         return  R.layout.fragment_navigation
    }

    override fun initView() {

    }
}