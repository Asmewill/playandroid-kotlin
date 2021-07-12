package fall.out.wanandroid.adapter

import android.text.Html
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.ImageLoader
import fall.out.wanandroid.Utils.SettingUtil
import fall.out.wanandroid.bean.ArticleResponseBody

/**
 * Created by Owen on 2019/10/26
 */
class HomeAdapter :BaseQuickAdapter<ArticleResponseBody.DatasBean,BaseViewHolder>(R.layout.item_home_list){


    override fun convert(helper: BaseViewHolder?, item: ArticleResponseBody.DatasBean?) {
        val tv_article_top= helper?.getView<TextView>(R.id.tv_article_top)
        val tv_article_fresh=helper?.getView<TextView>(R.id.tv_article_fresh)
        val tv_article_tag= helper?.getView<TextView>(R.id.tv_article_tag)
        val tv_article_author=helper?.getView<TextView>(R.id.tv_article_author)
        val tv_article_date=helper?.getView<TextView>(R.id.tv_article_date)
        val iv_article_thumbnail= helper?.getView<ImageView>(R.id.iv_article_thumbnail)
        val tv_article_title= helper?.getView<TextView>(R.id.tv_article_title)
        val tv_article_chapterName= helper?.getView<TextView>(R.id.tv_article_chapterName)
        val iv_like=helper?.getView<ImageView>(R.id.iv_like)
        item?:return
        helper?:return
        helper.addOnClickListener(R.id.iv_like)
        if(item.tags?.size!!>0){
            tv_article_tag?.visibility= View.VISIBLE
            tv_article_tag?.setText(item.tags!![0].name)
        }else{
            tv_article_tag?.visibility=View.GONE
        }
        if(item.fresh){
            tv_article_fresh?.visibility=View.VISIBLE
        }else{
            tv_article_fresh?.visibility=View.GONE
        }
        if(!TextUtils.isEmpty(item.author)){
           tv_article_author?.setText(item.author)
        }else{
            tv_article_author?.setText(item.shareUser)
        }
        tv_article_date?.setText(item.niceDate)
        tv_article_title?.setText(Html.fromHtml(item.title))
        tv_article_chapterName?.setText(item.superChapterName+"/"+item.chapterName)
       if(!TextUtils.isEmpty(item.envelopePic)&&!SettingUtil.getIsNoPhotoMode()){
           iv_article_thumbnail?.visibility=View.VISIBLE
           ImageLoader.load(mContext,item.envelopePic,iv_article_thumbnail)
       }else{
           iv_article_thumbnail?.visibility=View.GONE
       }
        if(item.isTop){
            tv_article_top?.visibility=View.VISIBLE
        }else{
            tv_article_top?.visibility=View.GONE
        }
        if(item.collect){
            iv_like?.setImageResource(R.drawable.ic_like)
        }else{
            iv_like?.setImageResource(R.drawable.ic_like_not)
        }
    }
}