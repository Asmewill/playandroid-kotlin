package com.example.oapp.ui.fragment

import com.example.oapp.R
import com.example.oapp.adapter.ProjectPageAdapter
import com.example.oapp.base.BaseFragment
import com.example.oapp.bean.HttpResult
import com.example.oapp.bean.ProjectData
import com.example.oapp.bean.ProjectItemData
import com.example.oapp.expand.applySchdules
import com.example.oapp.http.ApiCallback
import com.example.oapp.http.HttpRetrofit
import com.example.oapp.http.OObserver
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * Created by jsxiaoshui on 2021/6/25
 */
class ProjectFragment:BaseFragment() {
    override fun attachlayoutRes(): Int {
        return R.layout.fragment_project
    }

    override fun initView() {

    }

    override fun initData() {
        getProjectTab()

    }

    private fun getProjectTab() {
        HttpRetrofit.apiService.getProjectTab().applySchdules().subscribe(OObserver(object:ApiCallback<HttpResult<List<ProjectData>>>{
            override fun onSuccess(t: HttpResult<List<ProjectData>>) {
                viewPager.adapter=ProjectPageAdapter(t.data as ArrayList<ProjectData>,childFragmentManager)
                tabLayout.setupWithViewPager(viewPager)
            }
            override fun onFailture(t: Throwable) {

            }
        }))



    }
}