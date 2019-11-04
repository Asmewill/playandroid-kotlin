package fall.out.wanandroid.mvp.contract

import fall.out.wanandroid.base.IModel
import fall.out.wanandroid.base.IPresenter
import fall.out.wanandroid.base.IView
import fall.out.wanandroid.bean.HttpResult
import io.reactivex.Observable

/**
 * Created by Owen on 2019/10/26
 */
interface CommonContract {
    interface View:IView{
        fun showCollectSuccess(success:Boolean)
        fun showCancelCollectSuccess(success:Boolean)
    }
    interface Presenter<in V:View> :IPresenter<V>{
        fun addCollectArticle(id:Int)
        fun cancelCollectArticle(id:Int)
    }
    interface Model:IModel{
        fun addCollectArticle(id:Int):Observable<HttpResult<Any>>
        fun cancelCollectArticle(id:Int):Observable<HttpResult<Any>>
    }
}