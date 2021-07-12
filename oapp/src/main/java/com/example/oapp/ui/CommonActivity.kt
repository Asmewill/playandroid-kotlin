package com.example.oapp.ui

import android.content.Intent
import android.view.MenuItem
import com.example.oapp.R
import com.example.oapp.base.BaseActivity
import com.example.oapp.constant.Constant
import com.example.oapp.expand.showToast
import com.example.oapp.ui.fragment.SearchListFragment
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by jsxiaoshui on 2021/7/8
 */
class CommonActivity :BaseActivity() {
    private var fragment: SearchListFragment?=null
    private var keyWord: String?=""
    private var pageType: String?=""
    private var type=""
    private var title=""

    override fun attachLayoutRes(): Int {
        return R.layout.activity_common
    }
    override fun initView() {


    }
    override fun initData() {
        pageType=intent.getStringExtra(Constant.PAGE_TYPE)
        when(pageType){
            Constant.Type.SEARCH_TYPE_KEY->{
                keyWord=intent.getStringExtra(Constant.SEARCH_KEY)
                toolbar.title=keyWord
                fragment= SearchListFragment.getInstance(keyWord)
            }
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.common_frame_layout,it).commit()
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
}
