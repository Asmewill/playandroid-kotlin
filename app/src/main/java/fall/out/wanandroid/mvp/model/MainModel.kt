package fall.out.wanandroid.mvp.model

import fall.out.wanandroid.base.BaseModel
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.UserInfoBody
import fall.out.wanandroid.http.RetrofitHelper
import fall.out.wanandroid.mvp.contract.MainContract
import io.reactivex.Observable

/**
 * Created by Owen on 2019/10/7
 */
class MainModel : BaseModel() ,MainContract.Model{

    override fun logout(): Observable<HttpResult<Any>>{
        return  RetrofitHelper.apiService.logout()
    }
    override fun getUserInfo(): Observable<HttpResult<UserInfoBody>> {
      return  RetrofitHelper.apiService.getUserInfo()
    }
}