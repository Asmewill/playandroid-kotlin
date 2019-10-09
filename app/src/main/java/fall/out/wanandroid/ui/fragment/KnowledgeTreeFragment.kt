package fall.out.wanandroid.ui.fragment

import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseFragment

/**
 * Created by Owen on 2019/10/9
 */
class KnowledgeTreeFragment:BaseFragment() {
    companion object{
        fun getInstance():KnowledgeTreeFragment=KnowledgeTreeFragment()
    }
    override fun attachLayoutRes(): Int {
        return R.layout.fragment_know_tree
    }

    override fun initView() {

    }
}