package com.example.oapp.ui

import android.content.Intent
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.example.oapp.R
import com.example.oapp.base.BaseActivity
import com.example.oapp.bean.HttpResult
import com.example.oapp.bean.LoginBean
import com.example.oapp.constant.Constant
import com.example.oapp.event.LoginEvent
import com.example.oapp.expand.applySchdules
import com.example.oapp.expand.showToast
import com.example.oapp.http.ApiCallback
import com.example.oapp.http.HttpRetrofit
import com.example.oapp.http.OObserver
import com.example.oapp.utils.DialogUtil
import com.example.oapp.utils.Preference
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by jsxiaoshui on 2021/7/9
 */
class LoginActivity:BaseActivity() ,View.OnClickListener{
    private var username:String by Preference(Constant.USER_NAME_KEY,"")
    private var pwd:String by Preference(Constant.PASSWORD_KEY,"")
    private val loadingDialog by lazy {
        DialogUtil.getWaitDialog(this,"登录中...")
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        toolbar?.let {
            it.title="登录"
            setSupportActionBar(it)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btn_login.setOnClickListener(this)
        tv_sign_up.setOnClickListener(this)

    }

    override fun initData() {

    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login->{
                goLogin()

            }
            R.id.tv_sign_up->{
                val intent=Intent(this@LoginActivity,RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun goLogin() {
        loadingDialog.show()
        val userNameStr:String=et_username.text.toString()
        val pwdStr:String= et_password.text.toString()
        if(TextUtils.isEmpty(userNameStr)){
            showToast("用户名不能为空")
        }
        if(TextUtils.isEmpty(pwdStr)){
            showToast("密码不能为空")
        }
        HttpRetrofit.apiService.loginWanAndroid(userNameStr,pwdStr).applySchdules().subscribe(OObserver(object:ApiCallback<HttpResult<LoginBean>>{
            override fun onSuccess(t: HttpResult<LoginBean>) {
                if(t.errorCode==-1){
                    showToast(t.errorMsg)
                }else{
                    t.data?:return
                    isLogin=true
                    username=t.data?.username?:"null"
                    pwd=t.data?.password?:"null"
                    EventBus.getDefault().post(LoginEvent(true))
                    finish()
                }
                loadingDialog.dismiss()
            }
            override fun onFailture(t: Throwable) {
                showToast("网络错误，登录失败，请重试")
                loadingDialog.dismiss()
            }
        }))
    }
}