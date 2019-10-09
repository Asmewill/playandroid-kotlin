package fall.out.wanandroid.mvp.contract

import fall.out.wanandroid.base.IModel
import fall.out.wanandroid.base.IPresenter
import fall.out.wanandroid.base.IView
import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.UserInfoBody
import io.reactivex.Observable

/**
 * Created by Owen on 2019/10/5
 */
interface MainContract {
    interface View: IView {
        fun showLogoutSuccess(success:Boolean)
        fun showUserInfo(bean:UserInfoBody)
    }

    interface Presenter:IPresenter<View>{
        fun logout()
        fun getUserInfo()
    }
    interface Model:IModel{
        fun logout():Observable<HttpResult<Any>>
        fun getUserInfo():Observable<HttpResult<UserInfoBody>>
    }
}