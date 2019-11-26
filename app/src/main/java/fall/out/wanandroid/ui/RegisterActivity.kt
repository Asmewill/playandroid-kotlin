package fall.out.wanandroid.ui

import android.content.Intent
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import fall.out.wanandroid.R
import fall.out.wanandroid.Utils.DialogUtil
import fall.out.wanandroid.Utils.Preference
import fall.out.wanandroid.base.BaseActivity
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.LoginBean
import fall.out.wanandroid.constant.Constant
import fall.out.wanandroid.event.LoginEvent
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.ext.showToast
import fall.out.wanandroid.http.ApiCallBack
import fall.out.wanandroid.http.OObserver
import fall.out.wanandroid.http.RetrofitHelper
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Owen on 2019/11/1
 */
class RegisterActivity:BaseActivity(),View.OnClickListener {
    private var userShare:String by Preference(Constant.USER_NAME_KEY,"")
    private var pwdShare:String by Preference(Constant.PASSWORD_KEY,"")
    private val loadingDialog by lazy {
       DialogUtil.getWaitDialog(this,"注册中...")
    }

    override fun attachLayoutRes(): Int {
         return  R.layout.activity_register
    }

    override fun initView() {
        toolbar.title="注册"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tv_sign_in.setOnClickListener(this)
        btn_register.setOnClickListener(this)

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
                finish()
            }
            R.id.btn_register->{
               register()
            }
        }

    }

    fun register(){
        var userName=et_username.text.toString()
        var pwd1=et_password.text.toString()
        var pwd2=et_password2.text.toString()
        if(TextUtils.isEmpty(userName)){
            showToast("用户名不能为空")
            return
        }
        if(TextUtils.isEmpty(pwd1)){
            showToast("用户密码不能为空")
            return
        }
        if(TextUtils.isEmpty(pwd2)){
            showToast("用户密码不能为空")
            return
        }
        if(!pwd1.equals(pwd2)){
            showToast("二次密码输入不一致")
            return
        }
        loadingDialog.show()
        RetrofitHelper.apiService.register(userName,pwd1,pwd2).
            applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<LoginBean>>{
            override fun onSuccess(t: HttpResult<LoginBean>) {
                if(!this@RegisterActivity.isFinishing){
                     loadingDialog.dismiss()
                }
                loadingDialog.dismiss()
                if(t.errorCode==-1){
                    showToast(t.errorMsg)
                }else{
                    t.data?:return
                    isLogin=true
                    userShare=t.data?.username?:"null"
                    pwdShare=t.data?.password?:"null"
                    EventBus.getDefault().post(LoginEvent(true))
                    finish()
                }

            }
            override fun onFailture(t: Throwable) {
                if(!this@RegisterActivity.isFinishing){
                    loadingDialog.dismiss()
                }
            }
        }))


    }
}