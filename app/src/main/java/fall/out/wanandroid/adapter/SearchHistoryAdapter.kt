package fall.out.wanandroid.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fall.out.wanandroid.R
import fall.out.wanandroid.bean.SearchHistoryBean
import fall.out.wanandroid.ui.SearchActivity

/**
 * Created by Owen on 2019/11/18
 */
class SearchHistoryAdapter:BaseQuickAdapter<SearchHistoryBean,BaseViewHolder>(R.layout.item_search_history) {
    override fun convert(helper: BaseViewHolder, item: SearchHistoryBean?) {
        if(item==null||helper==null){
            return
        }
        val tv_search_key=helper.getView<TextView>(R.id.tv_search_key)
        tv_search_key.setText(item.key)
        val iv_clear=helper.getView<ImageView>(R.id.iv_clear)
        iv_clear.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
               if(mContext is SearchActivity){
                   (mContext as SearchActivity).deleleHotKey(item.key)
               }
            }
        })

    }
}