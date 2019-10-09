package fall.out.wanandroid.http

import fall.out.wanandroid.bean.HttpResult
import fall.out.wanandroid.bean.UserInfoBody
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Owen on 2019/10/7
 */
interface ApiService {
    /**
     * 获取个人积分，需要登录后访问
     * https://www.wanandroid.com/lg/coin/userinfo/json
     */
    @GET("/lg/coin/userinfo/json")
    fun getUserInfo(): Observable<HttpResult<UserInfoBody>>

    /**
     * 退出登录
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    fun logout(): Observable<HttpResult<Any>>

}