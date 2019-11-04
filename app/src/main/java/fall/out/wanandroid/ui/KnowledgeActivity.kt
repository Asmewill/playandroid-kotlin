package fall.out.wanandroid.ui

import android.view.MenuItem
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.bean.KnowledgeTreeBody
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ui.fragment.KnowledgePageAdapter
import kotlinx.android.synthetic.main.activity_knowledge.*
import kotlinx.android.synthetic.main.toolbar.toolbar

/**
 * Created by Owen on 2019/11/1
 */
class KnowledgeActivity:BaseActivity() {
    private var title=""
    private lateinit var knowledgeTreeBody:KnowledgeTreeBody

    override fun attachLayoutRes(): Int {
        return R.layout.activity_knowledge
    }
    override fun initView() {
        title=intent.getStringExtra(Constant.CONTENT_TITLE_KEY)
        knowledgeTreeBody= intent.getSerializableExtra(Constant.ITEM_BEAN) as KnowledgeTreeBody
        toolbar.title=title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(knowledgeTreeBody!=null&&knowledgeTreeBody.children!=null&&knowledgeTreeBody.children?.size!!>0){
            viewPager.adapter=KnowledgePageAdapter(knowledgeTreeBody.children!!,supportFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun initData() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){//响应toolbar 返回键
            android.R.id.home->{
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }


}