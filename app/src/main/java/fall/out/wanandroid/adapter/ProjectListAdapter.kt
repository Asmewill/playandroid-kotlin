package fall.out.wanandroid.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.ImageLoader
import fall.out.wanandroid.bean.ArticleResponseBody

/**
 * Created by Owen on 2019/10/30
 */
class ProjectListAdapter:BaseQuickAdapter<ArticleResponseBody.DatasBean,BaseViewHolder>(R.layout.item_project_list) {

    override fun convert(helper: BaseViewHolder, item: ArticleResponseBody.DatasBean?) {
        val item_project_list_iv=helper.getView<ImageView>(R.id.item_project_list_iv)
        val item_project_list_title_tv=helper.getView<TextView>(R.id.item_project_list_title_tv)
        val item_project_list_content_tv=helper.getView<TextView>(R.id.item_project_list_content_tv)
        val item_project_list_author_tv=helper.getView<TextView>(R.id.item_project_list_author_tv)
        val item_project_list_time_tv=helper.getView<TextView>(R.id.item_project_list_time_tv)
        val item_project_list_like_iv=helper.getView<ImageView>(R.id.item_project_list_like_iv)
        item?:return
        ImageLoader.load(mContext,item.envelopePic,item_project_list_iv)
        item_project_list_title_tv.setText(item.title)
        item_project_list_content_tv.setText(item.desc)
        item_project_list_author_tv.setText(item.author)
        item_project_list_time_tv.setText(item.niceDate)
        helper.addOnClickListener(R.id.item_project_list_like_iv)
        if(item.collect){
            item_project_list_like_iv.setImageResource(R.drawable.ic_like)
        }else{
            item_project_list_like_iv.setImageResource(R.drawable.ic_like_not)
        }


    }
}