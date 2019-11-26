package fall.out.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fall.out.wanandroid.ui.fragment.ToDoFragment

/**
 * Created by Owen on 2019/10/30
 */
class TodoPagerAdapter(fm:FragmentManager):FragmentStatePagerAdapter(fm) {
    private var fragmentList=ArrayList<Fragment>()
    init {
        fragmentList.add(ToDoFragment.getInstance("listnotdo"))
        fragmentList.add(ToDoFragment.getInstance("listdone"))
    }
    override fun getItem(position: Int): Fragment {
       return fragmentList.get(position)
    }
    override fun getCount(): Int {
        return fragmentList.size
    }


}