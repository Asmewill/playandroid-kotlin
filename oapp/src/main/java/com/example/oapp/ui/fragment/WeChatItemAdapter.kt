package com.example.oapp.ui.fragment

import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.oapp.R
import com.example.oapp.bean.WeChatItemData
import com.example.oapp.expand.showToast
import com.example.oapp.ui.MainActivity

/**
 * Created by jsxiaoshui on 2021/7/2
 */
class WeChatItemAdapter:BaseQuickAdapter<WeChatItemData.DatasBean,BaseViewHolder>(R.layout.item_wechat) {

    override fun convert(holder: BaseViewHolder?, item: WeChatItemData.DatasBean?) {
        holder?:return
        item?:return
        val tv_article_author=holder.getView<TextView>(R.id.tv_article_author)
        val tv_article_date=holder.getView<TextView>(R.id.tv_article_date)
        val tv_article_title=holder.getView<TextView>(R.id.tv_article_title)
        val tv_article_chapterName=holder.getView<TextView>(R.id.tv_article_chapterName)
        val iv_like=holder.getView<ImageView>(R.id.iv_like)
         item.author?.let {
             tv_article_author.setText(it)
         }
        item.title?.let {
            tv_article_title.setText(it)
        }
        item.chapterName?.let {
            tv_article_chapterName.setText(it)
        }
        item.niceDate?.let {
            tv_article_date.setText(it)
        }
        iv_like.setOnClickListener {
            showToast("collect。。。。。")
        }
    }
}