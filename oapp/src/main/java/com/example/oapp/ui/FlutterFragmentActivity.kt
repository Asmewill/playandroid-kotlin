package com.example.oapp.ui

import com.example.oapp.R
import com.example.oapp.base.BaseActivity

/**
 * Created by jsxiaoshui on 2021-12-15
 */
class FlutterFragmentActivity:BaseActivity() {
    override fun attachLayoutRes(): Int {
         return R.layout.activity_flutter_fragment
    }

    override fun initView() {
       // supportFragmentManager.beginTransaction().add(R.id.fl_layout,FlutterFragment.createDefault()).commit()
    }

    override fun initData() {
    }
}