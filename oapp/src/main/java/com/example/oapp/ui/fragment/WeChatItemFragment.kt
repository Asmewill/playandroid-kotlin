package com.example.oapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.oapp.R
import com.example.oapp.base.BaseFragment
import com.example.oapp.bean.HttpResult
import com.example.oapp.bean.WeChatItemData
import com.example.oapp.constant.Constant
import com.example.oapp.expand.applySchdules
import com.example.oapp.http.ApiCallback
import com.example.oapp.http.HttpRetrofit
import com.example.oapp.http.OObserver
import com.example.oapp.ui.ContentActivity
import kotlinx.android.synthetic.main.fragment_wechat_tab.*


/**
 * Created by jsxiaoshui on 2021/6/30
 */
class WeChatItemFragment:BaseFragment() {
    private var pageNum=0

    private val weChatItemAdapter by lazy {
        WeChatItemAdapter()
    }
    private var cid: Int=-1
    companion object{
        fun getInstance(cid:Int):WeChatItemFragment{
            val weChatItemFragment=WeChatItemFragment();
            val bundle=Bundle();
            bundle.putInt(Constant.TAB_CID,cid)
            weChatItemFragment.arguments=bundle
            return weChatItemFragment
        }
    }
    override fun attachlayoutRes(): Int {
        return R.layout.fragment_wechat_tab
    }
    override fun initView() {
        cid= arguments?.getInt(Constant.TAB_CID,-1)!!
        recyclerView?.run {
            this.layoutManager=LinearLayoutManager(activity)
            this.adapter=weChatItemAdapter
        }
        swipeRefreshLayout?.setOnRefreshListener {
            pageNum=0
            getWeChatItemData()
        }
        weChatItemAdapter.setOnLoadMoreListener(object:BaseQuickAdapter.RequestLoadMoreListener{
            override fun onLoadMoreRequested() {
                pageNum++
                getWeChatItemData()
            }
        },recyclerView)
        weChatItemAdapter.setOnItemClickListener { baseQuickAdapter, view, i ->
            val item:WeChatItemData.DatasBean = baseQuickAdapter.data.get(i) as WeChatItemData.DatasBean
            val intent=Intent(activity,ContentActivity::class.java)
            intent.putExtra(Constant.CONTENT_ID,item.id)
            intent.putExtra(Constant.CONTENT_URL,item.link)
            intent.putExtra(Constant.CONTENT_TITLE,item.title)
            startActivity(intent)

        }

    }
    override fun initData() {
        getWeChatItemData()
    }

    private fun getWeChatItemData() {
        HttpRetrofit.apiService.getWechatTabDetail(pageNum,cid).applySchdules().subscribe(OObserver(object:ApiCallback<HttpResult<WeChatItemData>>{
            override fun onSuccess(t: HttpResult<WeChatItemData>) {
                if(pageNum==0){
                    t.data?.datas?.let {
                        weChatItemAdapter.setNewData(t.data!!.datas)
                    }
                    swipeRefreshLayout?.isRefreshing=false
                }else{
                    t.data?.datas?.let {
                        weChatItemAdapter.addData(it)
                    }
                }
                if(t.data!!.curPage< t.data!!.pageCount){
                    weChatItemAdapter.loadMoreComplete()
                    weChatItemAdapter.setEnableLoadMore(true)
                }else{
                    weChatItemAdapter.loadMoreEnd(false)
                }

            }
            override fun onFailture(t: Throwable) {
                swipeRefreshLayout?.isRefreshing=false
            }
        }))
    }
}