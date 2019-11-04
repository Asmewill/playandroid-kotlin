package fall.out.wanandroid.ui

import android.content.Intent
import android.view.MenuItem
import android.view.View
import fall.out.wanandroid.R
import fall.out.wanandroid.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Owen on 2019/11/1
 */
class RegisterActivity:BaseActivity(),View.OnClickListener {


    override fun attachLayoutRes(): Int {
         return  R.layout.activity_register
    }

    override fun initView() {
        toolbar.title="注册"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tv_sign_in.setOnClickListener(this)

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

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_sign_in->{
                startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
            }
        }

    }
}