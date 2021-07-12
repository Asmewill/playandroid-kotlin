package com.example.oapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.oapp.R
import com.example.oapp.adapter.HomeAdapter
import com.example.oapp.base.BaseFragment
import com.example.oapp.bean.HomeData
import com.example.oapp.bean.HttpResult
import com.example.oapp.constant.Constant
import com.example.oapp.expand.applySchdules
import com.example.oapp.http.ApiCallback
import com.example.oapp.http.HttpRetrofit
import com.example.oapp.http.OObserver
import com.example.oapp.ui.ContentActivity
import com.example.oapp.utils.CommonUtil
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.act

/**
 * Created by jsxiaoshui on 2021/7/8
 */
class SearchListFragment:BaseFragment() {
    private var pageNum:Int=0
    private var keyWord:String=""
    private val homeAdapter by lazy {
        HomeAdapter()
    }

    override fun attachlayoutRes(): Int {
        return R.layout.fragment_search_list
    }
    companion object{
        fun getInstance(key:String?):SearchListFragment{
            val searchListFragment=SearchListFragment()
            val bundle=Bundle()
            bundle.putString(Constant.SEARCH_KEY,key)
            searchListFragment.arguments=bundle
            return searchListFragment
        }
    }

    override fun initView() {
        keyWord=arguments?.getString(Constant.SEARCH_KEY)!!
        recyclerView?.let {
            it.layoutManager=LinearLayoutManager(activity)
            it.adapter=homeAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            pageNum=0
            getSearchList()
        }
        homeAdapter.setOnLoadMoreListener({
            pageNum++
            getSearchList()
        },recyclerView)
        homeAdapter.onItemClickListener =
            BaseQuickAdapter.OnItemClickListener { p0, _, p2 ->
                val intent=Intent(activity,ContentActivity::class.java)
                val bean:HomeData.DatasBean= p0?.data?.get(p2) as HomeData.DatasBean
                intent.putExtra(Constant.CONTENT_TITLE,bean.title)
                intent.putExtra(Constant.CONTENT_URL,bean.link)
                intent.putExtra(Constant.CONTENT_ID,bean.id)
                startActivity(intent)
            }
    }

    override fun initData() {
        getSearchList()
    }

    private fun getSearchList() {
            HttpRetrofit.apiService.getSearchList(pageNum, keyWord).applySchdules().subscribe(OObserver(object:ApiCallback<HttpResult<HomeData>>{
                override fun onSuccess(t: HttpResult<HomeData>) {
                    if(t?.data != null &&t.data?.size!!>0){
                        if (pageNum <= 0) {
                            homeAdapter.setNewData(t.data?.datas)
                        } else {
                            homeAdapter.addData(t.data?.datas!!)
                        }
                        if (t?.data?.curPage!! < t?.data?.pageCount!!) {
                            homeAdapter?.loadMoreComplete()
                            homeAdapter?.setEnableLoadMore(true)
                        } else {
                            homeAdapter?.loadMoreEnd(false)
                        }
                        CommonUtil.forceStopRecycleViewScroll(recyclerView)//防止recycleView惯性滑动
                    }
                    swipeRefreshLayout?.isRefreshing=false
                }
                override fun onFailture(t: Throwable) {
                    swipeRefreshLayout?.isRefreshing=false
                }
            }))
        }

}