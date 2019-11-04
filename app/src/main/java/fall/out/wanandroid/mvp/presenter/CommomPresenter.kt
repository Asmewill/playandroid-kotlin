package fall.out.wanandroid.mvp.presenter

import fall.out.wanandroid.base.BasePresenter
import fall.out.wanandroid.ext.applySchedulers
import fall.out.wanandroid.mvp.contract.CommonContract

/**
 * Created by Owen on 2019/10/26
 */
open abstract class CommomPresenter<M:CommonContract.Model,V:CommonContract.View> :BasePresenter<M,V>(),CommonContract.Presenter<V>{

    override fun addCollectArticle(id: Int) {
        mModel?.addCollectArticle(id)?.applySchedulers()
    }

    override fun cancelCollectArticle(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}