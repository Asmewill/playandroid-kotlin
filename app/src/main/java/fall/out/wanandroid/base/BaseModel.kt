package fall.out.wanandroid.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Owen on 2019/10/7
 */
open class BaseModel :IModel{
    private var mCompositeDisposable:CompositeDisposable?=null
    //添加部署
    override fun addDisposable(disposable: Disposable) {
        if(mCompositeDisposable==null){
            mCompositeDisposable= CompositeDisposable()
        }
        disposable.let {
            mCompositeDisposable?.add(it)
        }
    }
    //解除部署
    override fun onDetach() {
        unDispose()
    }

    private fun unDispose() {
        mCompositeDisposable?.clear()
        mCompositeDisposable=null
    }
}