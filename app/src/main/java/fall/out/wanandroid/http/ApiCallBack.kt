package fall.out.wanandroid.http

/**
 * Created by Owen on 2019/10/26
 */
interface ApiCallBack<T> {
    fun onSuccess(t:T)
    fun onFailture(t:Throwable)
}