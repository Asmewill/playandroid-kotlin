package com.example.oapp.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oapp.R
import com.example.oapp.adapter.HomeAdapter
import com.example.oapp.adapter.KnowledgeAdapter
import com.example.oapp.base.BaseFragment
import com.example.oapp.bean.HttpResult
import com.example.oapp.bean.KnowledgeData
import com.example.oapp.expand.applySchdules
import com.example.oapp.http.ApiCallback
import com.example.oapp.http.HttpRetrofit
import com.example.oapp.http.OObserver
import kotlinx.android.synthetic.main.fragment_knowledge.*

/**
 * Created by jsxiaoshui on 2021/6/25
 */
class KnowledgeFragment:BaseFragment() {
    private val knowledgeAdapter by lazy {
        KnowledgeAdapter()
    }

    override fun attachlayoutRes(): Int {
        return R.layout.fragment_knowledge
    }

    override fun initView() {
        recyclerView?.run {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
            adapter=knowledgeAdapter
        }
        swipeRefreshLayout.setOnRefreshListener {
            getKnowledgeTree()
        }

    }

    override fun initData() {
        getKnowledgeTree()

    }

    private fun getKnowledgeTree() {
        HttpRetrofit.apiService.getKnowledgeTree().applySchdules()
            .subscribe(OObserver(object:ApiCallback<HttpResult<List<KnowledgeData>>>{
            override fun onSuccess(t: HttpResult<List<KnowledgeData>>) {
                t.data?.run {
                    knowledgeAdapter.setNewData(t.data)
                }
                swipeRefreshLayout.isRefreshing=false

            }
            override fun onFailture(t: Throwable) {
                swipeRefreshLayout.isRefreshing=false
            }
        }))
    }
}