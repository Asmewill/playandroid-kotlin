package fall.out.wanandroid.ui

import android.view.MenuItem
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Owen on 2019/11/4
 */
class SettingActivity:BaseActivity() {
    override fun attachLayoutRes(): Int {
        return R.layout.activity_setting
    }
    override fun initView() {
        toolbar.title="设置"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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