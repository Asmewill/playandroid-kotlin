package com.example.oapp.ui

import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.oapp.R
import com.example.oapp.adapter.KnowledgePageAdapter
import com.example.oapp.base.BaseVmDbActivity
import com.example.oapp.bean.KnowledgeData
import com.example.oapp.constant.Constant
import com.example.oapp.databinding.ActivityKnowledgeBinding
import com.example.oapp.viewmodel.KnowledgeViewModel
import kotlinx.android.synthetic.main.activity_knowledge.*

/**
 * Created by jsxiaoshui on 2021/8/2
 */
@Route(path = Constant.PagePath.KNOWLEDGE)
class KnowledgeActivity: BaseVmDbActivity<KnowledgeViewModel, ActivityKnowledgeBinding>() {


    private var listData:MutableList<KnowledgeData.ChildrenBean>?= mutableListOf<KnowledgeData.ChildrenBean>()
    private lateinit var knowledgePageAdapter:KnowledgePageAdapter

    @Autowired
    @JvmField var item_bean: KnowledgeData?=null


    override fun createViewModel(): KnowledgeViewModel {
        return ViewModelProvider(this).get(KnowledgeViewModel::class.java)
    }

    override fun layoutId(): Int {
         return R.layout.activity_knowledge
    }

    override fun initView() {
        ARouter.getInstance().inject(this)
        item_bean=intent.getSerializableExtra(Constant.ITEM_BENA) as KnowledgeData
        item_bean?.let {
            listData=it.children
            listData?.run {
                knowledgePageAdapter= KnowledgePageAdapter(this,supportFragmentManager)
                viewPager.adapter=knowledgePageAdapter
                tabLayout.setupWithViewPager(viewPager)
            }
        }
        toolbar?.title=item_bean?.name
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun initColor() {
        super.initColor()
        tabLayout.setBackgroundColor(mThemeColor)
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


    }

    override fun createObserver() {

    }
}