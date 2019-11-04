package fall.out.wanandroid.base

import fall.out.wanandroid.ext.showToast

/**
 * Created by Owen on 2019/10/5
 * 如果你的类是将泛型作为内部方法的返回，那么可以用 out：协变
 * interface Production<out T> {
      fun produce(): T
     }
 *
 * 如果你的类是将泛型对象作为函数的参数，那么可以用 in：逆变
 * interface Consumer<in T> {
   fun consume(item: T)
   }
 * V:IView 等价于V extends IView
 *
 */

abstract class BaseMvpActivity<in V:IView,P:IPresenter<V>> :BaseActivity(),IView{
    private var mPresenter: P?=null

    override fun initView() {
       mPresenter=createPresenter()
       mPresenter?.attachView(this as V)
    }

    abstract fun createPresenter(): P


    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
    override fun showLoading() {

    }
    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {
             showToast(errorMsg)
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun showDefaultMsg(msg: String) {
        showToast(msg)
    }

}