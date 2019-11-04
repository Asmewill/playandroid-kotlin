package fall.out.wanandroid.http

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by Owen on 2019/10/26
 */
class OObserver<T>  constructor(private val apiCallBack: ApiCallBack<T>):Observer<T> {
//    var apiCallBack:ApiCallBack<T>?=null
//    init {
//        this.apiCallBack=apiCallBack
//    }
    override fun onComplete() {

    }
    override fun onSubscribe(d: Disposable) {

    }
    override fun onNext(t: T) {
        apiCallBack?.onSuccess(t)
    }

    override fun onError(e: Throwable) {
        apiCallBack?.onFailture(e)
    }
}