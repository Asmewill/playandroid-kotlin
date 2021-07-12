package com.example.oapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oapp.R
import com.example.oapp.adapter.ProjectListAdapter
import com.example.oapp.base.BaseFragment
import com.example.oapp.bean.HttpResult
import com.example.oapp.bean.ProjectItemData
import com.example.oapp.constant.Constant
import com.example.oapp.expand.applySchdules
import com.example.oapp.http.ApiCallback
import com.example.oapp.http.HttpRetrofit
import com.example.oapp.http.OObserver
import com.example.oapp.ui.ContentActivity
import kotlinx.android.synthetic.main.fragment_wechat_tab.*
import kotlinx.android.synthetic.main.item_navigation_list.*


/**
 * Created by jsxiaoshui on 2021/6/30
 */
class ProjectItemFragment:BaseFragment() {
    private var pageNum=0

    private var cid: Int=-1
    private val projectListAdapter by lazy {
        ProjectListAdapter()
    }
    companion object{
        fun getInstance(cid:Int):ProjectItemFragment{
            val projectFragment=ProjectItemFragment();
            val bundle=Bundle()
            bundle.putInt(Constant.TAB_CID,cid)
            projectFragment.arguments=bundle
            return projectFragment
        }
    }
    override fun attachlayoutRes(): Int {
        return R.layout.fragment_project_list
    }
    override fun initView() {
        cid= arguments!!.getInt(Constant.TAB_CID)
        recyclerView?.let {
            it.layoutManager=LinearLayoutManager(activity)
            it.adapter=projectListAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            pageNum=0
            getProjectTabDetail()
        }
        projectListAdapter.setOnLoadMoreListener {
            pageNum++
            getProjectTabDetail()
        }
        projectListAdapter.setOnItemClickListener { baseQuickAdapter, view, i ->
            val intent=Intent(activity,ContentActivity::class.java)
            val bean= baseQuickAdapter.data.get(i) as ProjectItemData.DatasBean
            intent.putExtra(Constant.CONTENT_TITLE,bean.title)
            intent.putExtra(Constant.CONTENT_URL,bean.link)
            intent.putExtra(Constant.CONTENT_ID,bean.id)
            startActivity(intent)
        }
    }



    override fun initData() {
        getProjectTabDetail()
    }

    private fun getProjectTabDetail() {
        HttpRetrofit.apiService.getProjectDetail(pageNum,cid).applySchdules().subscribe(OObserver(object:ApiCallback<HttpResult<ProjectItemData>>{
            override fun onSuccess(t: HttpResult<ProjectItemData>) {
                t.data?.let {
                    if(pageNum==0){
                        projectListAdapter.setNewData(it.datas)
                        swipeRefreshLayout.isRefreshing=false
                    }else{
                        projectListAdapter.addData(it.datas!!)
                    }
                    if(t.data!!.curPage< t.data!!.pageCount){
                        projectListAdapter.loadMoreComplete()
                        projectListAdapter.setEnableLoadMore(true)
                    }else{
                        projectListAdapter.loadMoreEnd(false)
                    }
                }
            }
            override fun onFailture(t: Throwable) {
                swipeRefreshLayout.isRefreshing=false

            }
        }))

    }

}