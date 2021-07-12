package com.example.oapp.ui.fragment

import com.example.oapp.R
import com.example.oapp.adapter.WeChatPageAdapter
import com.example.oapp.base.BaseFragment
import com.example.oapp.bean.HttpResult
import com.example.oapp.bean.WeChatData
import com.example.oapp.expand.applySchdules
import com.example.oapp.http.ApiCallback
import com.example.oapp.http.HttpRetrofit
import com.example.oapp.http.OObserver
import kotlinx.android.synthetic.main.fragment_wechat.*

/**
 * Created by jsxiaoshui on 2021/6/25
 */
class WeChatFragment:BaseFragment() {

    override fun attachlayoutRes(): Int {
        return R.layout.fragment_wechat
    }

    override fun initView() {

    }

    override fun initData() {
        getWechatTab()
    }

    private fun getWechatTab() {
        HttpRetrofit.apiService.getWechatTab().applySchdules().subscribe(OObserver(object:ApiCallback<HttpResult<List<WeChatData>>>{
            override fun onSuccess(t: HttpResult<List<WeChatData>>) {
                t.data.let {
                    viewPager.adapter=WeChatPageAdapter(it as ArrayList<WeChatData>,
                       childFragmentManager
                    )
                    tabLayout.setupWithViewPager(viewPager)
                }
            }
            override fun onFailture(t: Throwable) {

            }
        }))
    }
}