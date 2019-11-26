package fall.out.wanandroid.ui

import android.view.MenuItem
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.bean.ToDoBean
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.ui.fragment.AboutUsFragment
import fall.out.wanandroid.ui.fragment.AddTodoFragment
import fall.out.wanandroid.ui.fragment.CollectFragment
import fall.out.wanandroid.ui.fragment.SearchListFragment
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Owen on 2019/11/4
 */
class CommonActivity:BaseActivity() {
    private var type=""
    private var title=""

    override fun attachLayoutRes(): Int {
        return  R.layout.activity_common
    }

    override fun initView() {
        type=intent.getStringExtra(Constant.TYPE_KEY)
        val fragment=when(type){
            Constant.Type.COLLECT_TYPE_KEY->{
                toolbar.title="收藏"
                CollectFragment.getIntance()
            }
             Constant.Type.ABOUT_US_TYPE_KEY->{
                 toolbar.title="关于我们"
                 AboutUsFragment.getInstance()
            }
            Constant.Type.SEARCH_TYPE_KEY->{
                title=intent.getStringExtra(Constant.SEARCH_WORD_KEY)
                toolbar.title=title
                SearchListFragment.getInstance(title)
            }
            Constant.Type.ADD_TODO_TYPE_KEY->{
                title="新增"
                toolbar.title=title
                AddTodoFragment.getInstance(1.toString(),null)
            }
            Constant.Type.EDIT_TODO_TYPE_KEY->{
                var item=intent.getSerializableExtra(Constant.ITEM_BEAN) as ToDoBean.DatasBean
                title="编辑"
                toolbar.title=title
                AddTodoFragment.getInstance(2.toString(),item)
            }
            Constant.Type.SEE_TODO_TYPE_KEY->{
                var item=intent.getSerializableExtra(Constant.ITEM_BEAN) as ToDoBean.DatasBean
                title="查看"
                toolbar.title=title
                AddTodoFragment.getInstance(3.toString(),item)
            }
            else ->
                null
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.common_frame_layout,fragment).commit()
        }
    }

    override fun initData() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}