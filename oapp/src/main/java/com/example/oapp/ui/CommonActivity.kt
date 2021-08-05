package com.example.oapp.ui

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.oapp.R
import com.example.oapp.base.BaseActivity
import com.example.oapp.bean.ToDoBean
import com.example.oapp.constant.Constant
import com.example.oapp.ui.fragment.AboutUsFragment
import com.example.oapp.ui.fragment.AddToDoFragment
import com.example.oapp.ui.fragment.CollectFragment
import com.example.oapp.ui.fragment.SearchListFragment
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by jsxiaoshui on 2021/7/8
 */
class CommonActivity :BaseActivity() {
    private var fragment: Fragment?=null
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
            Constant.Type.COLLECT_TYPE_KEY->{
                toolbar.title="收藏"
                fragment=CollectFragment.getInstance()
            }
            Constant.Type.ABOUT_US_TYPE_KEY->{
                toolbar.title="关于我们"
                fragment=AboutUsFragment.getInstance()
            }
            Constant.Type.ADD_TODO_TYPE_KEY->{
                toolbar.title="新增"
                fragment=AddToDoFragment.getInstance(1,null)
            }
            Constant.Type.EDIT_TODO_TYPE_KEY->{
                toolbar.title="编辑"
                fragment=AddToDoFragment.getInstance(2,
                    intent.getSerializableExtra(Constant.ITEM_BENA) as ToDoBean.DatasBean?
                )
            }
            Constant.Type.SEE_TODO_TYPE_KEY->{
                toolbar.title="查看"
                fragment=AddToDoFragment.getInstance(3,
                    intent.getSerializableExtra(Constant.ITEM_BENA) as ToDoBean.DatasBean?)
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
