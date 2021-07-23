package com.example.oapp.ui

import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oapp.R
import com.example.oapp.adapter.RankListAdapter
import com.example.oapp.base.BaseVmDbActivity
import com.example.oapp.bean.ListDataUiState
import com.example.oapp.bean.PointBean
import com.example.oapp.databinding.ActivityPointRankListBinding
import com.example.oapp.expand.showToast
import com.example.oapp.viewmodel.RankListViewModel
import kotlinx.android.synthetic.main.activity_point_rank_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by jsxiaoshui on 2021/7/22
 */
class RankListActivity:BaseVmDbActivity<RankListViewModel,ActivityPointRankListBinding>() {

    var pageNo=1
    private val rankListAdapter by lazy {
        RankListAdapter()
    }

    override fun createViewModel(): RankListViewModel {
        return ViewModelProvider(this).get(RankListViewModel::class.java)
    }

    override fun layoutId(): Int {
        return  R.layout.activity_point_rank_list
    }

    override fun initView() {
        //let的基础用法
//        toolbar?.let{
//            setTitle("积分排行榜")
//            setSupportActionBar(it)
//            return@let 88//函数最后一行或者return 表达式表示函数的返回值
//        }
//        supportActionBar?.setDisplayHomeAsUpEnabled(true) //显示返回按钮
        toolbar?.title = "积分排行榜"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//显示返回按钮
        recyclerView?.let {
            recyclerView.layoutManager=LinearLayoutManager(this)
            recyclerView.adapter=rankListAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            pageNo=1
            mViewModel.getHomeRankList(pageNo)
        }



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
           android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
        mViewModel.getHomeRankList(pageNo)
    }



    override fun createObserver() {
         mViewModel.rankListLiveData.observe(this,
             Observer<ListDataUiState<PointBean>> {
                 when(!it.isException){
                     true->{
                         it.dataBean?.data?.let {  pointBean->
                             rankListAdapter.setNewData(pointBean.datas)
                         }
                     }
                     false->{

                         it.error?.message?.let { it1 -> showToast(it1) }
                     }
                 }
                 swipeRefreshLayout.isRefreshing=false
             }
         )

    }
}