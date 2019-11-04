package fall.out.wanandroid.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.CommonUtil
import fall.out.wanandroid.bean.NavigationBean
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ui.ContentActivity
import q.rorbin.verticaltablayout.util.DisplayUtil

/**
 * Created by Owen on 2019/10/31
 */
class NavigationAdapter :BaseQuickAdapter<NavigationBean,BaseViewHolder>(R.layout.item_navigation_list){
    override fun convert(helper: BaseViewHolder, item: NavigationBean?) {
        val item_navigation_tv=helper.getView<TextView>(R.id.item_navigation_tv)
        val item_navigation_flow_layout=helper.getView<TagFlowLayout>(R.id.item_navigation_flow_layout)
        item?:return
        item_navigation_tv.setText(item.name)
        item_navigation_flow_layout.adapter=object :TagAdapter<NavigationBean.ArticlesBean>(item.articles){
            override fun getView(parent: FlowLayout?, position: Int, article: NavigationBean.ArticlesBean?): View {
              val textView=LayoutInflater.from(mContext).inflate(R.layout.flow_layout_tv,item_navigation_flow_layout,false) as TextView
               val padding=DisplayUtil.dp2px(mContext,10f)
                textView.setPadding(padding,padding,padding,padding)
                textView.text=article?.title
                textView.setTextColor(CommonUtil.randomColor())
                return  textView
            }
        }

        item_navigation_flow_layout.setOnTagClickListener(object :TagFlowLayout.OnTagClickListener{
            override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
                var intent= Intent(mContext, ContentActivity::class.java)
                intent.putExtra(Constant.CONTENT_TITLE_KEY,item.articles.get(position).title)
                intent.putExtra(Constant.CONTENT_URL_KEY,item.articles.get(position).link)
                mContext.startActivity(intent)
                return true
            }

        })

    }
}