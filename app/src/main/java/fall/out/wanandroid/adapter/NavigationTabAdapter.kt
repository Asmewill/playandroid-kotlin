package fall.out.wanandroid.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import fall.out.wanandroid.R
import fall.out.wanandroid.bean.NavigationBean
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

/**
 * Created by Owen on 2019/10/30
 */
class NavigationTabAdapter(context:Context,private val list:List<NavigationBean>):TabAdapter{
    private var context:Context=context!!

    override fun getCount(): Int {
        return list.size
    }

    override fun getBadge(position: Int): ITabView.TabBadge? {
        return null
    }

    override fun getIcon(position: Int): ITabView.TabIcon? {
        return null
    }

    override fun getTitle(position: Int): ITabView.TabTitle {
           return  ITabView.TabTitle.Builder().setContent(list[position].name).setTextColor(ContextCompat.getColor(context,
               R.color.colorAccent),ContextCompat.getColor(context,R.color.Grey500)).build()
    }

    override fun getBackground(position: Int): Int {
        return -1
    }
}