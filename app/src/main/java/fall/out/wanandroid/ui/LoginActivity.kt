package fall.out.wanandroid.ui

import android.content.Intent
import android.view.MenuItem
import android.view.View
import fall.out.wanandroid.R
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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Owen on 2019/11/1
 */
class LoginActivity:BaseActivity(),View.OnClickListener {
    private var username:String by Preference(Constant.USER_NAME_KEY,"")
    private var pwd:String by Preference(Constant.PASSWORD_KEY,"")
    private var token:String by Preference(Constant.TOKEN,"")

    override fun attachLayoutRes(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        toolbar.title="登录"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        tv_sign_up.setOnClickListener(this)
        btn_login.setOnClickListener(this)

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
           R.id.tv_sign_up->{
              startActivity(Intent(this,RegisterActivity::class.java))
           }
           R.id.btn_login->{
               var usernameStr=et_username.text.toString()
               var passwordStr=et_password.text.toString()
               if(usernameStr.isEmpty()){
                   showToast("用户名不能为空")
                   return
               }
               if(passwordStr.isEmpty()){
                   showToast("密码不能为空")
                   return
               }
               RetrofitHelper.apiService.loginWanAndroid(usernameStr,passwordStr).applySchedulers().subscribe(OObserver(object:ApiCallBack<HttpResult<LoginBean>>{
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
                   }
                   override fun onFailture(t: Throwable) {

                   }
               }))

           }
       }
    }
}