package fall.out.wanandroid.base

/**
 * Created by Owen on 2019/10/7
 */
abstract class BasePresenter<M:IModel,V:IView>:IPresenter<V> {
    protected var mModel:M?=null
    protected  var mView:V?=null
    abstract fun createModel(): M?

    override fun attachView(mView: V) {
        this.mView=mView
        //创建Model
        this.mModel=createModel()
    }
    //取消Model的订阅
    override fun detachView() {
        mModel?.onDetach()
    }
}