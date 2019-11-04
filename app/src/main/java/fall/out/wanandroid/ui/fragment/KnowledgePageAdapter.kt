package fall.out.wanandroid.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import fall.out.wanandroid.bean.KnowledgeTreeBody

/**
 * Created by Owen on 2019/11/1
 */
class KnowledgePageAdapter(private val listBean:List<KnowledgeTreeBody.ChildrenBean>,fm:FragmentManager):FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
      return   KnowledgeFragment.getInstance(listBean.get(position).id)
    }
    override fun getCount(): Int {
        return listBean.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listBean.get(position).name
    }
}