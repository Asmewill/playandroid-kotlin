package fall.out.wanandroid.adapter

import android.content.DialogInterface
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.DialogUtil
import fall.out.wanandroid.Utils.ImageLoader
import fall.out.wanandroid.bean.CollectArticle
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.ext.showToast
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper

/**
 * Created by Owen on 2019/11/5
 */
class CollectAdapter:BaseQuickAdapter<CollectArticle.DatasBean,BaseViewHolder>(R.layout.item_collect_list) {
    override fun convert(helper: BaseViewHolder, item: CollectArticle.DatasBean?) {
        var tv_article_author=helper.getView<TextView>(R.id.tv_article_author)
        var tv_article_date=helper.getView<TextView>(R.id.tv_article_date)
        var iv_article_thumbnail=helper.getView<ImageView>(R.id.iv_article_thumbnail)
        var tv_article_chapterName=helper.getView<TextView>(R.id.tv_article_chapterName)
        var iv_like=helper.getView<ImageView>(R.id.iv_like)
        var tv_article_title=helper.getView<TextView>(R.id.tv_article_title)
        item?:return
        if(!TextUtils.isEmpty(item.author)){
            tv_article_author.setText(item.author)
        }
        tv_article_date.setText(item.niceDate)
        if(!TextUtils.isEmpty(item.envelopePic)){
            iv_article_thumbnail.visibility=View.VISIBLE
            ImageLoader.load(mContext,item.envelopePic,iv_article_thumbnail)
        }else{
            iv_article_thumbnail.visibility=View.GONE
        }
        tv_article_chapterName.setText(item.chapterName)
        iv_like.setImageResource(R.drawable.ic_like)
        tv_article_title.setText(item.title)
        iv_like.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {

                DialogUtil.getConfimDialog(mContext,"确定要取消收藏吗?",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        RetrofitHelper.apiService.cancelCollectArticle(item.originId).applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<Any>>{
                            override fun onSuccess(t: HttpResult<Any>) {
                                if(t!=null){
                                    mContext.showToast("取消收藏成功")
                                    data.remove(item)
                                    notifyDataSetChanged()
                                }
                            }
                            override fun onFailture(t: Throwable) {

                            }

                        }))
                    }
                }).show()

            }

        })
    }
}