package fall.out.wanandroid.adapter

import android.text.Html
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fall.out.wanandroid.bean.ProjectTreeBean
import fall.out.wanandroid.ui.fragment.ProjectListFragment

/**
 * Created by Owen on 2019/10/30
 */
class ProjectPagerAdapter(private val beanList:List<ProjectTreeBean>,fm:FragmentManager):FragmentStatePagerAdapter(fm) {
    private var fragmentList=ArrayList<Fragment>()
    init {
        beanList.forEach {
            fragmentList.add(ProjectListFragment.getInstance(it.id))
        }
    }
    override fun getItem(position: Int): Fragment {
       return fragmentList.get(position)
    }
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return Html.fromHtml(beanList.get(position).name)
    }

    override fun getItemPosition(`object`: Any): Int = androidx.viewpager.widget.PagerAdapter.POSITION_NONE
}