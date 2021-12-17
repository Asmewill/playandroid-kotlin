package com.example.oapp.flutter

import com.example.oapp.R
import com.example.oapp.base.BaseActivity
import io.flutter.embedding.android.FlutterFragment

/**
 * Created by jsxiaoshui on 2021-12-15
 */
class FlutterFragmentActivity:BaseActivity() {
    override fun attachLayoutRes(): Int {
         return R.layout.activity_flutter_fragment
    }

    override fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.fl_layout, FlutterFragment.createDefault()).commit()
    }

    override fun initData() {
    }
}