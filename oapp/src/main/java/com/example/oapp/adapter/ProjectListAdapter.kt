package com.example.oapp.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.oapp.R
import com.example.oapp.bean.ProjectItemData
import com.example.oapp.utils.ImageLoader

/**
 * Created by jsxiaoshui on 2021/7/5
 */
class ProjectListAdapter:BaseQuickAdapter<ProjectItemData.DatasBean,BaseViewHolder>(R.layout.item_project_list) {

    override fun convert(holder: BaseViewHolder?, itemdata: ProjectItemData.DatasBean?) {
        holder?:return
        itemdata?:return
        val item_project_list_iv=holder.getView<ImageView>(R.id.item_project_list_iv)
        val item_project_list_title_tv=holder.getView<TextView>(R.id.item_project_list_title_tv)
        val item_project_list_content_tv=holder.getView<TextView>(R.id.item_project_list_content_tv)
        val item_project_list_author_tv=holder.getView<TextView>(R.id.item_project_list_author_tv)
        val item_project_list_time_tv=holder.getView<TextView>(R.id.item_project_list_time_tv)
        val item_project_list_like_iv=holder.getView<ImageView>(R.id.item_project_list_like_iv)

        ImageLoader.loadIv(mContext,itemdata.envelopePic!!,item_project_list_iv)
        itemdata.title?.let {
            item_project_list_title_tv.setText(it)
        }
        itemdata.desc?.let {
            item_project_list_content_tv.setText(it)
        }
        itemdata.author?.let {
            item_project_list_author_tv.setText(itemdata.author)
        }
        itemdata.niceDate?.let {
            item_project_list_time_tv.setText(itemdata.niceDate)
        }
        if(itemdata.zan>0){
            item_project_list_like_iv.setImageResource(R.drawable.ic_like)
        }else{
            item_project_list_like_iv.setImageResource(R.drawable.ic_like_not)
        }
    }
}