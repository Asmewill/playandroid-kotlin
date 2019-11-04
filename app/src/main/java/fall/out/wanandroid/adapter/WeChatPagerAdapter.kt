package fall.out.wanandroid.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import fall.out.wanandroid.bean.WXChapterBean
import fall.out.wanandroid.ui.fragment.KnowledgeFragment

/**
 * Created by Owen on 2019/10/29
 */
class WeChatPagerAdapter(private val list:List<WXChapterBean>, fm:FragmentManager): FragmentStatePagerAdapter(fm) {
    private var fragmentList=ArrayList<Fragment>()
    init {
        list.forEach {
            fragmentList.add(KnowledgeFragment.getInstance(it.id))
        }

    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getCount(): Int {
       return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list.get(position).name
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}