package fall.out.wanandroid.ui

import android.view.MenuItem
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.constant.Constant
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Owen on 2019/11/4
 */
class CommonActivity:BaseActivity() {
    private var type=""
    override fun attachLayoutRes(): Int {
        return  R.layout.activity_common
    }

    override fun initView() {
        toolbar.title="...."
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val fragment=when(type){
            Constant.Type.ABOUT_US_TYPE_KEY->{

            }
            else->
                null
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